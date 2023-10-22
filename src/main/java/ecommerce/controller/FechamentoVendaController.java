package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import ecommerce.beans.Recebimento;
import ecommerce.beans.Venda;
import ecommerce.dao.CaixaDao;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dao.RecebimentoDao;
import ecommerce.dao.VendaDao;
import ecommerce.dto.CaixaDto;
import ecommerce.dto.ParcelaDto;
import ecommerce.dto.RecebimentoDto;
import ecommerce.dto.VendaDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.InjectBean;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Transactional
@ConversationScoped
public class FechamentoVendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GerenciadorToken token;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private Uteis uteis;

	@Inject
	private ItemVendaController itemVendaController;

	@Inject
	private VendaDao vendaDao;
	
	@Inject
	private CaixaDao caixaDao;
	
	@Inject
	private RecebimentoDao recebimentoDao;
	
	private List<RecebimentoDto> recebimentos;

	private ParcelaDto parcelaDto;

	private CaixaDto caixaDto;
	
	private RecebimentoDto recebimentoEncontrado = new RecebimentoDto();
	
	private boolean procedimentorealizado = false;

	@PostConstruct
	public void carregarRecebimentos() {
		recebimentos = itemVendaController.getListaRecebimentoDto();
	}
	
	public void recuperarRecebimento(RecebimentoDto recebimentoDto) {
		recebimentoEncontrado = recebimentoDto;
	}

	public void gravarAlteracao() {
		try {
			token.validarToken();
			Optional<RecebimentoDto> optional = recebimentos.stream().filter( e-> e.getCodigo() == recebimentoEncontrado.getCodigo()).findFirst();
			recebimentos.remove(optional.get());		
			recebimentos.add(recebimentoEncontrado);
			atualizarSoma();
			procedimentorealizado = true;
		} catch (TokenException e2) {
			uteis.adicionarMensagemErro(e2);
		}
	}
	
	private void atualizarSoma() {
		BigDecimal resultado = recebimentos.stream().map(e -> e.getValor()).reduce(BigDecimal.ZERO, BigDecimal::add);
		itemVendaController.setTotalRecebimento(resultado);
	}
	
	public String concluirVenda() {
		try {
			token.validarToken();
			if (recebimentos.stream().anyMatch(e -> e.getValor().compareTo(BigDecimal.ZERO) <=0)) {
				uteis.adicionarMensagemAdvertencia("Existem parcelas com o valor zerado! Por favor verifique.");
				return null;
			}
			aplicarValorTotalVendaCorrigido();
			aplicarCorrecaoValorRecebimento();
			Venda venda = criarVenda();
			vendaDao.cadastrar(venda);
			criarLancamentoCaixa(venda);
			vendaDao.editar(venda);
			uteis.adicionarMensagemSucessoRegistro();
			return "/ecommerce/paginas/processos/impressosVenda.xhtml";
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	private void aplicarValorTotalVendaCorrigido() {
		VendaDto vendaDto = itemVendaController.getVendaDto();
		BigDecimal totalVenda = vendaDto.getTotalVenda();
		BigDecimal resultadoVendaCorrigido = itemVendaController.getValorTotalVendaCorrigido();
		int aux = resultadoVendaCorrigido.compareTo(totalVenda);
		if (aux == -1) {
			vendaDto.setDesconto(totalVenda.subtract(resultadoVendaCorrigido));
		} else if (aux == 1) {
			vendaDto.setAcrescimo(resultadoVendaCorrigido.subtract(totalVenda));
		}
	}
	
	public void calcularTroco(AjaxBehaviorEvent e) {
		VendaDto vendaDto = itemVendaController.getVendaDto();
		if (vendaDto.getRecebido() == null) {
			return;
		}
		if (vendaDto.getRecebido().compareTo(BigDecimal.ZERO) <= 0 || vendaDto.getRecebido().compareTo(itemVendaController.getTotalRecebimento()) == -1) {
			vendaDto.setRecebido(BigDecimal.ZERO);
			vendaDto.setTroco(BigDecimal.ZERO);
			uteis.adicionarMensagemAdvertencia("O valor recebido deve ser maior que o de venda!");
			return;
		}
		vendaDto.setTroco(vendaDto.getRecebido().subtract(itemVendaController.getTotalRecebimento()));
	}
	
	private void aplicarCorrecaoValorRecebimento() {
		VendaDto vendaDto = itemVendaController.getVendaDto();
		BigDecimal totalVenda = vendaDto.getTotalVenda();
		BigDecimal somaRecebimentos = itemVendaController.getTotalRecebimento();
		int aux = somaRecebimentos.compareTo(totalVenda);
		if (aux == -1) {
			vendaDto.setDesconto(totalVenda.subtract(somaRecebimentos).add(vendaDto.getDesconto()));
		} else if (aux == 1) {
			vendaDto.setAcrescimo(somaRecebimentos.subtract(totalVenda).add(vendaDto.getAcrescimo()));
		}
	}

	public Venda criarVenda() {
		ClienteDao cliDao = InjectBean.newInstanceCDI(ClienteDao.class);
		FuncionarioDao funcDao = InjectBean.newInstanceCDI(FuncionarioDao.class);
		ProdutoDao prodDao = InjectBean.newInstanceCDI(ProdutoDao.class);
		return itemVendaController.getVendaDto().toVenda(cliDao, funcDao, prodDao, itemVendaController.getListItemsVenda());
	}

	public void criarLancamentoCaixa(Venda venda) {
		int numeroParcela = 1;
		for (RecebimentoDto recebimentoDto : recebimentos) {
			parcelaDto = new ParcelaDto();
			parcelaDto.setDataEmissao(venda.getDataVenda());
			parcelaDto.setDataPagamento(null);
			parcelaDto.setNumeroParcela(String.valueOf(++numeroParcela));
			parcelaDto.setValorParcela(recebimentoDto.getValor());
			if (recebimentoDto.isQuitado()) {
				caixaDto = new CaixaDto();
				caixaDto.setDataLancamento(LocalDateTime.now());
				caixaDto.setDescricao("Recebimento: " + venda.getCliente().getNome()+" - Parcela: "+parcelaDto.getNumeroParcela());
				caixaDto.setValorRecebimento(recebimentoDto.getValor());
				parcelaDto.setDataPagamento(recebimentoDto.getDataVencimento());
				parcelaDto.getListacaixa().add(caixaDto);
			}
			Recebimento recebimento = recebimentoDto.toRecebimento(venda, parcelaDto);
			venda.getRecebimentos().add(recebimento);
			recebimentoDao.cadastrar(recebimento);
		}
	}

	public String voltar() {
		return itemVendaController.getVendaController().chamarItensVenda();
	}

	public String cancelar() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}

	public GerenciadorToken getToken() {
		return token;
	}

	public void setToken(GerenciadorToken token) {
		this.token = token;
	}

	public GerenciadorConversa getConversa() {
		return conversa;
	}

	public void setConversa(GerenciadorConversa conversa) {
		this.conversa = conversa;
	}

	public ItemVendaController getItemVendaController() {
		return itemVendaController;
	}

	public void setItemVendaController(ItemVendaController itemVendaController) {
		this.itemVendaController = itemVendaController;
	}

	public List<RecebimentoDto> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<RecebimentoDto> recebimentos) {
		this.recebimentos = recebimentos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public VendaDao getVendaDao() {
		return vendaDao;
	}

	public void setVendaDao(VendaDao vendaDao) {
		this.vendaDao = vendaDao;
	}

	public CaixaDao getCaixaDao() {
		return caixaDao;
	}

	public void setCaixaDao(CaixaDao caixaDao) {
		this.caixaDao = caixaDao;
	}

	public RecebimentoDao getRecebimentoDao() {
		return recebimentoDao;
	}

	public void setRecebimentoDao(RecebimentoDao recebimentoDao) {
		this.recebimentoDao = recebimentoDao;
	}

	public ParcelaDto getParcelaDto() {
		return parcelaDto;
	}

	public void setParcelaDto(ParcelaDto parcelaDto) {
		this.parcelaDto = parcelaDto;
	}

	public CaixaDto getCaixaDto() {
		return caixaDto;
	}

	public void setCaixaDto(CaixaDto caixaDto) {
		this.caixaDto = caixaDto;
	}

	public RecebimentoDto getRecebimentoEncontrado() {
		return recebimentoEncontrado;
	}

	public void setRecebimentoEncontrado(RecebimentoDto recebimentoEncontrado) {
		this.recebimentoEncontrado = recebimentoEncontrado;
	}

	public boolean isProcedimentorealizado() {
		return procedimentorealizado;
	}

	public void setProcedimentorealizado(boolean procedimentorealizado) {
		this.procedimentorealizado = procedimentorealizado;
	}

}
