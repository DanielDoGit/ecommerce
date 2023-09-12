package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Produto;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dto.ItemVendaDto;
import ecommerce.dto.ProdutoDto;
import ecommerce.dto.VendaDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@ConversationScoped
@Named
@Transactional
public class ItemVendaController implements Serializable{

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
	
	private List<ItemVendaDto> listItemsVenda = new ArrayList<ItemVendaDto>();
	private ItemVendaDto itemVendaDto = new ItemVendaDto();
	private List<ProdutoDto> listaProdutoDto = new ArrayList<ProdutoDto>();
 	
	private String argumentoBusca;
	
	public String chamarFormaPagamento() {
		if (validarCredito()){
			uteis.adicionarMensagemAdvertencia("Limite de crédito excedido!");
			return null;
		}
		return null;
	}
	
	public void adicionarItemVenda(AjaxBehaviorEvent e) {
		if (itemVendaDto.getIdProduto() == null) {
			uteis.adicionarMensagemAdvertencia("Por favor informe um item!");
			return;
		}
		if (validarEstoque(itemVendaDto)) {
			uteis.adicionarMensagemAdvertencia("Estoque insuficiente: "+itemVendaDto.getNomeProduto());
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
		vendaController.getVendaDto().setTotalVenda(totalVenda.add(itemVendaDto.getTotalUnitario()));
	}
	
	public void pesquisarProduto() {
		List<Produto> listaProduto = produtoDao.buscarSimilaridade("descricao", argumentoBusca);
		listaProdutoDto = listaProduto.stream().map(ProdutoDto::new).collect(Collectors.toList());
	}
	
	public void excluirItemVenda(ItemVendaDto item) {
		BigDecimal totalVenda = vendaController.getVendaDto().getTotalVenda();
		vendaController.getVendaDto().setTotalVenda(totalVenda.subtract(item.getTotalUnitario()));
		removerEstoqueTransiente(item);
		listItemsVenda.remove(item);
	}
	
	private boolean validarEstoque(ItemVendaDto item) {
		BigDecimal resultado = estoqueTransienteDao.getEstoqueDisponivel(LocalDate.now(), produtoDao.getById(item.getIdProduto()));
		if (item.getQuantidade().compareTo(BigDecimal.ZERO) <= 0) {
			return true;
		}
		return resultado.subtract(item.getQuantidade()).compareTo(BigDecimal.ZERO) == -1;
	}	
	
	private void criarEstoqueTransiente(ItemVendaDto item){
		Produto p = produtoDao.getById(item.getIdProduto());
		BigDecimal resultado = estoqueTransienteDao.getEstoqueDisponivel(LocalDate.now(), p);
		BigDecimal quantidadeDisponivel = resultado.subtract(item.getQuantidade());
		estoqueTransienteDao.processarAdicaoEstoqueTransiente(p, quantidadeDisponivel);
	}
	
	private void removerEstoqueTransiente(ItemVendaDto item){
		Produto p = produtoDao.getById(item.getIdProduto());
		BigDecimal resultado = estoqueTransienteDao.getEstoqueDisponivel(LocalDate.now(), p);
		BigDecimal quantidadeDisponivel = resultado.add(item.getQuantidade());
		estoqueTransienteDao.processarRemocaoEstoqueTransiente(p, quantidadeDisponivel);
	}
	
	public String cancelar() {
		listItemsVenda.forEach(e -> removerEstoqueTransiente(e));
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}
	
	public boolean validarCredito() {
		VendaDto vDto = vendaController.getVendaDto();
		return vDto.getTotalVenda().compareTo(vDto.getLimiteCredito()) == 1;
	}

	public VendaController getVendaController() {
		return vendaController;
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

}
