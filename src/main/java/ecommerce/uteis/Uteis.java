package ecommerce.uteis;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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

	public void adicionarMensagemSucessoRegistro() {
		adicionarMensagemInformativa("Registro salvo com sucesso!");
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

	public void adicionarMensagemRegistroConstraintViolation() {
		adicionarMensagemAdvertencia("O registro que você está excluindo, está sendo usado por outros registros!");
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

	public <T, K> List<K> transformListToDto(List<T> lista, Class<K> klass) throws Exception {
		List<K> listaTransformada = new ArrayList<K>();
		for (T o : lista) {
			listaTransformada.add(klass.getDeclaredConstructor(o.getClass()).newInstance(o));
		}
		return listaTransformada;
	}
	
	public boolean isNumber(String arg) {
		try {
			Integer.parseInt(arg);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	public boolean isPositiveNumber(String arg) {
		try {
			return Integer.parseInt(arg) > 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	public DateTimeFormatter getPadraoFormatacaoData() {
		return padraoFormatacaoData;
	}

}
