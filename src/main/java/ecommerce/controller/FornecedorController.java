package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cidade;
import ecommerce.beans.Fornecedor;
import ecommerce.beans.TipoPessoa;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.FornecedorDao;
import ecommerce.dto.CidadeDto;
import ecommerce.dto.FornecedorDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.PermissaoExeption;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Transactional
@ConversationScoped
public class FornecedorController implements Serializable {

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
	private List<FornecedorDto> listaFornecedoresCadastrados;
	private List<CidadeDto> listaCidadePesquisada;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarFornecedor.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarFornecedor.xhtml";

	private final List<String> opcoesBusca = Arrays.asList("Nome", "Cidade", "Código");
	private String argumentoBusca;
	private String opcaoBuscaSelecionada;
	private String mensagem;
	private boolean inclusao = false;

	public String prepararConsulta() {
		try {
			conversa.iniciar();
			token.gerarToken();
			loginController.possuiPermissao("Consultar fornecedor");
			listaFornecedoresCadastrados = fornecedorDao.buscarUltimosCadastrados().stream().map(FornecedorDto::new)
					.collect(Collectors.toList());
			atualizarMensagem();
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	private void atualizarMensagem() {
		mensagem = listaFornecedoresCadastrados.isEmpty() ? "Não há fornecedores listados..."
				: "Quantidade fornecedores listados: " + listaFornecedoresCadastrados.size();
	}

	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar fornecedor");
			token.gerarToken();
			fornecedorDto = new FornecedorDto();
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
			loginController.possuiPermissao("Alterar fornecedor");
			token.gerarToken();
			fornecedorDto = new FornecedorDto(fornecedorDao.getById(id));
			inclusao = false;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() {
		try {
			if (opcoesBusca.get(2).equals(opcaoBuscaSelecionada)) {
				listaFornecedoresCadastrados.clear();
				Fornecedor f = fornecedorDao.getById(Integer.valueOf(argumentoBusca));
				if (f != null) {
					listaFornecedoresCadastrados.add(new FornecedorDto(f));
				}
			} else if (opcoesBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaFornecedoresCadastrados = fornecedorDao.buscarSimilaridadeInnerJoin(Cidade.class, "cidade", "nome", argumentoBusca).stream()
						.map(FornecedorDto::new).collect(Collectors.toList());
			} else {
				listaFornecedoresCadastrados = fornecedorDao.buscarSimilaridade("nome", argumentoBusca).stream()
						.map(FornecedorDto::new).collect(Collectors.toList());
			}
			atualizarMensagem();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento digitado é inválido para a pesquisa!");
		}
	}

	public String excluir(Integer id) {
		try {
			token.validarToken();
			loginController.possuiPermissao("Excluir fornecedor");
			fornecedorDao.excluir(id);
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
		listaCidadePesquisada = listaCidade.stream().map(CidadeDto::new).collect(Collectors.toList());
	}

	public void selecionarCidade(CidadeDto t) {
		mostrarCidade(t.toCidade());
	}

	public void mostrarCidade(Cidade c) {
		if (c != null) {
			fornecedorDto.setIdCidade(c.getCodigo().toString());
			fornecedorDto.setNomeCidade(c.getNome());
		} else {
			fornecedorDto.setIdCidade(null);
			fornecedorDto.setNomeCidade(null);
		}
	}
	
	public void carregarCidade(AjaxBehaviorEvent e) {
		try {
			Cidade c = cidadeDao.getById(Integer.valueOf(fornecedorDto.getIdCidade()));
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
			Fornecedor f = fornecedorDto.toFornecedor(cidadeDao);
			if (inclusao) {
				fornecedorDao.cadastrar(f);
			}else {
				fornecedorDao.editar(f);
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
		List<Fornecedor> listaFornecedor = fornecedorDao.buscarSimilaridade("nome", fornecedorDto.getNome());
		if (listaFornecedor.isEmpty()) {
			resultado = true;
		} else if (listaFornecedor.size() > 1) {
			resultado = false;
		} else {
			Fornecedor f = listaFornecedor.get(0);
			if (fornecedorDto.getCodigo().equals(f.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("Fornecedor com nome já cadastrado!");
		}
		return resultado;
	}
	
	public String cancelar() {
		return paginaConsulta;
	}

	public TipoPessoa[] getValuesTipoPessoa() {
		return TipoPessoa.values();
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

	public List<FornecedorDto> getListaFornecedoresCadastrados() {
		return listaFornecedoresCadastrados;
	}

	public void setListaFornecedoresCadastrados(List<FornecedorDto> listaFornecedoresCadastrados) {
		this.listaFornecedoresCadastrados = listaFornecedoresCadastrados;
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
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

	public boolean isInclusao() {
		return inclusao;
	}

	public void setInclusao(boolean inclusao) {
		this.inclusao = inclusao;
	}

	public List<CidadeDto> getListaCidadePesquisada() {
		return listaCidadePesquisada;
	}

	public void setListaCidadePesquisada(List<CidadeDto> listaCidadePesquisada) {
		this.listaCidadePesquisada = listaCidadePesquisada;
	}

}
