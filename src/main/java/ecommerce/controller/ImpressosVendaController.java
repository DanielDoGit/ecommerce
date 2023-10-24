package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ecommerce.dao.VendaDao;
import ecommerce.relatorios.ComprovanteVendaDataSource;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@ConversationScoped
@Named
public class ImpressosVendaController implements Serializable {

	private static final long serialVersionUID = -1L;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private Uteis uteis;
	
	@Inject
	private ItemVendaController itemVendaController;
	
	@Inject
	private VendaDao vendaDao;
	
	private List<String> listaOpcoes = Arrays.asList("Comprovante venda", "Comprovante de Venda 2 vias");
	
	private String opcaoSelecionada;
	
	private ComprovanteVendaDataSource dataSourceComprovanteVenda;
	
	@PostConstruct
	public void carregarDados() {
		dataSourceComprovanteVenda = new ComprovanteVendaDataSource(itemVendaController);
	}
	
	public String cancelar() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}

	public GerenciadorConversa getConversa() {
		return conversa;
	}

	public void setConversa(GerenciadorConversa conversa) {
		this.conversa = conversa;
	}

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public ItemVendaController getItemVendaController() {
		return itemVendaController;
	}

	public void setItemVendaController(ItemVendaController itemVendaController) {
		this.itemVendaController = itemVendaController;
	}

	public VendaDao getVendaDao() {
		return vendaDao;
	}

	public void setVendaDao(VendaDao vendaDao) {
		this.vendaDao = vendaDao;
	}

	public List<String> getListaOpcoes() {
		return listaOpcoes;
	}

	public void setListaOpcoes(List<String> listaOpcoes) {
		this.listaOpcoes = listaOpcoes;
	}

	public ComprovanteVendaDataSource getDataSourceComprovanteVenda() {
		return dataSourceComprovanteVenda;
	}

	public void setDataSourceComprovanteVenda(ComprovanteVendaDataSource dataSourceComprovanteVenda) {
		this.dataSourceComprovanteVenda = dataSourceComprovanteVenda;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOpcaoSelecionada() {
		return opcaoSelecionada;
	}

	public void setOpcaoSelecionada(String opcaoSelecionada) {
		this.opcaoSelecionada = opcaoSelecionada;
	}
	
	

}
