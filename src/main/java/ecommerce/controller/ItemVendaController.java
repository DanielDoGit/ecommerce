package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cliente;
import ecommerce.beans.Parcela;
import ecommerce.beans.Produto;
import ecommerce.beans.Recebimento;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.CondicaoPagamentoDao;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.FormaPagamentoDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dao.RecebimentoDao;
import ecommerce.dto.CondicaoPagamentoDto;
import ecommerce.dto.FormaPagamentoDto;
import ecommerce.dto.ItemVendaDto;
import ecommerce.dto.ProdutoDto;
import ecommerce.dto.RecebimentoDto;
import ecommerce.dto.VendaDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@ConversationScoped
@Named
@Transactional
public class ItemVendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;

	@Inject
	private VendaController vendaController;

	@Inject
	private EstoqueTransienteDao estoqueTransienteDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private Uteis uteis;

	@Inject
	private FormaPagamentoDao formaPagamentoDao;

	@Inject
	private CondicaoPagamentoDao condicaoPagamentoDao;

	@Inject
	private RecebimentoDao recebimentoDao;

	@Inject
	private ClienteDao clienteDao;

	private List<ItemVendaDto> listItemsVenda = new ArrayList<ItemVendaDto>();
	private ItemVendaDto itemVendaDto = new ItemVendaDto();
	private List<ProdutoDto> listaProdutoDto = new ArrayList<ProdutoDto>();

	private List<FormaPagamentoDto> listaFormaPagamentoDto = new ArrayList<FormaPagamentoDto>();
	private List<CondicaoPagamentoDto> listaCondicaoPagamentoDto = new ArrayList<CondicaoPagamentoDto>();
	private FormaPagamentoDto formaPagamentoDto;
	private CondicaoPagamentoDto condicaoPagamentoDto;
	private List<RecebimentoDto> listaRecebimentoDto = new ArrayList<RecebimentoDto>();

	private String argumentoBusca;

	private BigDecimal correcao = BigDecimal.ZERO;
	private BigDecimal totalRecebimento = BigDecimal.ZERO;

	@PostConstruct
	public void carregarDados() {
		listaFormaPagamentoDto = formaPagamentoDao.buscarTodos().stream().map(FormaPagamentoDto::new).collect(Collectors.toList());
		listaCondicaoPagamentoDto = condicaoPagamentoDao.buscarTodos().stream().map(CondicaoPagamentoDto::new).collect(Collectors.toList());
	}

	public String chamarRecebimento() {
		try {
			token.validarToken();
			if (listItemsVenda.isEmpty()) {
				uteis.adicionarMensagemAdvertencia("É preciso ter ao menos um item na venda para prosseguir!");
				return null;
			}
			if (!validarCredito()) {
				uteis.adicionarMensagemAdvertencia("Limite de crédito excedido!");
				return null;
			}
			criarRecebimento();
			return "/ecommerce/paginas/processos/fechamentoVenda.xhtml";
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public BigDecimal getValorTotalVendaCorrigido() {
		BigDecimal totalVenda = getVendaDto().getTotalVenda();
		return totalVenda.add(totalVenda.multiply(correcao).divide(new BigDecimal("100.00"), 2, RoundingMode.HALF_UP));
	}

	private void criarRecebimento() {
		totalRecebimento = getValorTotalVendaCorrigido();
		listaRecebimentoDto.clear();
		if (condicaoPagamentoDto.getCodigo() == 1) {
			RecebimentoDto recebimentoDto = new RecebimentoDto();
			recebimentoDto.setCondicaopagamento(condicaoPagamentoDto);
			recebimentoDto.setFormaPagamentoDto(formaPagamentoDto);
			recebimentoDto.setDataEmissao(LocalDate.now());
			recebimentoDto.setDataVencimento(LocalDate.now());
			recebimentoDto.setQuitado(true);
			recebimentoDto.setValor(totalRecebimento);
			listaRecebimentoDto.add(recebimentoDto);
		} else {
			String[] array = condicaoPagamentoDto.getDescricao().split("\\s-\\s");
			String numeroParcelas = condicaoPagamentoDto.getNumeroParcelas().toString();
			BigDecimal valorCadaParcela = totalRecebimento.divide(new BigDecimal(numeroParcelas), 2,RoundingMode.HALF_UP);
			for (String parcela : array) {
				RecebimentoDto recebimentoDto = new RecebimentoDto();
				recebimentoDto.setCondicaopagamento(condicaoPagamentoDto);
				recebimentoDto.setFormaPagamentoDto(formaPagamentoDto);
				recebimentoDto.setDataEmissao(LocalDate.now());
				recebimentoDto.setDataVencimento(LocalDate.now().plusDays(Long.valueOf(parcela)));
				recebimentoDto.setValor(valorCadaParcela);
				recebimentoDto.setQuitado(formaPagamentoDto.isCompensado());
				listaRecebimentoDto.add(recebimentoDto);
			}
		}
	}

	public void adicionarItemVenda(AjaxBehaviorEvent e) {
		if (itemVendaDto.getIdProduto() == null) {
			uteis.adicionarMensagemAdvertencia("Por favor informe um item!");
			return;
		}
		if (!validarEstoque(itemVendaDto)) {
			uteis.adicionarMensagemAdvertencia("Estoque insuficiente: " + itemVendaDto.getNomeProduto());
			return;
		}
		criarEstoqueTransiente(itemVendaDto);
		BigDecimal resultado = itemVendaDto.getQuantidade().multiply(itemVendaDto.getValorUnitario()).setScale(2);
		itemVendaDto.setTotalUnitario(resultado);
		incrementarTotal(itemVendaDto);
		listItemsVenda.add(itemVendaDto);
		itemVendaDto = new ItemVendaDto();
	}

	public void selecionarProduto(ProdutoDto produto) {
		itemVendaDto = new ItemVendaDto(produto);
	}

	public void incrementarTotal(ItemVendaDto itemVendaDto) {
		BigDecimal totalVenda = vendaController.getVendaDto().getTotalVenda();
		getVendaDto().setTotalVenda(totalVenda.add(itemVendaDto.getTotalUnitario()));
	}

	public void pesquisarProduto() {
		List<Produto> listaProduto = produtoDao.buscarSimilaridade("descricao", argumentoBusca);
		listaProdutoDto = listaProduto.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}

	public void excluirItemVenda(ItemVendaDto item) {
		BigDecimal totalVenda = getVendaDto().getTotalVenda();
		getVendaDto().setTotalVenda(totalVenda.subtract(item.getTotalUnitario()));
		removerEstoqueTransiente(item);
		listItemsVenda.remove(item);
	}

	private boolean validarEstoque(ItemVendaDto item) {
		Produto p = produtoDao.getById(item.getIdProduto());
		BigDecimal resultado = estoqueTransienteDao.getEstoqueDisponivel(p);
		return resultado.subtract(item.getQuantidade()).compareTo(BigDecimal.ZERO) >= 0;
	}

	private void criarEstoqueTransiente(ItemVendaDto item) {
		Produto p = produtoDao.getById(item.getIdProduto());
		estoqueTransienteDao.processarAdicaoEstoqueTransiente(p, item.getQuantidade());
	}

	private void removerEstoqueTransiente(ItemVendaDto item) {
		Produto p = produtoDao.getById(item.getIdProduto());
		estoqueTransienteDao.processarRemocaoEstoqueTransiente(p, item.getQuantidade());
	}

	public String cancelar() {
		listItemsVenda.forEach(e -> removerEstoqueTransiente(e));
		listItemsVenda.clear();
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}

	public boolean validarCredito() {
		VendaDto vDto = vendaController.getVendaDto();
		Cliente c = clienteDao.getById(Integer.valueOf(vDto.getIdCliente()));
		BigDecimal totalRecebimentos = getSomaRecebimentos(c);
		BigDecimal totalConsumido = totalRecebimentos.add(vDto.getTotalVenda());
		return totalConsumido.compareTo(vDto.getLimiteCredito()) <= 0;
	}

	private BigDecimal getSomaRecebimentos(Cliente c) {
		List<Recebimento> listaRecebimento = recebimentoDao.getRecebimentosByCliente(c);
		BigDecimal somaRecebimentos = BigDecimal.ZERO;
		BigDecimal somaParcelasPagas = BigDecimal.ZERO;
		for (Recebimento r : listaRecebimento) {
			somaRecebimentos = somaRecebimentos.add(r.getValor());
			for (Parcela parcela : r.getListaParcelas()) {
				somaParcelasPagas.add(parcela.getValorParcela());
			}
		}
		return somaRecebimentos.subtract(somaParcelasPagas);
	}

	public VendaController getVendaController() {
		return vendaController;
	}

	public VendaDto getVendaDto() {
		return vendaController.getVendaDto();
	}

	public void setVendaController(VendaController vendaController) {
		this.vendaController = vendaController;
	}

	public GerenciadorConversa getConversa() {
		return conversa;
	}

	public void setConversa(GerenciadorConversa conversa) {
		this.conversa = conversa;
	}

	public GerenciadorToken getToken() {
		return token;
	}

	public void setToken(GerenciadorToken token) {
		this.token = token;
	}

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<ItemVendaDto> getListItemsVenda() {
		return listItemsVenda;
	}

	public void setListItemsVenda(List<ItemVendaDto> listItemsVenda) {
		this.listItemsVenda = listItemsVenda;
	}

	public ItemVendaDto getItemVendaDto() {
		return itemVendaDto;
	}

	public void setItemVendaDto(ItemVendaDto itemVendaDto) {
		this.itemVendaDto = itemVendaDto;
	}

	public String getArgumentoBusca() {
		return argumentoBusca;
	}

	public void setArgumentoBusca(String argumentoBusca) {
		this.argumentoBusca = argumentoBusca;
	}

	public ProdutoDao getProdutoDao() {
		return produtoDao;
	}

	public void setProdutoDao(ProdutoDao produtoDao) {
		this.produtoDao = produtoDao;
	}

	public List<ProdutoDto> getListaProdutoDto() {
		return listaProdutoDto;
	}

	public void setListaProdutoDto(List<ProdutoDto> listaProdutoDto) {
		this.listaProdutoDto = listaProdutoDto;
	}

	public EstoqueTransienteDao getEstoqueTransienteDao() {
		return estoqueTransienteDao;
	}

	public void setEstoqueTransienteDao(EstoqueTransienteDao estoqueTransienteDao) {
		this.estoqueTransienteDao = estoqueTransienteDao;
	}

	public FormaPagamentoDao getFormaPagamentoDao() {
		return formaPagamentoDao;
	}

	public void setFormaPagamentoDao(FormaPagamentoDao formaPagamentoDao) {
		this.formaPagamentoDao = formaPagamentoDao;
	}

	public CondicaoPagamentoDao getCondicaoPagamentoDao() {
		return condicaoPagamentoDao;
	}

	public void setCondicaoPagamentoDao(CondicaoPagamentoDao condicaoPagamentoDao) {
		this.condicaoPagamentoDao = condicaoPagamentoDao;
	}

	public List<FormaPagamentoDto> getListaFormaPagamentoDto() {
		return listaFormaPagamentoDto;
	}

	public void setListaFormaPagamentoDto(List<FormaPagamentoDto> listaFormaPagamentoDto) {
		this.listaFormaPagamentoDto = listaFormaPagamentoDto;
	}

	public List<CondicaoPagamentoDto> getListaCondicaoPagamentoDto() {
		return listaCondicaoPagamentoDto;
	}

	public void setListaCondicaoPagamentoDto(List<CondicaoPagamentoDto> listaCondicaoPagamentoDto) {
		this.listaCondicaoPagamentoDto = listaCondicaoPagamentoDto;
	}

	public FormaPagamentoDto getFormaPagamentoDto() {
		return formaPagamentoDto;
	}

	public void setFormaPagamentoDto(FormaPagamentoDto formaPagamentoDto) {
		this.formaPagamentoDto = formaPagamentoDto;
	}

	public CondicaoPagamentoDto getCondicaoPagamentoDto() {
		return condicaoPagamentoDto;
	}

	public void setCondicaoPagamentoDto(CondicaoPagamentoDto condicaoPagamentoDto) {
		this.condicaoPagamentoDto = condicaoPagamentoDto;
	}

	public RecebimentoDao getRecebimentoDao() {
		return recebimentoDao;
	}

	public void setRecebimentoDao(RecebimentoDao recebimentoDao) {
		this.recebimentoDao = recebimentoDao;
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public List<RecebimentoDto> getListaRecebimentoDto() {
		return listaRecebimentoDto;
	}

	public void setListaRecebimentoDto(List<RecebimentoDto> listaRecebimentoDto) {
		this.listaRecebimentoDto = listaRecebimentoDto;
	}

	public BigDecimal getCorrecao() {
		return correcao;
	}

	public void setCorrecao(BigDecimal correcao) {
		this.correcao = correcao;
	}

	public BigDecimal getTotalRecebimento() {
		return totalRecebimento;
	}

	public void setTotalRecebimento(BigDecimal resultadoRecebimento) {
		this.totalRecebimento = resultadoRecebimento;
	}

}
