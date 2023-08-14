package ecommerce.dto;

import java.io.Serializable;

import ecommerce.beans.Grupo;

public class GrupoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String nome;
	
	public GrupoDto(Grupo grupo) {
		this.id = grupo.getCodigo();
		this.nome = grupo.getDescricao();
	}
	
	public GrupoDto() {}
	
	public Grupo toGrupo() {
		return new Grupo(id, nome);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
