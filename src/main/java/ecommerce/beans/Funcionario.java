package ecommerce.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Funcionario implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	private String nome;
	
	private String cpf;
	
	private String bairro;
	
	private String endereco;
	
	@ManyToOne
	@JoinColumn(name = "cidade")
	private Cidade cidade;
	
	private String login;
	
	private String senha;
	
	private boolean ativo;
	
	@OneToMany(mappedBy = "funcionario", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<FuncionarioPermissao> listaFuncionarioPermissao = new ArrayList<>();

	public Funcionario(Integer codigo, String nome, String cpf, String bairro, String endereco, Cidade cidade,
			String login, String senha, boolean ativo, List<FuncionarioPermissao> listaFuncionarioPermissao) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cpf = cpf;
		this.bairro = bairro;
		this.endereco = endereco;
		this.cidade = cidade;
		this.login = login;
		this.senha = senha;
		this.ativo = ativo;
		this.listaFuncionarioPermissao = listaFuncionarioPermissao;
	}

	public Funcionario() {
		super();
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

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
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


	public List<FuncionarioPermissao> getListaFuncionarioPermissao() {
		return listaFuncionarioPermissao;
	}

	public void setListaFuncionarioPermissao(List<FuncionarioPermissao> listaFuncionarioPermissao) {
		this.listaFuncionarioPermissao = listaFuncionarioPermissao;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		return Objects.equals(codigo, other.codigo);
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
	
}
