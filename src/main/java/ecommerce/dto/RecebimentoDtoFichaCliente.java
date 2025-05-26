package ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ecommerce.beans.CondicaoPagamento;
import ecommerce.beans.FormaPagamento;
import ecommerce.beans.Parcela;
import ecommerce.beans.Recebimento;
import ecommerce.beans.Venda;
import ecommerce.dao.VendaDao;

public class RecebimentoDtoFichaCliente {
	
private Integer codigo;
	
	private BigDecimal valor;
	
	private LocalDate dataEmissao;
	
	private LocalDate dataVencimento;
	
	private boolean quitado;
	
	private FormaPagamentoDto formaPagamentoDto;
	
	private CondicaoPagamentoDto condicaopagamento;
	
	private List<ParcelaDtoFichaCliente> listaParcelaDto = new ArrayList<ParcelaDtoFichaCliente>();

	private Integer codigoVenda;
	
	public RecebimentoDtoFichaCliente(Recebimento recebimento) {
		this.codigo = recebimento.getCodigo();
		this.codigoVenda = recebimento.getVenda().getCodigo();
		this.condicaopagamento = new CondicaoPagamentoDto(recebimento.getCondicaopagamento());
		this.dataEmissao = recebimento.getDataEmissao();
		this.dataVencimento = recebimento.getDataVencimento();
		this.formaPagamentoDto = new FormaPagamentoDto(recebimento.getFormaPagamento());
		this.listaParcelaDto = new ArrayList<ParcelaDtoFichaCliente>();
		for (Parcela parcela : recebimento.getListaParcelas()) {
			listaParcelaDto.add(new ParcelaDtoFichaCliente(parcela));
		}
		this.quitado = recebimento.isQuitado();
		this.valor = recebimento.getValor();
	}
	
	public Recebimento toRecebimento(VendaDao vendaDao) {
		FormaPagamento fp = formaPagamentoDto.toFormaPagamento();
		CondicaoPagamento cp = condicaopagamento.toCondicaoPagamento();
		Recebimento r = new Recebimento();
		r.setCodigo(codigo);
		r.setValor(valor);
		r.setDataEmissao(dataEmissao);
		r.setDataVencimento(dataVencimento);
		r.setQuitado(quitado);
		Venda venda = vendaDao.getById(codigoVenda);
		r.setVenda(venda);
		r.setCondicaopagamento(cp);
		r.setFormaPagamento(fp);
		r.setCliente(venda.getCliente());
		for (ParcelaDtoFichaCliente parcelaDto : listaParcelaDto) {
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

	public List<ParcelaDtoFichaCliente> getListaParcelaDto() {
		return listaParcelaDto;
	}

	public void setListaParcelaDto(List<ParcelaDtoFichaCliente> listaParcelaDto) {
		this.listaParcelaDto = listaParcelaDto;
	}

	public Integer getCodigoVenda() {
		return codigoVenda;
	}

	public void setCodigoVenda(Integer codigoVenda) {
		this.codigoVenda = codigoVenda;
	}
	
	
}
