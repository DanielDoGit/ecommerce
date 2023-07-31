package ecommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import ecommerce.beans.Cidade;

public class CidadeDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private String nome;
	
	private String uf;
	
	public CidadeDto() {
		super();
	}

	public CidadeDto(Cidade c) {
		super();
		this.codigo = c.getCodigo();
		this.nome = c.getNome();
		this.uf = c.getUf();
	}
	
	public Cidade toCidade() {
		return new Cidade(codigo, nome, uf);
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

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
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
		CidadeDto other = (CidadeDto) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(nome, other.nome);
	}

}
