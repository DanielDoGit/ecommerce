package ecommerce.controller;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import ecommerce.dto.RecebimentoDto;
import ecommerce.relatorios.Relatorio;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
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
	private Uteis uteis;
	
	@Inject
	private VendaController vendaController;
	
	@Inject
	private ItemVendaController itemVendaController;
	
	@Inject
	private Relatorio relatorio;
	
	private List<RecebimentoDto> recebimentos;
	
	@PostConstruct
	public void carregarRecebimentos() {
		recebimentos = itemVendaController.getListaRecebimentoDto();
	}
	
	public void editarRecebimento(Integer id) {
		
		try {
			relatorio.nomeRelatorio("ComprovanteVenda.jasper");
			relatorio.executarRelatorio();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			token.validarToken();
			RecebimentoDto recebOptional = recebimentos.stream().filter( e-> e.getCodigo() == id).findFirst().get();
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
		}		
	}
	
	public String voltar() {
		return vendaController.chamarItensVenda();
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
