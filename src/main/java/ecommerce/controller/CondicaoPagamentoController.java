package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

import ecommerce.beans.CondicaoPagamento;
import ecommerce.dao.CondicaoPagamentoDao;
import ecommerce.dto.CondicaoPagamentoDto;
import ecommerce.uteis.AppException;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.TokenException;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Transactional
@ConversationScoped
@Named
public class CondicaoPagamentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private CondicaoPagamentoDao condicaoPagamentoDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private Uteis uteis;

	@Inject
	private LoginController login;

	private List<CondicaoPagamentoDto> listaCondicaoPagamentoPesquisado;
	private CondicaoPagamentoDto condicaoPagamentoDto;
	private String quantidadeDias;

	private String mensagem;
	private String argumentoBusca;
	private final List<String> opcaoBusca = Arrays.asList("Descrição", "Código");
	private String opcaoBuscaSelecionada;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarCondicaoPagamento.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarCondicaoPagamento.xhtml";

	private boolean inclusao;

	public String prepararConsulta() throws Exception {
		try {
			conversa.iniciar();
			token.gerarToken();
			login.possuiPermissao("Consultar condicao de pagamento");
			List<CondicaoPagamento> lista = condicaoPagamentoDao.buscarUltimosCadastrados();
			listaCondicaoPagamentoPesquisado = uteis.transformListToDto(lista, CondicaoPagamentoDto.class);
			mensagem = listaCondicaoPagamentoPesquisado.isEmpty()
					? "Não há condições de pagamento cadastradas no sistema..."
					: "Ultimas condições de pagamento registradas...";
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() throws Exception {
		try {
			if (opcaoBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaCondicaoPagamentoPesquisado.clear();
				CondicaoPagamento f = condicaoPagamentoDao.getById(Integer.valueOf(argumentoBusca));
				if (f != null) {
					listaCondicaoPagamentoPesquisado.add(new CondicaoPagamentoDto(f));
				}
			} else {
				List<CondicaoPagamento> lista = condicaoPagamentoDao.buscarSimilaridade("descricao", argumentoBusca);
				listaCondicaoPagamentoPesquisado = uteis.transformListToDto(lista, CondicaoPagamentoDto.class);
			}
			mensagem = listaCondicaoPagamentoPesquisado.isEmpty()
					? "Não há formas de pagamento cadastradas com esse argumento."
					: "Quantidade de formas de pagamento cadastradas: " + listaCondicaoPagamentoPesquisado.size();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de pesquisa é inválido!");
		} catch (Exception e) {
			throw e;
		}
	}

	public String prepararCadastro() {
		try {
			token.gerarToken();
			login.possuiPermissao("Cadastrar condicao de pagamento");
			condicaoPagamentoDto = new CondicaoPagamentoDto();
			condicaoPagamentoDto.setNumeroParcelas(0);
			inclusao = true;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararAlteracao(Integer id) {
		try {
			token.gerarToken();
			login.possuiPermissao("Alterar condicao de pagamento");
			CondicaoPagamento cp = condicaoPagamentoDao.getById(id);
			if (cp.getCodigo() == 1) {
				throw new AppException("Não é possível alterar a condição de pagamento Á vista!");
			}
			condicaoPagamentoDto = new CondicaoPagamentoDto(cp);
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
			login.possuiPermissao("Excluir condicao de pagamento");
			CondicaoPagamento cp = condicaoPagamentoDao.getById(id);
			if (cp.getCodigo() == 1) {
				throw new AppException("Não é possível excluir a condição de pagamento Á vista!");
			}
			listaCondicaoPagamentoPesquisado.remove(new CondicaoPagamentoDto(cp));
			condicaoPagamentoDao.excluir(cp);
			uteis.adicionarMensagemSucessoExclusao();
			return paginaConsulta;
		} catch (PermissaoExeption | TokenException | AppException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String confirmar() throws Exception {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			CondicaoPagamento f = condicaoPagamentoDto.toCondicaoPagamento();
			if (inclusao) {
				condicaoPagamentoDao.cadastrar(f);
			} else {
				condicaoPagamentoDao.editar(f);
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
		String argumento = condicaoPagamentoDto.getDescricao();
		if (argumento == null) {
			uteis.adicionarMensagemAdvertencia("Não é possível salvar uma condição de pagamento sem parcelas");
			return false;
		}
		boolean resultado = false;
		List<CondicaoPagamento> lista = condicaoPagamentoDao.buscarSimilaridade("descricao", argumento);
		if (lista.isEmpty()) {
			resultado = true;
		} else if (lista.size() > 1) {
			resultado = false;
		} else {
			CondicaoPagamento f = lista.get(0);
			if (f.getCodigo().equals(condicaoPagamentoDto.getCodigo())) {
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

	public void validarInputDia() {
		if (quantidadeDias != null && uteis.isPositiveNumber(quantidadeDias)) {
			if (condicaoPagamentoDto.getDescricao() == null) {
				condicaoPagamentoDto.setDescricao(quantidadeDias);
			} else {
				StringBuilder st = new StringBuilder();
				st.append(condicaoPagamentoDto.getDescricao());
				st.append(" - ");
				st.append(quantidadeDias);
				condicaoPagamentoDto.setDescricao(st.toString());
			}
			condicaoPagamentoDto.setNumeroParcelas(condicaoPagamentoDto.getNumeroParcelas() + 1);
		}
		quantidadeDias = "";
	}

	public void limparUltimaParcela() {
		String descricao = condicaoPagamentoDto.getDescricao();
		if (descricao != null) {
			String[] split = descricao.split(" \\- ");
			StringBuilder st = new StringBuilder();
			for (int i = 0; i < split.length-1; i++) {
				st.append(split[i]);
				if (i < split.length-2) {
					st.append(" - ");
				}
			}
			String resultante = st.toString();
			if (!resultante.isBlank()) {
				condicaoPagamentoDto.setDescricao(resultante);
				condicaoPagamentoDto.setNumeroParcelas(condicaoPagamentoDto.getNumeroParcelas() - 1);
			}else {
				condicaoPagamentoDto.setDescricao(null);
				condicaoPagamentoDto.setNumeroParcelas(0);
			}
			
		}
	}

	public CondicaoPagamentoDao getCondicaoPagamentoDao() {
		return condicaoPagamentoDao;
	}

	public void setCondicaoPagamentoDao(CondicaoPagamentoDao condicaoPagamentoDao) {
		this.condicaoPagamentoDao = condicaoPagamentoDao;
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

	public LoginController getLogin() {
		return login;
	}

	public void setLogin(LoginController login) {
		this.login = login;
	}

	public List<CondicaoPagamentoDto> getListaCondicaoPagamentoPesquisado() {
		return listaCondicaoPagamentoPesquisado;
	}

	public void setListaCondicaoPagamentoPesquisado(List<CondicaoPagamentoDto> listaCondicaoPagamentoPesquisado) {
		this.listaCondicaoPagamentoPesquisado = listaCondicaoPagamentoPesquisado;
	}

	public CondicaoPagamentoDto getCondicaoPagamentoDto() {
		return condicaoPagamentoDto;
	}

	public void setCondicaoPagamentoDto(CondicaoPagamentoDto condicaoPagamentoDto) {
		this.condicaoPagamentoDto = condicaoPagamentoDto;
	}

	public String getQuantidadeDias() {
		return quantidadeDias;
	}

	public void setQuantidadeDias(String quantidadeDias) {
		this.quantidadeDias = quantidadeDias;
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

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public List<String> getOpcaoBusca() {
		return opcaoBusca;
	}

	public String getPaginaConsulta() {
		return paginaConsulta;
	}

	public String getPaginaCadastro() {
		return paginaCadastro;
	}

}
