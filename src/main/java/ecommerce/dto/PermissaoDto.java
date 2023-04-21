package ecommerce.dto;

import ecommerce.beans.Permissao;

public class PermissaoDto {
	
	private Integer idPermissao;
	private String nomePermissao;
	
	public Permissao toPermissao() {
		return new Permissao(idPermissao, nomePermissao);
	}
	
	public PermissaoDto() {
	}
	
	public PermissaoDto(Permissao permissao) {
		this.idPermissao = permissao.getId();
		this.nomePermissao = permissao.getNome();
	}

	public Integer getIdPermissao() {
		return idPermissao;
	}

	public void setIdPermissao(Integer idPermissao) {
		this.idPermissao = idPermissao;
	}

	public String getNomePermissao() {
		return nomePermissao;
	}

	public void setNomePermissao(String nomePermissao) {
		this.nomePermissao = nomePermissao;
	}

}
