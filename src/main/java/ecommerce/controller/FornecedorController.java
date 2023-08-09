package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ecommerce.dao.CidadeDao;
import ecommerce.dao.FornecedorDao;
import ecommerce.dto.FornecedorDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Transactional
@ConversationScoped
public class FornecedorController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private LoginController loginController;
	
	@Inject
	private FornecedorDao fornecedorDao;
	
	@Inject
	private CidadeDao cidadeDao;
	
	@Inject
	private Uteis uteis;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private GerenciadorToken token;
	
	private FornecedorDto fornecedorDto;
	
	private final String paginaConsulta = "/ecommerce/cadastros/consultarFornecedor.xhtml";
	private final String paginaCadastro = "/ecommerce/cadastros/cadastrarFornecedor.xhtml";

	private final List<String> opcoesBusca = Arrays.asList("Nome", "Cidade", "Código");
	private String argumentoBusca;
	private String opcaoBuscaSelecionada;
	
	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Consultar fornecedor");
			return null;
		} catch (PermissaoExeption e) {
			return null;
		}
	}
	
	
	
	
	public LoginController getLoginController() {
		return loginController;
	}
	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}
	public FornecedorDao getFornecedorDao() {
		return fornecedorDao;
	}
	public void setFornecedorDao(FornecedorDao fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
	}
	public CidadeDao getCidadeDao() {
		return cidadeDao;
	}
	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}
	public Uteis getUteis() {
		return uteis;
	}
	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
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
	public FornecedorDto getFornecedorDto() {
		return fornecedorDto;
	}
	public void setFornecedorDto(FornecedorDto fornecedorDto) {
		this.fornecedorDto = fornecedorDto;
	}
	public String getArgumentoBusca() {
		return argumentoBusca;
	}
	public void setArgumentoBusca(String argumentoBusca) {
		this.argumentoBusca = argumentoBusca;
	}
	public String getOpcaoBuscaSelecionada() {
		return opcaoBuscaSelecionada;
	}
	public void setOpcaoBuscaSelecionada(String opcaoBuscaSelecionada) {
		this.opcaoBuscaSelecionada = opcaoBuscaSelecionada;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPaginaConsulta() {
		return paginaConsulta;
	}
	public String getPaginaCadastro() {
		return paginaCadastro;
	}
	public List<String> getOpcoesBusca() {
		return opcoesBusca;
	}
	
	

}
