package ecommerce.uteis.jsf;

import ecommerce.uteis.interseptor.ItemVendaInterceptor;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.interceptor.Interceptors;

@RequestScoped
public class GerenciadorConversa {

	@Inject
	private Conversation conversation;

	@Interceptors(ItemVendaInterceptor.class)
	public void finalizar() {
		if (!conversation.isTransient()) {
			conversation.end();
		}
	}

	public void iniciar() {
		if (conversation.isTransient()) {
			conversation.setTimeout(10 * 60 * 1000);
			conversation.begin();
		}
	}

	public Conversation getConversation() {
		return conversation;
	}

}
