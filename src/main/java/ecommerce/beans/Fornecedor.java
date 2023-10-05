package ecommerce.beans;

import java.io.Serializable;

import ecommerce.uteis.jpa.ConverterBoolean;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Fornecedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	private String nome;
	
	private String cpfCnpj;
	
	private String bairro;
	
	private String endereco;
	
	private String contato;
	
	@ManyToOne
	@JoinColumn(name = "cidade")
	private Cidade cidade;

	@Convert(converter = ConverterBoolean.class)
	private boolean ativo;
	
	@Enumerated(value =  EnumType.ORDINAL)
	private TipoPessoa fiscajuridica;

	public Fornecedor(Integer codigo, String nome, String cpfCnpj, String bairro, String endereco, String contato,
			Cidade cidade, boolean ativo, TipoPessoa fiscajuridica) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cpfCnpj = cpfCnpj;
		this.bairro = bairro;
		this.endereco = endereco;
		this.contato = contato;
		this.cidade = cidade;
		this.ativo = ativo;
		this.fiscajuridica = fiscajuridica;
	}

	public Fornecedor() {
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

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getCpfCnpj() {
		return cpfCnpj;
	}

	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	public TipoPessoa getFiscajuridica() {
		return fiscajuridica;
	}

	public void setFiscajuridica(TipoPessoa fiscajuridica) {
		this.fiscajuridica = fiscajuridica;
	}
	
	
}
