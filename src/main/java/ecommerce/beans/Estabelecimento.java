package ecommerce.beans;

import java.io.Serializable;

import java.util.Objects;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Estabelecimento implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	private String nome;
	
	private String contato;
	
	private String endereco;
	
	private String bairro;
	
	@CNPJ
	private String cnpj;
	
	@ManyToOne
	@JoinColumn(name = "cidade")
	private Cidade cidade;
	
	public Estabelecimento(Integer codigo, String nome, String contato, String endereco, String bairro, String cnpj,
			Cidade cidade) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.contato = contato;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cnpj = cnpj;
		this.cidade = cidade;
	}

	public Estabelecimento() {
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, nome);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Estabelecimento other = (Estabelecimento) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(nome, other.nome);
	}
	
	

}
