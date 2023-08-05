package ecommerce.uteis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.faces.context.ExceptionHandler;
import jakarta.faces.context.ExceptionHandlerWrapper;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.ExceptionQueuedEvent;
import jakarta.faces.event.ExceptionQueuedEventContext;

@SuppressWarnings("deprecation")
@RequestScoped
public class JsfExceptionHandler extends ExceptionHandlerWrapper {

	private ExceptionHandler wrapped;

	private String caminhoLog;
	
	public JsfExceptionHandler(ExceptionHandler wrapped, String caminhoLog) {
		this.wrapped = wrapped;
		this.caminhoLog = caminhoLog;
	}

	@Override
	public ExceptionHandler getWrapped() {
		return this.wrapped;
	}

	@Override
	public void handle() throws FacesException {
		Iterator<ExceptionQueuedEvent> events = getUnhandledExceptionQueuedEvents().iterator();
		while (events.hasNext()) {
			ExceptionQueuedEvent event = events.next();
			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
			Throwable exception = context.getException();
			gravarLog(exception);
			redirect("/ecommerce/paginas/uteis/errogenerico.xhtml");
			events.remove();
		}
		getWrapped().handle();
	}

	private void gravarLog(Throwable throwable) {
		try (FileOutputStream fos = new FileOutputStream(new File(caminhoLog))) {
			PrintWriter pw = new PrintWriter(fos);
			throwable.printStackTrace(pw);
			pw.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void redirect(String page) {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			String contextPath = externalContext.getRequestContextPath();
			externalContext.redirect(contextPath + page);
			facesContext.responseComplete();
		} catch (IOException e) {
			throw new FacesException(e);
		}
	}

}
