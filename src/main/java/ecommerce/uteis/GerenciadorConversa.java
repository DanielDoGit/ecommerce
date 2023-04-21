package ecommerce.uteis;

import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class GerenciadorConversa {

	@Inject
	private Conversation conversation;

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

}
