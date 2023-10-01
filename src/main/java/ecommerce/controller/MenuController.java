package ecommerce.controller;

import java.io.Serializable;

import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class MenuController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GerenciadorConversa gerenciadorConversa;

	@Inject
	private Uteis uteis;

	public String acessarMenuCadastro() {
		gerenciadorConversa.finalizar();
		return uteis.getCaminhoMenuCadastros();
	}

	public String acessarMenuProcesso() {
		gerenciadorConversa.finalizar();
		return uteis.getCaminhoMenuProcesso();
	}

	public String acessarMenuRelatorio() {
		gerenciadorConversa.finalizar();
		return uteis.getCaminhoMenuRelatorio();
	}

	public String acessarMenuInicial() {
		return uteis.getCaminhoInicial();
	}

}
