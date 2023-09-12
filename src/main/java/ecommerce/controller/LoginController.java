package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ecommerce.beans.Funcionario;
import ecommerce.beans.Permissao;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.FuncionarioDto;
import ecommerce.dto.PermissaoDto;
import ecommerce.uteis.PermissaoExeption;
import ecommerce.uteis.Uteis;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@SessionScoped
@Transactional
public class LoginController implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private PermissaoDao permissaoDao;

	@Inject
	private FuncionarioDao funcionarioDao;

	@Inject
	private Uteis uteis;
	
	@Inject
	private EstoqueTransienteDao estDao;

	private FuncionarioDto funcionarioDto;
	private String login;
	private String senha;
	private List<PermissaoDto> listaPermissaoExistente = new ArrayList<>();

	@PostConstruct
	public void carregarPermissoes() {
		listaPermissaoExistente = permissaoDao.listarTodos().stream().map(PermissaoDto::new).collect(Collectors.toList());
	}

	public String realizarLogin() {
		Optional<Funcionario> funcionario = funcionarioDao.realizarlogin(login, senha);
		login = null;
		senha = null;
		if (funcionario.isPresent()) {
			funcionarioDto = new FuncionarioDto(funcionario.get());
			return uteis.getCaminhoInicial();
		} else {
			uteis.adicionarMensagemAdvertencia("Usuário ou senha incorretos. Verifique as credenciais e tente novamente!");
			return null;
		}
	}

	public String realizarLogout() {
		funcionarioDto.getListaPermissoes().clear();
		funcionarioDto = null;
		return uteis.getCaminhoLogin();
	}

	public void possuiPermissao(String arg) throws PermissaoExeption {
		boolean e = funcionarioDto.getListaPermissoes().stream().anyMatch(es -> arg.equals(es.getNomePermissao()));
		if (!e) {
			verificarExistenciaPermissao(arg);
			throw new PermissaoExeption(arg);
		}
	}

	public void verificarExistenciaPermissao(String arg) {
		boolean possui = listaPermissaoExistente.stream().noneMatch(e -> e.getNomePermissao().equals(arg));
		if (possui) {
			Permissao p = new Permissao();
			p.setDescricao(arg);
			listaPermissaoExistente.add(new PermissaoDto(p));
			permissaoDao.cadastrar(p);
		}
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public FuncionarioDto getFuncionarioDto() {
		return funcionarioDto;
	}

	public void setFuncionarioDto(FuncionarioDto funcionarioDto) {
		this.funcionarioDto = funcionarioDto;
	}

	public PermissaoDao getPermissaoDao() {
		return permissaoDao;
	}

	public void setPermissaoDao(PermissaoDao permissaoDao) {
		this.permissaoDao = permissaoDao;
	}

	public List<PermissaoDto> getListaPermissaoExistente() {
		return listaPermissaoExistente;
	}

	public void setListaPermissaoExistente(List<PermissaoDto> listaPermissaoExistente) {
		this.listaPermissaoExistente = listaPermissaoExistente;
	}

}
