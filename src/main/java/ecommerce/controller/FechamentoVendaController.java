package ecommerce.controller;

import java.io.Serializable;
import java.util.List;

import ecommerce.dto.RecebimentoDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class FechamentoVendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GerenciadorToken token;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private VendaController vendaController;
	
	@Inject
	private ItemVendaController itemVendaController;
	
	private List<RecebimentoDto> recebimentos;
	
	@PostConstruct
	public void carregarRecebimentos() {
		recebimentos = itemVendaController.getListaRecebimentoDto();
	}
	
	public void editarRecebimento(RecebimentoDto recebimentoDto) {
		
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

	public VendaController getVendaController() {
		return vendaController;
	}

	public void setVendaController(VendaController vendaController) {
		this.vendaController = vendaController;
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
	
	

}
