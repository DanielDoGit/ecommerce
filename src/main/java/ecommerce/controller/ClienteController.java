package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cidade;
import ecommerce.beans.Cliente;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.ClienteDao;
import ecommerce.dto.CidadeDto;
import ecommerce.dto.ClienteDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.TokenException;
import ecommerce.uteis.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Transactional
@ConversationScoped
@Named
public class ClienteController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDao cidadeDao;

	@Inject
	private ClienteDao clienteDao;

	@Inject
	private LoginController loginController;

	@Inject
	private Uteis uteis;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	private ClienteDto clienteDto;
	private CidadeDto cidadeDto;
	private List<ClienteDto> listaClienteDto;
	private List<CidadeDto> listaCidadeDto;

	private String mensagem;
	private String argumentoBusca;
	private boolean inclusao;

	private final List<String> opcoesBusca = Arrays.asList("Nome", "Cidade", "Código");
	private String opcaoBuscaSelecionada;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarCliente.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarCliente.xhtml";

	@PostConstruct
	private void inicializar() {
		listaClienteDto = new ArrayList<ClienteDto>();
		listaCidadeDto = new ArrayList<CidadeDto>();
	}

	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Consultar cliente");
			token.gerarToken();
			listaClienteDto = clienteDao.buscarUltimosCadastrados().stream().map(ClienteDto::new).collect(Collectors.toList());
			mensagem = listaClienteDto.isEmpty() ? "Não há clientes cadastrados." : "Ultimos clientes cadastrados...";
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	private void atualizarMensagem() {
		mensagem = listaClienteDto.isEmpty() ? "Não há clientes cadastrados com o argumento passado."
				: "Quantidade de clientes cadastrados: " + listaClienteDto.size();
	}
	
	public void atualizarPesquisa() {
		try {
			if ("Código".equals(opcaoBuscaSelecionada)) {
				listaClienteDto.clear();
				Cliente c = clienteDao.getById(Integer.valueOf(argumentoBusca));
				if (c != null) {
					listaClienteDto.add(new ClienteDto(c));
				}
			} else if ("Nome".equals(opcaoBuscaSelecionada)) {
				listaClienteDto = clienteDao.buscarSimilaridade("nome", argumentoBusca).stream()
						.map(ClienteDto::new).collect(Collectors.toList());
			} else {
				listaClienteDto = clienteDao.buscarSimilaridadeInnerJoin(Cidade.class, "nome", argumentoBusca).stream()
						.map(ClienteDto::new).collect(Collectors.toList());
			}
			atualizarMensagem(); 
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de busca é invalido!");
		}
	}
	
	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar cliente");
			token.gerarToken();
			clienteDto = new ClienteDto();
			clienteDto.setDataCadastro(LocalDate.now());
			clienteDto.setCredito(BigDecimal.ZERO);
			argumentoBusca = "";
			inclusao = true;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	public String prepararAlteracao(Integer id) {
		try {
			loginController.possuiPermissao("Alterar cliente");
			token.gerarToken();
			clienteDto = new ClienteDto(clienteDao.getById(id));
			argumentoBusca = "";
			inclusao = false;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	

	public String excluir(Integer id) {
		try {
			token.validarToken();
			loginController.possuiPermissao("Excluir cliente");
			clienteDao.excluir(id);
			atualizarPesquisa();
			uteis.adicionarMensagemSucessoExclusao();
			return paginaConsulta;
		} catch (PermissaoExeption | TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
		
	}

	public void pesquisarCidade() {
		List<Cidade> listaCidade = cidadeDao.consultarCidadeNome(argumentoBusca);
		listaCidadeDto = listaCidade.stream().map(CidadeDto::new).collect(Collectors.toList());
	}

	public void selecionarCidade(CidadeDto t) {
		mostrarCidade(t.toCidade());
	}

	public void mostrarCidade(Cidade c) {
		if (c != null) {
			clienteDto.setIdCidade(c.getCodigo().toString());
			clienteDto.setNomeCidade(c.getNome());
		} else {
			clienteDto.setIdCidade(null);
			clienteDto.setNomeCidade(null);
		}
	}
	
	public void carregarCidade(AjaxBehaviorEvent e) {
		try {
			Cidade c = cidadeDao.getById(Integer.valueOf(clienteDto.getIdCidade()));
			mostrarCidade(c);
		} catch (NumberFormatException e1) {
			uteis.adicionarMensagemAdvertencia("Argumento de pesquisa inválido!");
			mostrarCidade(null);
		}
	}
	
	public String confirmar() {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			Cliente cli = clienteDto.toCliente(cidadeDao);
			if (inclusao) {
				clienteDao.cadastrar(cli);
			}else {
				clienteDao.editar(cli);
			}
			atualizarPesquisa();	
			uteis.adicionarMensagemSucessoRegistro();
			return paginaConsulta;
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	private boolean validarDados() {
		boolean resultado = false;
		List<Cliente> listaCliente = clienteDao.buscarSimilaridade("nome", clienteDto.getNome());
		if (listaCliente.isEmpty()) {
			resultado = true;
		} else if (listaCliente.size() > 1) {
			resultado = false;
		} else {
			Cliente f = listaCliente.get(0);
			if (clienteDto.getCodigo().equals(f.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("Cliente com nome já cadastrado!");
		}
		return resultado;
	}
	
	public String cancelar() {
		return paginaConsulta;
	}
	

	public CidadeDao getCidadeDao() {
		return cidadeDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
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

	public ClienteDto getClienteDto() {
		return clienteDto;
	}

	public void setClienteDto(ClienteDto clienteDto) {
		this.clienteDto = clienteDto;
	}

	public CidadeDto getCidadeDto() {
		return cidadeDto;
	}

	public void setCidadeDto(CidadeDto cidadeDto) {
		this.cidadeDto = cidadeDto;
	}

	public List<ClienteDto> getListaClienteDto() {
		return listaClienteDto;
	}

	public void setListaClienteDto(List<ClienteDto> listaClienteDto) {
		this.listaClienteDto = listaClienteDto;
	}

	public List<CidadeDto> getListaCidadeDto() {
		return listaCidadeDto;
	}

	public void setListaCidadeDto(List<CidadeDto> listaCidadeDto) {
		this.listaCidadeDto = listaCidadeDto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
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

	public List<String> getOpcoesBusca() {
		return opcoesBusca;
	}

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
	}

}
