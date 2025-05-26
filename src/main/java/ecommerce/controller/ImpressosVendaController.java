package ecommerce.controller;

import java.io.IOException;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import ecommerce.dao.VendaDao;
import ecommerce.relatorios.ComprovanteVendaDataSource;
import ecommerce.relatorios.Relatorio;
import ecommerce.uteis.jsf.AppException;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;
import net.sf.jasperreports.engine.JRException;

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
	
	@Inject
	private Relatorio relatorio;
	
	private List<String> listaOpcoes = Arrays.asList("Comprovante venda", "Comprovante de Venda 2 vias");
	
	private String opcaoSelecionada = listaOpcoes.get(0);
	
	private ComprovanteVendaDataSource dataSourceComprovanteVenda;
	
	public void processarOpcaoSelecionada() throws JRException, IOException, AppException {
		dataSourceComprovanteVenda = new ComprovanteVendaDataSource(itemVendaController);
		relatorio.setMap(dataSourceComprovanteVenda.getHasmapDatasource());
		relatorio.setDataSource(dataSourceComprovanteVenda.getCollectionDataSource());
		if (listaOpcoes.get(0).equals(opcaoSelecionada)) {
			relatorio.nomeRelatorio("/ecommerce/relatorios/ComprovanteVenda.jasper");
			relatorio.executarRelatorio();
		}else {
			HashMap<String, Object> hashMap = dataSourceComprovanteVenda.getHasmapDatasource();
			hashMap.put("viaCliente", "VIA DO CLIENTE");
			hashMap.put("viaEmpresa", "VIA DA EMPRESA");
			relatorio.nomeRelatorio("/ecommerce/relatorios/ComprovanteVendaDuasVias.jasper");
			relatorio.executarRelatorio();
		}
		
	}
	
	@Transactional
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
