package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Grupo;
import ecommerce.dao.GrupoDao;
import ecommerce.dto.GrupoDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.TokenException;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@ConversationScoped
@Named
@Transactional
public class GrupoController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private GrupoDao grupoDao;
	
	@Inject
	private LoginController loginController;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private GerenciadorToken token;
	
	@Inject
	private Uteis uteis;
	
	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarGrupo.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarGrupo.xhtml";
	private final List<String> opcoesBusca = Arrays.asList("Descricao", "Código");
	private String opcaoBuscaSelecionada;
	
	private GrupoDto grupoDto;
	private boolean inclusao;
	private List<GrupoDto> listaGrupoDto;
	private String mensagem;
	private String argumentoBusca;
	
	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Consultar grupo");
			token.gerarToken();
			listaGrupoDto = grupoDao.buscarUltimosCadastrados().stream().map(GrupoDto::new).collect(Collectors.toList());
			mensagem = listaGrupoDto.isEmpty() ? "Não há grupos cadastrados...":"Ultimos grupos cadastrados";
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	public void atualizarPesquisa() {
		try {
			if (opcoesBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaGrupoDto.clear();
				Grupo g = grupoDao.getById(Integer.valueOf(argumentoBusca));
				if (g != null) {
					listaGrupoDto.add(new GrupoDto(g));
				}
			}else {
				listaGrupoDto = grupoDao.buscarSimilaridade("descricao", argumentoBusca).stream().map(GrupoDto::new).collect(Collectors.toList());
			}
			mensagem = listaGrupoDto.isEmpty() ? "Não há grupos cadastrados com esse argumento":"Grupos cadastrados: "+listaGrupoDto.size();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("Argumento de busca inválido !");
		}
	}
	
	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar grupo");
			token.gerarToken();
			grupoDto = new GrupoDto();
			inclusao = true;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	public String prepararAlteracao(Integer id) {
		try {
			loginController.possuiPermissao("Alterar grupo");
			token.gerarToken();
			grupoDto = new GrupoDto(grupoDao.getById(id));
			inclusao = false;
			return paginaCadastro;
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
			Grupo g = grupoDto.toGrupo();
			if (inclusao) {
				grupoDao.cadastrar(g);
			}else {
				grupoDao.editar(g);
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
		List<Grupo> listaGrupo = grupoDao.buscarSimilaridade("descricao", grupoDto.getNome());
		if (listaGrupo.isEmpty()) {
			resultado = true;
		} else if (listaGrupo.size() > 1) {
			resultado = false;
		} else {
			Grupo f = listaGrupo.get(0);
			if (grupoDto.getId().equals(f.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("Grupo com nome já cadastrado!");
		}
		return resultado;
	}
	
	public String excluir(Integer id) {
		try {
			token.validarToken();
			loginController.possuiPermissao("Excluir grupo");
			grupoDao.excluir(id);
			atualizarPesquisa();
			uteis.adicionarMensagemSucessoExclusao();
			return paginaConsulta;
		} catch (PermissaoExeption | TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
		
	}

	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
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

	public Uteis getUteis() {
		return uteis;
	}

	public void setUteis(Uteis uteis) {
		this.uteis = uteis;
	}

	public String getOpcaoBuscaSelecionada() {
		return opcaoBuscaSelecionada;
	}

	public void setOpcaoBuscaSelecionada(String opcaoBuscaSelecionada) {
		this.opcaoBuscaSelecionada = opcaoBuscaSelecionada;
	}

	public GrupoDto getGrupoDto() {
		return grupoDto;
	}

	public void setGrupoDto(GrupoDto grupoDto) {
		this.grupoDto = grupoDto;
	}

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
	}

	public List<GrupoDto> getListaGrupoDto() {
		return listaGrupoDto;
	}

	public void setListaGrupoDto(List<GrupoDto> listaGrupoDto) {
		this.listaGrupoDto = listaGrupoDto;
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
