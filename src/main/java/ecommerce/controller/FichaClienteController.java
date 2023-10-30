package ecommerce.controller;

import java.io.Serializable;

import ecommerce.dao.ClienteDao;
import ecommerce.dto.ClienteDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.PermissaoExeption;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@ConversationScoped
@Named
@Transactional
public class FichaClienteController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private ClienteDao clienteDao;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private GerenciadorToken token;
	
	@Inject
	private LoginController controller;
	
	@Inject
	private Uteis uteis;
	
	private ClienteDto clienteDto;
	
	public String identificarCliente() {
		try {
			conversa.iniciar();
			token.gerarToken();
			controller.possuiPermissao("Acessar ficha do cliente");
			clienteDto = new ClienteDto();
			return "/ecommerce/paginas/processos/fichaCliente.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public GerenciadorConversa getConversa() {
		return conversa;
	}

	public void setConversa(GerenciadorConversa conversa) {
		this.conversa = conversa;
	}

	public GerenciadorToken getToken() {
		return token;
	}

	public void setToken(GerenciadorToken token) {
		this.token = token;
	}

	public LoginController getController() {
		return controller;
	}

	public void setController(LoginController controller) {
		this.controller = controller;
	}

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
