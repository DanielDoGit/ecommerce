package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
	
	private final String paginaConsulta = "";
	private final String paginaCadastro = "";
	private List<String> opcaoBusca = Arrays.asList("Nome", "Código", "Cidade");
	private String opcaoBuscaSelecionada;
	private String argumentoBusca;
	private String mensagem;
	private boolean inclusao = false;
	

	@PostConstruct
	public void carregarPermissoes() {
		listaPermissaoExistente = permissaoDao.listarTodos().stream().map(PermissaoDto::new).collect(Collectors.toList());
	}
	
	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Pesquisar funcionario");
			token.gerarToken();
			listaFuncionarioDto = funcionarioDao.buscarUltimosCadastrados().stream().map(FuncionarioDto::new).collect(Collectors.toList());
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
			}else {
				funcionarioDao.editar(f);
			}
			uteis.adicionarMensagemSucessoRegistro();
			atualizarPesquisa();
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
		}else if (listaFuncionario.size() > 1) {
			resultado = false;
		}else {
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
		mensagem = listaFuncionarioDto.isEmpty() ? "Não há funcionarios cadastrados." : "Ultimos funcionarios cadastrados.";
	}
	
	public void atualizarPesquisa() {
		try {
			if ("Código".equals(opcaoBuscaSelecionada)) {
				listaFuncionarioDto.clear();
				Funcionario f = funcionarioDao.getById(Integer.valueOf(argumentoBusca));
				if (f != null) {
					listaFuncionarioDto.add(new FuncionarioDto(f));
				}
			}else if ("Nome".equals(opcaoBuscaSelecionada)) {
				listaFuncionarioDto = funcionarioDao.buscarSimilaridade("nome", argumentoBusca).stream().map(FuncionarioDto::new).collect(Collectors.toList());
			}else {
				listaFuncionarioDto = funcionarioDao.buscarSimilaridade("cidade", argumentoBusca).stream().map(FuncionarioDto::new).collect(Collectors.toList());
			}
			atualizarMensagem();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de busca é invalido!");
		}
	}

	
}
