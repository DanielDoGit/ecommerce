package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.FormaPagamento;
import ecommerce.dao.FormaPagamentoDao;
import ecommerce.dto.FormaPagamentoDto;
import ecommerce.uteis.jsf.AppException;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.PermissaoExeption;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class FormaPagamentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private FormaPagamentoDao formaPagamentoDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private LoginController loginController;

	@Inject
	private Uteis uteis;

	private List<FormaPagamentoDto> listaFormaDtos;
	private FormaPagamentoDto formaPagamentoDto;

	private final List<String> listaOpcao = Arrays.asList("Nome", "Código");
	private String opcaoBuscaSelecionada;
	private String argumentoBusca;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarFormaPagamento.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarFormaPagamento.xhtml";
	private String mensagem;
	private boolean inclusao;

	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Pesquisar forma de pagamento");
			token.gerarToken();
			listaFormaDtos = formaPagamentoDao.buscarUltimosCadastrados().stream().map(FormaPagamentoDto::new)
					.collect(Collectors.toList());
			mensagem = listaFormaDtos.isEmpty() ? "Não há formas de pagamento cadastradas no software..."
					: "Ultimas formas de pagamento cadastradas...";
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararCadastro() {
		try {
			token.gerarToken();
			loginController.possuiPermissao("Cadastrar forma de pagamento");
			formaPagamentoDto = new FormaPagamentoDto();
			inclusao = true;
			argumentoBusca = "";
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararAlteracao(Integer id) {
		try {
			token.gerarToken();
			loginController.possuiPermissao("Alterar forma de pagamento");
			FormaPagamento f = formaPagamentoDao.getById(id);
			if (f.getCodigo() == 1) {
				throw new AppException("A forma de pagamento Dinheiro não pode ser editada.");
			}
			formaPagamentoDto = new FormaPagamentoDto(f);
			inclusao = false;
			return paginaCadastro;
		} catch (PermissaoExeption | AppException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String excluir(Integer id) {
		try {
			token.validarToken();
			loginController.possuiPermissao("Excluir forma de pagamento");
			FormaPagamento f = formaPagamentoDao.getById(id);
			if (f == null) {
				throw new AppException("O registro informado não existe!");
			}
			if (f.getCodigo().equals(1)) {
				throw new AppException("O registro Dinheiro não pode ser excluido!");
			}
			listaFormaDtos.remove(new FormaPagamentoDto(f));
			formaPagamentoDao.excluir(f);
			uteis.adicionarMensagemSucessoExclusao();
			return paginaConsulta;
		} catch (PermissaoExeption | TokenException | AppException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() {
		try {
			if (listaOpcao.get(1).equals(opcaoBuscaSelecionada)) {
				listaFormaDtos.clear();
				FormaPagamento f = formaPagamentoDao.getById(Integer.valueOf(argumentoBusca));
				if (f != null) {
					listaFormaDtos.add(new FormaPagamentoDto(f));
				}
			} else {
				listaFormaDtos = formaPagamentoDao.buscarSimilaridade("descricao", argumentoBusca).stream()
						.map(FormaPagamentoDto::new).collect(Collectors.toList());
			}
			mensagem = listaFormaDtos.isEmpty() ? "Não há formas de pagamento cadastradas com esse argumento." : "Quantidade de formas de pagamento cadastradas: "+listaFormaDtos.size();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de pesquisa é inválido!");
		}
	}
	
	public String confirmar() {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			FormaPagamento f = formaPagamentoDto.toFormaPagamento();
			if (inclusao) {
				formaPagamentoDao.cadastrar(f);
			}else {
				formaPagamentoDao.editar(f);
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
		List<FormaPagamento> lista = formaPagamentoDao.buscarSimilaridade("descricao", formaPagamentoDto.getDescricao());
		if (lista.isEmpty()) {
			resultado = true;
		}else if (lista.size() > 1) {
			resultado = false;
		}else {
			FormaPagamento f = lista.get(0);
			if (f.getCodigo().equals(formaPagamentoDto.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("O registro que você está cadastrando ou alterando já existe!");
		}
		return resultado;
	}
	
	public String cancelar() {
		return paginaConsulta;
	}

	public FormaPagamentoDao getFormaPagamentoDao() {
		return formaPagamentoDao;
	}

	public void setFormaPagamentoDao(FormaPagamentoDao formaPagamentoDao) {
		this.formaPagamentoDao = formaPagamentoDao;
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

	public List<FormaPagamentoDto> getListaFormaDtos() {
		return listaFormaDtos;
	}

	public void setListaFormaDtos(List<FormaPagamentoDto> listaFormaDtos) {
		this.listaFormaDtos = listaFormaDtos;
	}

	public FormaPagamentoDto getFormaPagamentoDto() {
		return formaPagamentoDto;
	}

	public void setFormaPagamentoDto(FormaPagamentoDto formaPagamentoDto) {
		this.formaPagamentoDto = formaPagamentoDto;
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

	public List<String> getListaOpcao() {
		return listaOpcao;
	}

	public String getPaginaConsulta() {
		return paginaConsulta;
	}

	public String getPaginaCadastro() {
		return paginaCadastro;
	}

}
