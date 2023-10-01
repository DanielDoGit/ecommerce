package ecommerce.controller;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Fornecedor;
import ecommerce.beans.Grupo;
import ecommerce.beans.Produto;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.FornecedorDao;
import ecommerce.dao.GrupoDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dto.CadastroProdutoDto;
import ecommerce.dto.FornecedorDto;
import ecommerce.dto.GrupoDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import ecommerce.uteis.jsf.InjectBean;
import ecommerce.uteis.jsf.PermissaoExeption;
import ecommerce.uteis.jsf.TokenException;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class ProdutoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ProdutoDao produtoDao;
	
	@Inject
	private GrupoDao grupoDao;

	@Inject
	private FornecedorDao fornecedorDao;

	@Inject
	private Uteis uteis;

	@Inject
	private LoginController loginController;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private EstoqueTransienteDao estoqueDao;

	private CadastroProdutoDto produtoDto;
	private List<CadastroProdutoDto> listaProdutosPesquisados;
	private List<FornecedorDto> listaFornecedoresPesquisados;
	private List<GrupoDto> listaGruposPesquisados;
	private String mensagem;
	private boolean inclusao;
	private String argumentoBusca;
	private final List<String> opcoesBusca = Arrays.asList("Nome", "Código", "Fornecedor");
	private String opcaoBuscaSelecionada;

	private final String paginaConsulta = "/ecommerce/paginas/cadastros/consultarProduto.xhtml";
	private final String paginaCadastro = "/ecommerce/paginas/cadastros/cadastrarProduto.xhtml";

	public String prepararConsulta() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Consultar produto");
			listaProdutosPesquisados = produtoDao.buscarUltimosCadastrados().stream().map(CadastroProdutoDto::new)
					.collect(Collectors.toList());
			mensagem = listaProdutosPesquisados.isEmpty() ? "Não há produtos cadastrados no sitema."
					: "Ultimos produtos cadastrados...";
			return paginaConsulta;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String prepararCadastro() {
		try {
			loginController.possuiPermissao("Cadastrar produto");
			token.gerarToken();
			produtoDto = new CadastroProdutoDto();
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
			loginController.possuiPermissao("Alterar produto");
			token.gerarToken();
			produtoDto = new CadastroProdutoDto(produtoDao.getById(id), estoqueDao);
			inclusao = false;
			return paginaCadastro;
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String excluir(Integer id) {
		try {
			loginController.possuiPermissao("Excluir produto");
			token.validarToken();
			produtoDao.excluir(id);
			uteis.adicionarMensagemSucessoExclusao();
			atualizarPesquisa();
			return paginaCadastro;
		} catch (PermissaoExeption | TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void atualizarPesquisa() {
		try {
			if (opcoesBusca.get(1).equals(opcaoBuscaSelecionada)) {
				listaProdutosPesquisados.clear();
				Produto c = produtoDao.getById(Integer.valueOf(argumentoBusca));
				if (c != null) {
					listaProdutosPesquisados.add(new CadastroProdutoDto(c));
				}
			} else if (opcoesBusca.get(0).equals(opcaoBuscaSelecionada)) {
				listaProdutosPesquisados = produtoDao.buscarSimilaridade("descricao", argumentoBusca).stream()
						.map(CadastroProdutoDto::new).collect(Collectors.toList());
			} else {
				listaProdutosPesquisados = produtoDao.buscarSimilaridadeInnerJoin(Fornecedor.class, "cidade","nome", argumentoBusca)
						.stream().map(CadastroProdutoDto::new).collect(Collectors.toList());
			}
			mensagem = listaProdutosPesquisados.isEmpty() ? "Não há produtos cadastrados no sitema com esse argumento."
					: "Quantidade de produtos cadastrados: " + listaProdutosPesquisados.size();
		} catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de busca é invalido!");
		}
	}

	public void pesquisarFornecedor() {
		List<Fornecedor> fornecedores = fornecedorDao.buscarSimilaridade("nome", argumentoBusca);
		listaFornecedoresPesquisados = fornecedores.stream().map(FornecedorDto::new).collect(Collectors.toList());
	}

	public void selecionarFornecedor(FornecedorDto t) throws Exception {
		CidadeDao c = InjectBean.newInstanceCDI(CidadeDao.class);
		mostrarFornecedor(t.toFornecedor(c));
	}

	public void mostrarFornecedor(Fornecedor c) {
		if (c != null) {
			produtoDto.setIdFornecedor(c.getCodigo().toString());
			produtoDto.setNomeFornecedor(c.getNome());
		} else {
			produtoDto.setIdFornecedor(null);
			produtoDto.setNomeFornecedor(null);
		}
	}

	public void carregarFornecedor(AjaxBehaviorEvent e) {
		try {
			Fornecedor c = fornecedorDao.getById(Integer.valueOf(produtoDto.getIdFornecedor()));
			mostrarFornecedor(c);
		} catch (NumberFormatException e1) {
			uteis.adicionarMensagemAdvertencia("Argumento de pesquisa inválido!");
			mostrarFornecedor(null);
		}
	}
	
	
	public void pesquisarGrupo() {
		List<Grupo> grupos = grupoDao.buscarSimilaridade("descricao", argumentoBusca);
		listaGruposPesquisados = grupos.stream().map(GrupoDto::new).collect(Collectors.toList());
	}

	public void selecionarGrupo(GrupoDto t) throws Exception {
		mostrarGrupo(t.toGrupo());
	}

	public void mostrarGrupo(Grupo c) {
		if (c != null) {
			produtoDto.setIdGrupo(c.getCodigo().toString());
			produtoDto.setNomeGrupo(c.getDescricao());
		} else {
			produtoDto.setIdGrupo(null);
			produtoDto.setNomeGrupo(null);
		}
	}

	public void carregarGrupo(AjaxBehaviorEvent e) {
		try {
			Grupo c = grupoDao.getById(Integer.valueOf(produtoDto.getIdGrupo()));
			mostrarGrupo(c);
		} catch (NumberFormatException e1) {
			uteis.adicionarMensagemAdvertencia("Argumento de pesquisa inválido!");
			mostrarGrupo(null);
		}
	}
	
	public String confirmar() {
		try {
			token.validarToken();
			if (!validarDados()) {
				return null;
			}
			Produto cli = produtoDto.toProduto(fornecedorDao, grupoDao);
			if (inclusao) {
				produtoDao.cadastrar(cli);
			}else {
				produtoDao.editar(cli);
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
		List<Produto> produto = produtoDao.buscarSimilaridade("descricao", produtoDto.getDescricao());
		if (produto.isEmpty()) {
			resultado = true;
		} else if (produto.size() > 1) {
			resultado = false;
		} else {
			Produto f = produto.get(0);
			if (produtoDto.getCodigo().equals(f.getCodigo())) {
				resultado = true;
			}
		}
		if (resultado == false) {
			uteis.adicionarMensagemAdvertencia("Produto com descricão já cadastrado!");
		}
		return resultado;
	}
	
	public String cancelar() {
		return paginaConsulta;
	}


	public ProdutoDao getProdutoDao() {
		return produtoDao;
	}

	public void setProdutoDao(ProdutoDao produtoDao) {
		this.produtoDao = produtoDao;
	}

	public FornecedorDao getFornecedorDao() {
		return fornecedorDao;
	}

	public void setFornecedorDao(FornecedorDao fornecedorDao) {
		this.fornecedorDao = fornecedorDao;
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

	public EstoqueTransienteDao getEstoqueDao() {
		return estoqueDao;
	}

	public void setEstoqueDao(EstoqueTransienteDao estoqueDao) {
		this.estoqueDao = estoqueDao;
	}

	public CadastroProdutoDto getProdutoDto() {
		return produtoDto;
	}

	public void setProdutoDto(CadastroProdutoDto produtoDto) {
		this.produtoDto = produtoDto;
	}

	public List<CadastroProdutoDto> getListaProdutosPesquisados() {
		return listaProdutosPesquisados;
	}

	public void setListaProdutosPesquisados(List<CadastroProdutoDto> listaProdutosPesquisados) {
		this.listaProdutosPesquisados = listaProdutosPesquisados;
	}

	public List<FornecedorDto> getListaFornecedoresPesquisados() {
		return listaFornecedoresPesquisados;
	}

	public void setListaFornecedoresPesquisados(List<FornecedorDto> listaFornecedoresPesquisados) {
		this.listaFornecedoresPesquisados = listaFornecedoresPesquisados;
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

	public String getPaginaConsulta() {
		return paginaConsulta;
	}

	public String getPaginaCadastro() {
		return paginaCadastro;
	}

	public GrupoDao getGrupoDao() {
		return grupoDao;
	}

	public void setGrupoDao(GrupoDao grupoDao) {
		this.grupoDao = grupoDao;
	}

	public List<GrupoDto> getListaGruposPesquisados() {
		return listaGruposPesquisados;
	}

	public void setListaGruposPesquisados(List<GrupoDto> listaGruposPesquisados) {
		this.listaGruposPesquisados = listaGruposPesquisados;
	}

}
