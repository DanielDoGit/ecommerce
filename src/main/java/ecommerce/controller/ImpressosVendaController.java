package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ecommerce.dto.ItemVendaDto;
import ecommerce.dto.RecebimentoDto;
import ecommerce.dto.VendaDto;
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
	
	private List<String> listaOpcoes = Arrays.asList("Comprovante venda", "Comprovante de Venda 2 vias");
	
	private ComprovanteVendaDataSource dataSourceComprovanteVenda;
	
	@PostConstruct
	public void carregarDados() {
		dataSourceComprovanteVenda = new ComprovanteVendaDataSource(itemVendaController);
	}
	
	public String cancelar() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}
	
	

}
