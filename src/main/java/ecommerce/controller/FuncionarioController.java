package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cidade;
import ecommerce.beans.Funcionario;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.FuncionarioDto;
import ecommerce.dto.PermissaoDto;
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

@ConversationScoped
@Transactional
@Named
public class FuncionarioController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CidadeDao cidadeDao;

	@Inject
	private FuncionarioDao funcionarioDao;

	@Inject
	private LoginController loginController;

	@Inject
	private PermissaoDao permissaoDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private Uteis uteis;

	private List<FuncionarioDto> listaFuncionarioDto = new ArrayList<FuncionarioDto>();
	private List<PermissaoDto> listaPermissaoExistente;
	private FuncionarioDto funcionarioDto;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarFuncionario.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarFuncionario.xhtml";
	private List<String> opcaoBusca = Arrays.asList("Nome", "Código", "Cidade");
	private String opcaoBuscaSelecionada;
	private String argumentoBusca;
	private String mensagem;
	private boolean inclusao = false;
	private boolean marcarTodos = true;

	@PostConstruct
	public void carregarPermissoes() {
		listaPermissaoExistente = permissaoDao.listarTodos().stream().map(PermissaoDto::new)
				.collect(Collectors.toList());
	}

	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Pesquisar funcionario");
			token.gerarToken();
			listaFuncionarioDto = funcionarioDao.buscarUltimosCadastrados().stream().map(FuncionarioDto::new)
					.collect(Collectors.toList());
			atualizarMensagem();
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar funcionario");
			token.gerarToken();
			funcionarioDto = new FuncionarioDto();
			carregarPermissoes();
			argumentoBusca = "";
			inclusao = true;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	private Funcionario criarFuncionario() {
		return funcionarioDto.toFuncionario(permissaoDao, cidadeDao);
	}

	public String confirmar() {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			Funcionario f = criarFuncionario();
			if (inclusao) {
				funcionarioDao.cadastrar(f);
			} else {
				funcionarioDao.editar(f);
			}
			if (f.getCodigo().equals(loginController.getFuncionarioDto().getCodigo())) {
				loginController.getFuncionarioDto().setListaPermissoes(funcionarioDto.getListaPermissoes());
			}
			uteis.adicionarMensagemSucessoRegistro();
			return paginaConsulta;
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	private boolean validarDados() {
		boolean resultado = false;
		List<Funcionario> listaFuncionario = funcionarioDao.buscarSimilaridade("login", funcionarioDto.getLogin());
		if (listaFuncionario.isEmpty()) {
			resultado = true;
		} else if (listaFuncionario.size() > 1) {
			resultado = false;
		} else {
			Funcionario f = listaFuncionario.get(0);
			if (funcionarioDto.getCodigo().equals(f.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("Funcionario com login já cadastrado!");
		}
		return resultado;
	}

	private void atualizarMensagem() {
		mensagem = listaFuncionarioDto.isEmpty() ? "Não há funcionarios cadastrados."
				: "Ultimos funcionarios cadastrados.";
	}

	public void atualizarPesquisa() {
		try {
			if ("Código".equals(opcaoBuscaSelecionada)) {
				listaFuncionarioDto.clear();
				Funcionario f = funcionarioDao.getById(Integer.valueOf(argumentoBusca));
				if (f != null) {
					listaFuncionarioDto.add(new FuncionarioDto(f));
				}
			} else if ("Nome".equals(opcaoBuscaSelecionada)) {
				listaFuncionarioDto = funcionarioDao.buscarSimilaridade("nome", argumentoBusca).stream()
						.map(FuncionarioDto::new).collect(Collectors.toList());
			} else {
				listaFuncionarioDto = funcionarioDao.buscarSimilaridade("cidade", argumentoBusca).stream()
						.map(FuncionarioDto::new).collect(Collectors.toList());
			}
			atualizarMensagem();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de busca é invalido!");
		}
	}

	public String prepararAlteracao(Integer id) {
		try {
			loginController.possuiPermissao("Alterar funcionario");
			token.gerarToken();
			funcionarioDto = new FuncionarioDto(funcionarioDao.realizarlogin("1","1").get());
			inclusao = false;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String excluir(Integer id) {

		try {
			loginController.possuiPermissao("Excluir funcionario");
			token.validarToken();
			funcionarioDao.excluir(id);
			atualizarPesquisa();
			return paginaConsulta;
		} catch (PermissaoExeption | TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void selecionarTodos() {
		funcionarioDto.getListaPermissoes().clear();
		if (marcarTodos) {
			funcionarioDto.getListaPermissoes().addAll(listaPermissaoExistente);
		}
		marcarTodos = !marcarTodos;
	}

	public void carregarCidade(AjaxBehaviorEvent e) {
		try {
			String id = funcionarioDto.getIdCidade();
			Cidade c = cidadeDao.getById(Integer.valueOf(id));
			mostrarCidade(c);
		} catch (NumberFormatException e1) {
			uteis.adicionarMensagemAdvertencia("Argumento de pesquisa inválido!");
			mostrarCidade(null);
		}
	}

	private void mostrarCidade(Cidade c) {
		if (c != null) {
			funcionarioDto.setIdCidade(Integer.toString(c.getCodigo()));
			funcionarioDto.setNomeCidade(c.getNome());
		} else {
			funcionarioDto.setIdCidade(null);
			funcionarioDto.setNomeCidade(null);
		}
	}

	public String cancelar() {
		funcionarioDto = null;
		return paginaConsulta;
	}

	public CidadeDao getCidadeDao() {
		return cidadeDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public FuncionarioDao getFuncionarioDao() {
		return funcionarioDao;
	}

	public void setFuncionarioDao(FuncionarioDao funcionarioDao) {
		this.funcionarioDao = funcionarioDao;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public PermissaoDao getPermissaoDao() {
		return permissaoDao;
	}

	public void setPermissaoDao(PermissaoDao permissaoDao) {
		this.permissaoDao = permissaoDao;
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

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public List<FuncionarioDto> getListaFuncionarioDto() {
		return listaFuncionarioDto;
	}

	public void setListaFuncionarioDto(List<FuncionarioDto> listaFuncionarioDto) {
		this.listaFuncionarioDto = listaFuncionarioDto;
	}

	public List<PermissaoDto> getListaPermissaoExistente() {
		return listaPermissaoExistente;
	}

	public void setListaPermissaoExistente(List<PermissaoDto> listaPermissaoExistente) {
		this.listaPermissaoExistente = listaPermissaoExistente;
	}

	public FuncionarioDto getFuncionarioDto() {
		return funcionarioDto;
	}

	public void setFuncionarioDto(FuncionarioDto funcionarioDto) {
		this.funcionarioDto = funcionarioDto;
	}

	public List<String> getOpcaoBusca() {
		return opcaoBusca;
	}

	public void setOpcaoBusca(List<String> opcaoBusca) {
		this.opcaoBusca = opcaoBusca;
	}

	public String getOpcaoBuscaSelecionada() {
		return opcaoBuscaSelecionada;
	}

	public void setOpcaoBuscaSelecionada(String opcaoBuscaSelecionada) {
		this.opcaoBuscaSelecionada = opcaoBuscaSelecionada;
	}

	public String getArgumentoBusca() {
		return argumentoBusca;
	}

	public void setArgumentoBusca(String argumentoBusca) {
		this.argumentoBusca = argumentoBusca;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
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

}
