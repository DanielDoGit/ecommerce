package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ecommerce.beans.Parcela;
import ecommerce.beans.Recebimento;

public class ParcelaDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;

	private String numeroParcela;

	private LocalDate dataEmissao;
	
	private LocalDate dataPagamento;

	private BigDecimal valorParcela;

	private List<CaixaDto> listacaixa = new ArrayList<>();
	
	public Parcela toParcela(Recebimento recebimento) {
		Parcela p = new Parcela();
		p.setCodigo(codigo);
		p.setNumeroParcela(numeroParcela);
		p.setDataEmissao(dataEmissao);
		p.setDataPagamento(dataPagamento);
		p.setValorParcela(valorParcela);
		p.setRecebimento(recebimento);
		for (CaixaDto caixaDto : listacaixa) {
			p.getListacaixa().add(caixaDto.toCaixa(p));
		}
		return p;
	}
	
	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(String numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDate dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorRecebimento) {
		this.valorParcela = valorRecebimento;
	}

	public List<CaixaDto> getListacaixa() {
		return listacaixa;
	}

	public void setListacaixa(List<CaixaDto> listacaixa) {
		this.listacaixa = listacaixa;
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
		ParcelaDto other = (ParcelaDto) obj;
		return Objects.equals(codigo, other.codigo);
	}
	
	

}
