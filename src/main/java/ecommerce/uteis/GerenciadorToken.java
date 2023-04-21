package ecommerce.uteis;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class GerenciadorToken implements Serializable {

	private static final long serialVersionUID = 1L;

	private String clienteToken;

	private String servidorToken;

	public String getClienteToken() {
		return clienteToken;
	}

	public void setClienteToken(String clienteToken) {
		this.clienteToken = clienteToken;
	}

	public String getServidorToken() {
		return servidorToken;
	}

	public void setServidorToken(String servidorToken) {
		this.servidorToken = servidorToken;
	}

	public void gerarToken() {
		servidorToken = "key" + String.valueOf(System.currentTimeMillis());
		clienteToken = servidorToken;
	}

	public void validarToken() throws TokenException{
		boolean result = servidorToken.equals(clienteToken) ? true : false;
		gerarToken();
		if (!result) {
			throw new TokenException();
		}
	}

}