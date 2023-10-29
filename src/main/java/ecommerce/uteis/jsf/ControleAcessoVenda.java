package ecommerce.uteis.jsf;

import ecommerce.controller.FechamentoVendaController;
import jakarta.faces.context.FacesContext;
import jakarta.faces.event.PhaseEvent;
import jakarta.faces.event.PhaseId;
import jakarta.faces.event.PhaseListener;
import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;

public class ControleAcessoVenda implements PhaseListener {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private FechamentoVendaController fechamentoVendaController;
	
	@Override
	public void afterPhase(PhaseEvent event) {
		try {
			FacesContext context = event.getFacesContext();
			if (context.getViewRoot() != null) {
				if (!context.getViewRoot().getViewId().equals("/ecommerce/paginas/processos/impressosVenda.xhtml")) {
					if (fechamentoVendaController.isVendaRealizada()) {
						HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
						context.getExternalContext().redirect(request.getContextPath() + "/ecommerce/paginas/processos/impressosVenda.xhtml");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void beforePhase(PhaseEvent event) {

	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RESTORE_VIEW;
	}

}
