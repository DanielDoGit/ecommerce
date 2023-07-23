package ecommerce.uteis;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

@Named
@RequestScoped
public class Uteis implements Serializable {
	private static final long serialVersionUID = 1L;

	private DateTimeFormatter padraoFormatacaoData = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Inject
	private ServletContext servletContext;
	

	public String getCaminhoInicial() {
		return "/ecommerce/paginas/uteis/inicial.xhtml";
	}

	public String getCaminhoMenuCadastros() {
		return "/ecommerce/paginas/uteis/menucadastro.xhtml";
	}

	public String getCaminhoMenuProcesso() {
		return "/ecommerce/paginas/uteis/menuprocesso.xhtml";
	}

	public String getCaminhoMenuRelatorio() {
		return "/ecommerce/paginas/uteis/menurelatorio.xhtml";
	}
	

	public String getCaminhoLogin() {
		return "/ecommerce/paginas/uteis/login.xhtml";
	}
	
	
	public void adicionarMensagemSucessoExclusao() {
		adicionarMensagemInformativa("Exclusão realizada com sucesso!");
	}

	public void adicionarMensagemRelatorioInexistente() {
		adicionarMensagemAdvertencia("Relatório inexistente!");
	}

	public void adicionarMensagemErro(Exception e) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		String mensagem = e.getMessage();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, mensagem, mensagem);
		facesContext.addMessage(null, facesMessage);
	}

	public void adicionarMensagemInformativa(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, mensagem, "");
		facesContext.addMessage(null, facesMessage);
	}

	public void adicionarMensagemAdvertencia(String mensagem) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem, "");
		facesContext.addMessage(null, facesMessage);
	}

	public String extrairNumeros(String text) {
		String newText = "";
		int result;
		for (int i = 0; i < text.length(); i++) {
			result = (int) text.charAt(i);
			if (result >= 48 && result <= 57) {
				newText = newText.concat(String.valueOf(text.charAt(i)));
			}
		}
		return newText;
	}

	public DateTimeFormatter getPadraoFormatacaoData() {
		return padraoFormatacaoData;
	}

}
