package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.AjusteEstoque;
import ecommerce.beans.Produto;
import ecommerce.beans.TipoMovimentacao;
import ecommerce.dao.AjusteEstoqueDao;
import ecommerce.dao.FornecedorDao;
import ecommerce.dao.GrupoDao;
import ecommerce.dao.ProdutoDao;
import ecommerce.dto.AjusteEstoqueDto;
import ecommerce.dto.CadastroProdutoDto;
import ecommerce.uteis.jsf.AppException;
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
@Transactional
@ConversationScoped
public class AjusteEstoqueController implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private AjusteEstoqueDao ajusteEstoqueDao;
	
	@Inject
	private LoginController loginController;
	
	@Inject
	private GerenciadorToken token;
	
	@Inject
	private Uteis uteis;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private ProdutoDao produtoDao;
	
	private AjusteEstoqueDto ajusteEstoqueDto;
	private List<CadastroProdutoDto> listaProdutoDto;
	
	private String codigoAjuste;
	private String argumentoBusca;
	
	public String prepararAjusteEstoque() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Cadastrar ajuste estoque");
			token.gerarToken();
			ajusteEstoqueDto = new AjusteEstoqueDto();
			ajusteEstoqueDto.setDataAjuste(LocalDate.now());
			argumentoBusca = "";
			return "/ecommerce/paginas/processos/ajusteEstoque.xhtml";
		} catch (Exception e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	public void pesquisarProduto() {
		List<Produto> listaProduto = produtoDao.buscarSimilaridade("descricao", argumentoBusca);
		listaProdutoDto = listaProduto.stream().map(CadastroProdutoDto::new).collect(Collectors.toList());
	}
	
	public void selecionarProduto(CadastroProdutoDto p) throws Exception {
		try {
			FornecedorDao forDao = InjectBean.newInstanceCDI(FornecedorDao.class);
			GrupoDao grupoDao = InjectBean.newInstanceCDI(GrupoDao.class);
			mostrarProduto(p.toProduto(forDao, grupoDao));
		} catch (Exception e) {
			throw e;
		}
	}
	
	public void carregarProduto(AjaxBehaviorEvent e) {
		try {
			Produto p = produtoDao.getById(Integer.valueOf(ajusteEstoqueDto.getIdProDuto()));
			mostrarProduto(p);
		} catch (NumberFormatException e2) {
			uteis.adicionarMensagemAdvertencia("O argumento de pesquisa é inválido!");
			mostrarProduto(null);
		}
	}
	
	public void mostrarProduto(Produto p) {
		if (p != null) {
			ajusteEstoqueDto.setIdProDuto(p.getCodigo().toString());
			ajusteEstoqueDto.setNomeProduto(p.getDescricao());
		}else {
			ajusteEstoqueDto.setIdProDuto(null);
			ajusteEstoqueDto.setNomeProduto(null);
		}
	}
	
	public String confirmar() {
		try {
			token.validarToken();
			if(!validarDados()) {
				return null;
			}
			AjusteEstoque a = ajusteEstoqueDto.toEstoque(produtoDao);
			ajusteEstoqueDao.cadastrar(a);
			uteis.adicionarMensagemSucessoRegistro();
			return uteis.getCaminhoInicial();
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}

	}
	
	
	public String prepararExclusao() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Excluir ajuste estoque");
			token.gerarToken();
			return "/ecommerce/paginas/processos/excluirAjusteEstoque.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	
	public String excluir() {
		try {
			token.validarToken();
			AjusteEstoque e = ajusteEstoqueDao.getById(Integer.valueOf(codigoAjuste));
			if (e == null) {
				throw new AppException("Não há nenhum registro com esse código!");
			}
			ajusteEstoqueDao.excluir(e);
			uteis.adicionarMensagemSucessoExclusao();
			return uteis.getCaminhoInicial();
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}catch (NumberFormatException e) {
			uteis.adicionarMensagemAdvertencia("O argumento de consulta é invalido!");
			return null;
		}catch (AppException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
		
	}
	
	public boolean validarDados() {
		boolean a = ajusteEstoqueDto.getQuantidade().compareTo(BigDecimal.ZERO) == 1;
		if (!a) {
			uteis.adicionarMensagemAdvertencia("A quantidade deve ser maior do que zero!");
		}
		return a;
	}
	
	public String cancelar() {
		return "/ecommerce/paginas/uteis/inicial.xhtml";
	}
	
	
	public TipoMovimentacao[] getTipos() {
		TipoMovimentacao[] tipos = {TipoMovimentacao.ENTRADA, TipoMovimentacao.SAIDA};
		return tipos;
	}

	public AjusteEstoqueDao getAjusteEstoqueDao() {
		return ajusteEstoqueDao;
	}

	public void setAjusteEstoqueDao(AjusteEstoqueDao ajusteEstoqueDao) {
		this.ajusteEstoqueDao = ajusteEstoqueDao;
	}

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
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

	public GerenciadorConversa getConversa() {
		return conversa;
	}

	public void setConversa(GerenciadorConversa conversa) {
		this.conversa = conversa;
	}

	public ProdutoDao getProdutoDao() {
		return produtoDao;
	}

	public void setProdutoDao(ProdutoDao produtoDao) {
		this.produtoDao = produtoDao;
	}

	public AjusteEstoqueDto getAjusteEstoqueDto() {
		return ajusteEstoqueDto;
	}

	public void setAjusteEstoqueDto(AjusteEstoqueDto ajusteEstoqueDto) {
		this.ajusteEstoqueDto = ajusteEstoqueDto;
	}

	public List<CadastroProdutoDto> getListaProdutoDto() {
		return listaProdutoDto;
	}

	public void setListaProdutoDto(List<CadastroProdutoDto> listaProdutoDto) {
		this.listaProdutoDto = listaProdutoDto;
	}

	public String getCodigoAjuste() {
		return codigoAjuste;
	}

	public void setCodigoAjuste(String codigoAjuste) {
		this.codigoAjuste = codigoAjuste;
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
	
	

}
