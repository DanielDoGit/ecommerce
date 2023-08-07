package ecommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import ecommerce.beans.Permissao;

public class PermissaoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Integer idPermissao;
	private String nomePermissao;
	
	public Permissao toPermissao() {
		return new Permissao(idPermissao, nomePermissao);
	}
	
	public PermissaoDto() {
	}
	
	public PermissaoDto(Permissao permissao) {
		this.idPermissao = permissao.getCodigo();
		this.nomePermissao = permissao.getDescricao();
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

	@Override
	public int hashCode() {
		return Objects.hash(idPermissao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PermissaoDto other = (PermissaoDto) obj;
		return Objects.equals(idPermissao, other.idPermissao);
	}

}
