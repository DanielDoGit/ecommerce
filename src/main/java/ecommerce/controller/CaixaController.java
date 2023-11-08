package ecommerce.controller;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Caixa;
import ecommerce.beans.Funcionario;
import ecommerce.dao.CaixaDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dto.CaixaDto;
import ecommerce.uteis.jsf.GerenciadorConversa;
import ecommerce.uteis.jsf.GerenciadorToken;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@Named
@ConversationScoped
public class CaixaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private List<CaixaDto> listCaixaDTO = new ArrayList<CaixaDto>();
	
	@Inject
	private CaixaDao caixaDao;
	
	@Inject
	private GerenciadorConversa conversa;
	
	@Inject
	private GerenciadorToken token;
	
	@Inject
	private LoginController loginController;
	
	@Inject
	private FuncionarioDao funcionarioDao;
	
	@PostConstruct
	public void carregarDados() {
		LocalDateTime dataHoraHoje = LocalDateTime.now();
        Funcionario funcionario = funcionarioDao.getById(loginController.getFuncionarioDto().getCodigo());
		List<Caixa> listCaixa = caixaDao.getLancamentos(dataHoraHoje, dataHoraHoje, funcionario);
		listCaixaDTO = listCaixa.stream().map(CaixaDto::new).collect(Collectors.toList());
	}

	public List<CaixaDto> getListCaixaDTO() {
		return listCaixaDTO;
	}

	public void setListCaixaDTO(List<CaixaDto> listCaixaDTO) {
		this.listCaixaDTO = listCaixaDTO;
	}

	public CaixaDao getCaixaDao() {
		return caixaDao;
	}

	public void setCaixaDao(CaixaDao caixaDao) {
		this.caixaDao = caixaDao;
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

	public FuncionarioDao getFuncionarioDao() {
		return funcionarioDao;
	}

	public void setFuncionarioDao(FuncionarioDao funcionarioDao) {
		this.funcionarioDao = funcionarioDao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
