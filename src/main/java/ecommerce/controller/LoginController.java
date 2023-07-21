package ecommerce.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import ecommerce.beans.Funcionario;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.PermissaoDao;
import ecommerce.dto.FuncionarioDto;
import ecommerce.dto.PermissaoDto;
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
    	funcionario.i;
    	
    	return null;
    }
    
    public String realizarLogout() {
    	return null;
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
