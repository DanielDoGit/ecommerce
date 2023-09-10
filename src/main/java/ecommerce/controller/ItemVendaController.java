package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Produto;
import ecommerce.dao.ProdutoDao;
import ecommerce.dto.ItemVendaDto;
import ecommerce.dto.ProdutoDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
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
	private GerenciadorConversa conversa;
	
	@Inject
	private GerenciadorToken token;
	
	@Inject
	private Uteis uteis;
	
	private List<ItemVendaDto> listItemsVenda = new ArrayList<ItemVendaDto>();
	private ItemVendaDto itemVendaDto;
	private List<ProdutoDto> listaProdutoDto = new ArrayList<ProdutoDto>();
 	
	private String argumentoBusca;
	
	public String chamarRecebimento() {
		return null;
	}
	
	public void adicionarItemVenda(ItemVendaDto itemVendaDto) {
		listItemsVenda.add(itemVendaDto);
		incrementarTotal(itemVendaDto);
	}
	
	public void selecionarProduto(ProdutoDto produto) {
		itemVendaDto = new ItemVendaDto(produto);
		adicionarItemVenda(itemVendaDto);
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
		listItemsVenda.remove(item);
		BigDecimal totalVenda = vendaController.getVendaDto().getTotalVenda();
		vendaController.getVendaDto().setTotalVenda(totalVenda.subtract(item.getTotalUnitario()));
	}
	
	public String cancelar() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
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

}
