package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import ecommerce.beans.Caixa;
import ecommerce.beans.Funcionario;
import ecommerce.beans.Parcela;

public class CaixaDto implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private String descricao;
	
	private BigDecimal valorRecebimento;
	
	private LocalDateTime dataLancamento;
	
	public Caixa toCaixa(Parcela parcela, Funcionario funcionario) {
		Caixa c = new Caixa();
		c.setCodigo(codigo);
		c.setDataLancamento(dataLancamento);
		c.setDescricao(descricao);
		c.setParcela(parcela);
		c.setValorBaixa(valorRecebimento);
		c.setFuncionario(funcionario);
		return c;
	}
	
	public CaixaDto() {}
	
	public CaixaDto(Caixa c) {
		this.codigo = c.getCodigo();
		this.dataLancamento = c.getDataLancamento();
		this.descricao = c.getDescricao();
		this.valorRecebimento = c.getValorBaixa();
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

	public BigDecimal getValorRecebimento() {
		return valorRecebimento;
	}

	public void setValorRecebimento(BigDecimal valorRecebimento) {
		this.valorRecebimento = valorRecebimento;
	}

	public LocalDateTime getDataLancamento() {
		return dataLancamento;
	}

	public void setDataLancamento(LocalDateTime dataLancamento) {
		this.dataLancamento = dataLancamento;
	}

	
	public static long getSerialversionuid() {
		return serialVersionUID;
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
		CaixaDto other = (CaixaDto) obj;
		return Objects.equals(codigo, other.codigo);
	}	
	
}
