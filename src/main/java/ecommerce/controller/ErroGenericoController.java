package ecommerce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.Charset;

import ecommerce.uteis.jsf.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

@Named
@RequestScoped
public class ErroGenericoController {

	private String mensagem;

	@Inject
	private ServletContext servletContext;
	
	@Inject
	private Uteis uteis;

	@PostConstruct
	public void inicializar() {
		try (FileInputStream fis = new FileInputStream(new File(servletContext.getInitParameter("caminhoLog")))) {
			byte[] bytes = new byte[fis.available()];
			fis.read(bytes);
			mensagem = new String(bytes, Charset.forName("UTF-8"));
		} catch (Exception e) {
			mensagem = "Não foi possível carregar o erro ocorrido. Por favor contate o suporte!";
		}
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

}
