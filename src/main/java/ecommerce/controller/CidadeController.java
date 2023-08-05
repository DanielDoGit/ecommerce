package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cidade;
import ecommerce.dao.CidadeDao;
import ecommerce.dto.CidadeDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.TokenException;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Transactional
@ConversationScoped
public class CidadeController implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String consultarCidade = "/ecommerce/paginas/cadastros/consultarCidade.xhtml";
	private final String cadastrarCidade = "/ecommerce/paginas/cadastros/cadastrarCidade.xhtml";

	private boolean inclusao = false;

	private String mensagem;

	private String argumentoBusca;

	private String opcaoBuscaSelecionada;

	private final List<String> opcaoBusca = Arrays.asList("Código", "Nome", "UF");

	@Inject
	private CidadeDao cidadeDao;

	@Inject
	private Uteis uteis;

	@Inject
	private LoginController loginController;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	private CidadeDto cidadeDto;

	private List<CidadeDto> listaCidadeDto = new ArrayList<CidadeDto>();;

	public String prepararConsulta() {
		try {
			loginController.possuiPermissao("Pesquisar cidade");
			conversa.iniciar();
			token.gerarToken();
			listaCidadeDto = cidadeDao.buscarUltimosCadastrados().stream().map(CidadeDto::new).collect(Collectors.toList());
			atualizarMensagemRodape();
			argumentoBusca = "";
			return consultarCidade;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() {
		try {
			if (opcaoBusca.get(0).equals(opcaoBuscaSelecionada)) {
				listaCidadeDto.clear();
				Cidade c = cidadeDao.getById(Integer.valueOf(argumentoBusca));
				if (c != null) {
					listaCidadeDto.add(new CidadeDto(c));
				}
			} else if (opcaoBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaCidadeDto = cidadeDao.buscarSimilaridade("nome", argumentoBusca).stream().map(CidadeDto::new)
						.collect(Collectors.toList());
			} else {
				listaCidadeDto = cidadeDao.buscarSimilaridade("uf", argumentoBusca).stream().map(CidadeDto::new)
						.collect(Collectors.toList());
			}
			atualizarMensagemRodape();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("Verifique o argumento digitado e tente novamente.");
		}
	}

	private void atualizarMensagemRodape() {
		mensagem = listaCidadeDto.isEmpty() ? "Não há registros cadastrados no software"
				: "Ultimos registros cadastrados";
	}

	public String excluir(Integer id) {
		try {
			token.validarToken();
			loginController.possuiPermissao("Excluir cidade");
			cidadeDao.excluir(id);
			atualizarPesquisa();
			uteis.adicionarMensagemSucessoExclusao();
			return consultarCidade;
		} catch (PermissaoExeption | TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararAlteracao(Integer id) {

		try {
			loginController.possuiPermissao("Alterar cidade");
			inclusao = false;
			cidadeDto = new CidadeDto(cidadeDao.getById(id));
			token.gerarToken();
			return cadastrarCidade;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar cidade");
			inclusao = true;
			cidadeDto = new CidadeDto();
			token.gerarToken();
			return cadastrarCidade;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String confirmar() {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			Cidade c = cidadeDto.toCidade();
			if (inclusao) {
				cidadeDao.cadastrar(c);
			} else {
				cidadeDao.editar(c);
			}
			uteis.adicionarMensagemSucessoRegistro();
			return consultarCidade;
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}

	}

	public String cancelar() {
		cidadeDto = null;
		return consultarCidade;
	}

	private boolean validarDados() {
		boolean resultado = false;
		List<Cidade> listaCidade = cidadeDao.consultarCidadeNome(cidadeDto.getNome());
		if (listaCidade.isEmpty()) {
			resultado = true;
		} else if (listaCidade.size() > 1) {
			resultado = false;
		} else {
			Cidade c = listaCidade.get(0);
			if (c.getCodigo().equals(cidadeDto.getCodigo())) {
				resultado = true;
			} else {
				uteis.adicionarMensagemAdvertencia("O registro alterado já existe!");
			}
		}
		return resultado;
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

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
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

	public CidadeDto getCidadeDto() {
		return cidadeDto;
	}

	public void setCidadeDto(CidadeDto cidadeDto) {
		this.cidadeDto = cidadeDto;
	}

	public List<CidadeDto> getListaCidadeDto() {
		return listaCidadeDto;
	}

	public void setListaCidadeDto(List<CidadeDto> listaCidadeDto) {
		this.listaCidadeDto = listaCidadeDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getOpcaoBusca() {
		return opcaoBusca;
	}

}
