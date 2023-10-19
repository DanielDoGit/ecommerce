package ecommerce.uteis.jsf;

import ecommerce.controller.LoginController;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.inject.Inject;
import jakarta.servlet.ServletContext;

@SuppressWarnings("deprecation")
public class JsfExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	@Inject
	private ServletContext servletContext;

	@Inject
	private LoginController loginUsuario;

	public JsfExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	public ExceptionHandler getExceptionHandler() {
		String caminhoLog;
		if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
			caminhoLog = servletContext.getInitParameter("caminhoLogLinux");
		}else {
			caminhoLog = servletContext.getInitParameter("caminhoLogWindows");
		}
		return new JsfExceptionHandler(parent.getExceptionHandler(), caminhoLog);
	}

}
