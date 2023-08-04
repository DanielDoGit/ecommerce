package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ecommerce.beans.Cidade;
import ecommerce.dao.CidadeDao;
import ecommerce.dto.CidadeDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class CidadeController implements Serializable {

	private static final long serialVersionUID = 1L;

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
			listaCidadeDto = cidadeDao.buscarUltimosCadastrados().stream().map(CidadeDto::new).toList();
			atualizarMensagenRodape();
			argumentoBusca = "";
			return "/ecommerce/paginas/cadastros/consultarCidade.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() {
		try {
			listaCidadeDto.clear();
			if (opcaoBusca.get(0).equals(opcaoBuscaSelecionada)) {
				Cidade c = cidadeDao.getById(Integer.valueOf(argumentoBusca));
				if (c != null) {
					listaCidadeDto.add(new CidadeDto(c));
				}
			} else if (opcaoBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaCidadeDto = cidadeDao.buscarSimilaridade("nome", argumentoBusca).stream().map(CidadeDto::new).toList();
			} else {
				listaCidadeDto = cidadeDao.buscarSimilaridade("uf", argumentoBusca).stream().map(CidadeDto::new).toList();
			}
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("Verifique o argumento digitado e tente novamente.");
		}
		atualizarMensagenRodape();
	}

	private void atualizarMensagenRodape() {
		mensagem = listaCidadeDto.isEmpty() ? "Não há registros cadastrados no software"
				: "Ultimos registros cadastrados";
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

	public CidadeDto getCidadeDto() {
		return cidadeDto;
	}

	public void setCidadeDto(CidadeDto cidadeDto) {
		this.cidadeDto = cidadeDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getOpcaoBusca() {
		return opcaoBusca;
	}

}
