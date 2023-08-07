package ecommerce.controller;

import java.io.Serializable;

import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cidade;
import ecommerce.beans.Estabelecimento;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.EstabelecimentoDao;
import ecommerce.dto.CidadeDto;
import ecommerce.dto.EstabelecimentoDto;
import ecommerce.uteis.Formatadores;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.TokenException;
import ecommerce.uteis.Uteis;
import ecommerce.uteis.ValidadorBean;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.faces.event.AjaxBehaviorEvent;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@Transactional
@ConversationScoped
public class EstabelecimentoController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstabelecimentoDao dao;

	@Inject
	private CidadeDao cidadeDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private LoginController controller;
	
	@Inject
	private ValidadorBean<Estabelecimento> validador;

	@Inject
	private GerenciadorToken token;

	@Inject
	private Uteis uteis;

	private EstabelecimentoDto dto;

	private String argumentoBusca = "";

	private List<CidadeDto> listaCidadePesquisada;
	
	private Formatadores formatadores = new Formatadores();

	public String alterarEstabelecimento() {
		try {
			conversa.iniciar();
			controller.possuiPermissao("Alterar Estabelecimento");
			token.gerarToken();
			Estabelecimento e = dao.getById(1);
			dto = new EstabelecimentoDto(e);
			dto.setCnpj(formatadores.formatarCnpj(dto.getCnpj()));
			return "/ecommerce/paginas/cadastros/estabelecimento.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String cancelar() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}

	public String confirmar() {
		try {
			token.validarToken();
			Estabelecimento e = dto.toEstabelecimento(cidadeDao);
			dao.editar(e);
			uteis.adicionarMensagemInformativa("Estabelecimento alterado com sucesso !");
			return uteis.getCaminhoInicial();
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public void carregarCidade(AjaxBehaviorEvent e) {
		try {
			String id = dto.getIdCidade();
			Cidade c = cidadeDao.getById(Integer.valueOf(id));
			mostrarCidade(c);
		} catch (NumberFormatException e1) {
			uteis.adicionarMensagemAdvertencia("Argumento de pesquisa inválido!");
			mostrarCidade(null);
		}
	}

	private void mostrarCidade(Cidade c) {
		if (c != null) {
			dto.setIdCidade(Integer.toString(c.getCodigo()));
			dto.setNomeCidade(c.getNome());
		} else {
			dto.setIdCidade(null);
			dto.setNomeCidade(null);
		}
	}

	public void pesquisarCidade() {
		List<Cidade> listaCidade = cidadeDao.consultarCidadeNome(argumentoBusca);
		listaCidadePesquisada = listaCidade.stream().map(CidadeDto::new).collect(Collectors.toList());
	}

	public void selecionarCidade(CidadeDto cidadeDto) {
		mostrarCidade(cidadeDto.toCidade());
	}

	public EstabelecimentoDao getDao() {
		return dao;
	}

	public void setDao(EstabelecimentoDao dao) {
		this.dao = dao;
	}

	public CidadeDao getCidadeDao() {
		return cidadeDao;
	}

	public void setCidadeDao(CidadeDao cidadeDao) {
		this.cidadeDao = cidadeDao;
	}

	public EstabelecimentoDto getDto() {
		return dto;
	}

	public void setDto(EstabelecimentoDto dto) {
		this.dto = dto;
	}

	public String getArgumentoBusca() {
		return argumentoBusca;
	}

	public void setArgumentoBusca(String argumentoBusca) {
		this.argumentoBusca = argumentoBusca;
	}

	public List<CidadeDto> getListaCidadePesquisada() {
		return listaCidadePesquisada;
	}

	public void setListaCidadePesquisada(List<CidadeDto> listaCidadePesquisada) {
		this.listaCidadePesquisada = listaCidadePesquisada;
	}

}
