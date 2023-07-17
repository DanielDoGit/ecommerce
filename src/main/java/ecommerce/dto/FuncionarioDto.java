package ecommerce.dto;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

import ecommerce.beans.Funcionario;

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
	
	private List<PermissaoDto> listaPermissoes;
	
	public FuncionarioDto(Funcionario func) {
		this.codigo = func.getCodigo();
		this.nome = func.getNome();
		this.cpf = func.getCpf();
		this.bairro = func.getBairro();
		this.endereco = func.getEndereco();
		this.idCidade = func.getCidade().getCodigo().toString();
		this.nomeCidade = func.getCidade().getNome();
		this.login = func.getLogin();
		this.senha = func.getSenha();
		this.ativo = func.isAtivo();
		listaPermissoes = func.getListaFuncionarioPermissao().stream().map(e -> new PermissaoDto(e.getPermissao())).collect(Collectors.toList());
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
