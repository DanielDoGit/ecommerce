package ecommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Funcionario;
import ecommerce.beans.FuncionarioPermissao;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.PermissaoDao;

public class FuncionarioDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String nome;

	private String cpf;

	private String bairro;

	private String endereco;
	
	private String idCidade;
	
	private String nomeCidade;

	private String login;

	private String senha;

	private boolean ativo;
	
	private List<PermissaoDto> listaPermissoes = new ArrayList<PermissaoDto>();
	
	public FuncionarioDto() {}
	
	public FuncionarioDto(Funcionario func) {
		this.codigo = func.getCodigo();
		this.nome = func.getNome();
		this.cpf = func.getCpf();
		this.bairro = func.getBairro();
		this.endereco = func.getEndereco();
		if (func.getCidade() != null) {
			this.idCidade = func.getCidade().getCodigo().toString();
			this.nomeCidade = func.getCidade().getNome();
		}
		this.login = func.getLogin();
		this.senha = func.getSenha();
		this.ativo = func.isAtivo();
		listaPermissoes = func.getListaFuncionarioPermissao().stream().map(e -> new PermissaoDto(e.getPermissao())).collect(Collectors.toList());
	}
	
	public Funcionario toFuncionario(PermissaoDao permissaoDao, CidadeDao cidadeDao) {
		Funcionario funcionario = new Funcionario();
		funcionario.setCodigo(codigo);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setBairro(bairro);
		funcionario.setEndereco(endereco);
		funcionario.setAtivo(ativo);
		funcionario.setLogin(login);
		funcionario.setSenha(senha);
		funcionario.setCidade(cidadeDao.getById(Integer.valueOf(idCidade)));
		for (PermissaoDto permissaoDto : listaPermissoes) {
			FuncionarioPermissao funcionarioPermissao = new FuncionarioPermissao();
			funcionarioPermissao.setFuncionario(funcionario);
			funcionarioPermissao.setPermissao(permissaoDao.getById(permissaoDto.getIdPermissao()));
			funcionario.getListaFuncionarioPermissao().add(funcionarioPermissao);
		}
		return funcionario; 
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getIdCidade() {
		return idCidade;
	}

	public void setIdCidade(String idCidade) {
		this.idCidade = idCidade;
	}

	public String getNomeCidade() {
		return nomeCidade;
	}

	public void setNomeCidade(String nomeCidade) {
		this.nomeCidade = nomeCidade;
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

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public List<PermissaoDto> getListaPermissoes() {
		return listaPermissoes;
	}

	public void setListaPermissoes(List<PermissaoDto> listaPermissoes) {
		this.listaPermissoes = listaPermissoes;
	}
}
