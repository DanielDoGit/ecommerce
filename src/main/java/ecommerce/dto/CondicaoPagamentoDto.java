package ecommerce.dto;

import java.io.Serializable;
import java.util.Objects;

import ecommerce.beans.CondicaoPagamento;
import jakarta.validation.constraints.NotBlank;

public class CondicaoPagamentoDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	@NotBlank(message = "O numero de parcelas está vazio!")
	private String descricao;
	
	private Integer numeroParcelas;
	
	public CondicaoPagamentoDto(CondicaoPagamento co) {
		super();
		this.codigo = co.getCodigo();
		this.descricao = co.getDescricao();
		this.numeroParcelas = co.getNumeroParcelas();
	}

	public CondicaoPagamentoDto() {
		super();
	}
	
	public CondicaoPagamento toCondicaoPagamento() {
		return new CondicaoPagamento(codigo, descricao, numeroParcelas);
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

	public Integer getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(Integer numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
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
		CondicaoPagamentoDto other = (CondicaoPagamentoDto) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao);
	}

	
	
	
}
