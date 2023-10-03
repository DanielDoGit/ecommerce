package ecommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import ecommerce.beans.FormaPagamento;
import ecommerce.uteis.jsf.Formatadores;

public class FormaPagamentoDto implements Serializable, Comparable<FormaPagamentoDto> {

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private String descricao;
	
	private boolean aparecerCaixa;
	
	private boolean compensado;
	
	public FormaPagamentoDto(FormaPagamento fo) {
		this.codigo = fo.getCodigo();
		this.descricao = fo.getDescricao();
		this.aparecerCaixa = fo.isAparecerCaixa();
		this.compensado = fo.isCompensado();
	}
	
	public FormaPagamentoDto() {}
	
	public FormaPagamento toFormaPagamento() {
		String f = new Formatadores().removerEspacoDuplicado(descricao);
		return new FormaPagamento(codigo, f, aparecerCaixa, compensado);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public boolean isAparecerCaixa() {
		return aparecerCaixa;
	}

	public void setAparecerCaixa(boolean aparecerCaixa) {
		this.aparecerCaixa = aparecerCaixa;
	}

	public boolean isCompensado() {
		return compensado;
	}

	public void setCompensado(boolean compensado) {
		this.compensado = compensado;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormaPagamentoDto other = (FormaPagamentoDto) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao);
	}

	@Override
	public int compareTo(FormaPagamentoDto o) {
		return descricao.compareTo(o.getDescricao());
	}

	
	
	
}
