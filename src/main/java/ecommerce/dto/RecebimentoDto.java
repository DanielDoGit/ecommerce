package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ecommerce.beans.CondicaoPagamento;
import ecommerce.beans.FormaPagamento;
import ecommerce.beans.Recebimento;
import ecommerce.beans.Venda;

public class RecebimentoDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	
	private BigDecimal valor;
	
	private LocalDate dataEmissao;
	
	private LocalDate dataVencimento;
	
	private boolean quitado;
	
	private FormaPagamentoDto formaPagamentoDto;
	
	private CondicaoPagamentoDto condicaopagamento;
	
	private List<ParcelaDto> listaParcelaDto = new ArrayList<ParcelaDto>();
	
	public RecebimentoDto() {
	}
		
	public Recebimento toRecebimento(Venda venda) {
		FormaPagamento fp = formaPagamentoDto.toFormaPagamento();
		CondicaoPagamento cp = condicaopagamento.toCondicaoPagamento();
		Recebimento r = new Recebimento();
		r.setCodigo(codigo);
		r.setValor(valor);
		r.setDataEmissao(dataEmissao);
		r.setDataVencimento(dataVencimento);
		r.setQuitado(quitado);
		r.setVenda(venda);
		r.setCondicaopagamento(cp);
		r.setFormaPagamento(fp);
		r.setCliente(venda.getCliente());
		for (ParcelaDto parcelaDto : listaParcelaDto) {
			r.getListaParcelas().add(parcelaDto.toParcela(r));
		}
		return r;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public LocalDate getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(LocalDate dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public LocalDate getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDate dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public boolean isQuitado() {
		return quitado;
	}

	public void setQuitado(boolean quitado) {
		this.quitado = quitado;
	}

	public FormaPagamentoDto getFormaPagamentoDto() {
		return formaPagamentoDto;
	}

	public void setFormaPagamentoDto(FormaPagamentoDto formaPagamentoDto) {
		this.formaPagamentoDto = formaPagamentoDto;
	}

	public CondicaoPagamentoDto getCondicaopagamento() {
		return condicaopagamento;
	}

	public void setCondicaopagamento(CondicaoPagamentoDto condicaopagamento) {
		this.condicaopagamento = condicaopagamento;
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
		RecebimentoDto other = (RecebimentoDto) obj;
		return Objects.equals(codigo, other.codigo);
	}

	public List<ParcelaDto> getListaParcelaDto() {
		return listaParcelaDto;
	}

	public void setListaParcelaDto(List<ParcelaDto> listaParcelaDto) {
		this.listaParcelaDto = listaParcelaDto;
	}
}
