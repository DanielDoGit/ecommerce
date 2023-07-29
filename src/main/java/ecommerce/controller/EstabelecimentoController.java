package ecommerce.controller;

import java.io.Serializable;

import ecommerce.beans.Estabelecimento;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.EstabelecimentoDao;
import ecommerce.dto.EstabelecimentoDto;
import ecommerce.uteis.GerenciadorConversa;
import ecommerce.uteis.GerenciadorToken;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.Uteis;
import jakarta.enterprise.context.ConversationScoped;
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
	private GerenciadorToken token;
	
	@Inject
	private Uteis uteis;
	
	private EstabelecimentoDto dto;
	
	public String alterarEstabelecimento() {
		try {
			conversa.iniciar();
			controller.possuiPermissao("Alterar Estabelecimento");
			token.gerarToken();
			Estabelecimento e = dao.getById(1);
			dto = new EstabelecimentoDto(e);
			return "/ecommerce/paginas/cadastros/estabelecimento.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
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
	
	
	
	
	
	

}
