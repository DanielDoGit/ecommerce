package ecommerce.controller;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Cliente;
import ecommerce.beans.EstoqueTransiente;
import ecommerce.beans.Funcionario;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.ClienteDto;
import ecommerce.dto.FuncionarioDto;
import ecommerce.dto.VendaDto;
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
public class VendaController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteDao clienteDao;

	@Inject
	private FuncionarioDao funcionarioDao;

	@Inject
	private GerenciadorConversa conversa;

	@Inject
	private GerenciadorToken token;

	@Inject
	private Uteis uteis;

	@Inject
	private LoginController loginController;

	private VendaDto vendaDto;

	private String argumentoBusca;

	private List<ClienteDto> listaClienteDto = new ArrayList<ClienteDto>();

	private List<FuncionarioDto> listaFuncionarioDto = new ArrayList<FuncionarioDto>();
	
	public String abrirVenda() {
		try {
			conversa.iniciar();
			loginController.possuiPermissao("Iniciar venda");
			token.gerarToken();
			argumentoBusca = "";
			vendaDto = new VendaDto();
			limparRegistrosAntigos();
			return "/ecommerce/paginas/processos/aberturaVenda.xhtml";
		} catch (PermissaoExeption e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}
	
	private void limparRegistrosAntigos() {
		EstoqueTransienteDao estDao = InjectBean.newInstanceCDI(EstoqueTransienteDao.class);
		List<EstoqueTransiente> lista =	estDao.buscarRegistrosAcimaDoPrazo();
		lista.forEach( e -> estDao.excluir(e));
	}

	public void carregarCliente(AjaxBehaviorEvent s) {
		try {
			Cliente c = clienteDao.getById(Integer.valueOf(vendaDto.getIdCliente()));
			mostrarCliente(c);
		} catch (NumberFormatException e) {
			mostrarCliente(null);
		}
	}

	private void mostrarCliente(Cliente c) {
		if (c != null) {
			vendaDto.setIdCliente(String.valueOf(c.getCodigo()));
			vendaDto.setNomeCliente(c.getNome());
			vendaDto.setLimiteCredito(c.getCredito());
		} else {
			vendaDto.setIdCliente(null);
			vendaDto.setNomeCliente(null);
			vendaDto.setLimiteCredito(BigDecimal.ZERO);
		}
	}

	public void carregarFuncionario(AjaxBehaviorEvent s) {
		try {
			Funcionario c = funcionarioDao.getById(Integer.valueOf(vendaDto.getIdFuncionario()));
			mostrarFuncionario(c);
		} catch (NumberFormatException e) {
			mostrarFuncionario(null);
		}
	}

	private void mostrarFuncionario(Funcionario c) {
		if (c != null) {
			vendaDto.setIdFuncionario(String.valueOf(c.getCodigo()));
			vendaDto.setNomeFuncionario(c.getNome());
		} else {
			vendaDto.setIdFuncionario(null);
			vendaDto.setNomeFuncionario(null);
		}
	}

	public String chamarItensVenda() {
		try {
			token.validarToken();
			return "/ecommerce/paginas/processos/itensVenda.xhtml";
		} catch (TokenException e) {
			uteis.adicionarMensagemErro(e);
			return null;
		}
	}

	public String cancelarOperacao() {
		conversa.finalizar();
		return uteis.getCaminhoInicial();
	}

	public void pesquisarCliente() {
		List<Cliente> listaCliente = clienteDao.buscarClienteAtivo(argumentoBusca);
		listaClienteDto = listaCliente.stream().map(ClienteDto::new).collect(Collectors.toList());
	}

	public void pesquisarFuncionario() {
		List<Funcionario> listaFuncionario = funcionarioDao.buscarFuncionarioAtivo(argumentoBusca);
		listaFuncionarioDto = listaFuncionario.stream().map(FuncionarioDto::new).collect(Collectors.toList());
	}

	public void selecionarCliente(ClienteDto cliDto) {
		CidadeDao cid = InjectBean.newInstanceCDI(CidadeDao.class);
		mostrarCliente(cliDto.toCliente(cid));
	}

	public void selecionarFuncionario(FuncionarioDto funcDto) {
		CidadeDao cid = InjectBean.newInstanceCDI(CidadeDao.class);
		PermissaoDao pDao = InjectBean.newInstanceCDI(PermissaoDao.class);
		mostrarFuncionario(funcDto.toFuncionario(pDao, cid));
	}

	public ClienteDao getClienteDao() {
		return clienteDao;
	}

	public void setClienteDao(ClienteDao clienteDao) {
		this.clienteDao = clienteDao;
	}

	public FuncionarioDao getFuncionarioDao() {
		return funcionarioDao;
	}

	public void setFuncionarioDao(FuncionarioDao funcionarioDao) {
		this.funcionarioDao = funcionarioDao;
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

	public LoginController getLoginController() {
		return loginController;
	}

	public void setLoginController(LoginController loginController) {
		this.loginController = loginController;
	}

	public VendaDto getVendaDto() {
		return vendaDto;
	}

	public void setVendaDto(VendaDto vendaDto) {
		this.vendaDto = vendaDto;
	}

	public String getArgumentoBusca() {
		return argumentoBusca;
	}

	public void setArgumentoBusca(String argumentoBusca) {
		this.argumentoBusca = argumentoBusca;
	}

	public List<ClienteDto> getListaClienteDto() {
		return listaClienteDto;
	}

	public void setListaClienteDto(List<ClienteDto> listaClienteDto) {
		this.listaClienteDto = listaClienteDto;
	}

	public List<FuncionarioDto> getListaFuncionarioDto() {
		return listaFuncionarioDto;
	}

	public void setListaFuncionarioDto(List<FuncionarioDto> listaFuncionarioDto) {
		this.listaFuncionarioDto = listaFuncionarioDto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
