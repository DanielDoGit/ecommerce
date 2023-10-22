package ecommerce.relatorios;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import ecommerce.controller.ItemVendaController;
import ecommerce.dto.ItemVendaDto;
import ecommerce.dto.VendaDto;

public class ComprovanteVendaDataSource {
	
	private transient String codigoVenda;
	private transient String codigoCliente;
	private transient String nomeCliente;
	private transient LocalDate dataVenda;
	private transient BigDecimal totalVenda;
	private transient BigDecimal descontos;
	private transient BigDecimal acrescimos;
	private transient BigDecimal totalRecebimentos;
	private transient List<ItemVendaDto> listaItensVenda;
	
	public ComprovanteVendaDataSource(ItemVendaController controller) {
		VendaDto vendaDto = controller.getVendaDto();
		this.codigoVenda = vendaDto.getCodigo().toString();
		this.codigoCliente = vendaDto.getIdCliente();
		this.nomeCliente = vendaDto.getNomeCliente();
		this.dataVenda = vendaDto.getDataVenda();
		this.totalVenda = vendaDto.getTotalVenda();
		this.descontos = vendaDto.getDesconto();
		this.acrescimos = vendaDto.getAcrescimo();
		this.totalRecebimentos = controller.getTotalRecebimento();
		this.listaItensVenda = controller.getListItemsVenda();
	}

	public String getCodigoVenda() {
		return codigoVenda;
	}

	public void setCodigoVenda(String codigoVenda) {
		this.codigoVenda = codigoVenda;
	}

	public String getCodigoCliente() {
		return codigoCliente;
	}

	public void setCodigoCliente(String codigoCliente) {
		this.codigoCliente = codigoCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public LocalDate getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(LocalDate dataVenda) {
		this.dataVenda = dataVenda;
	}

	public BigDecimal getTotalVenda() {
		return totalVenda;
	}

	public void setTotalVenda(BigDecimal totalVenda) {
		this.totalVenda = totalVenda;
	}

	public BigDecimal getDescontos() {
		return descontos;
	}

	public void setDescontos(BigDecimal descontos) {
		this.descontos = descontos;
	}

	public BigDecimal getAcrescimos() {
		return acrescimos;
	}

	public void setAcrescimos(BigDecimal acrescimos) {
		this.acrescimos = acrescimos;
	}

	public BigDecimal getTotalRecebimentos() {
		return totalRecebimentos;
	}

	public void setTotalRecebimentos(BigDecimal totalRecebimentos) {
		this.totalRecebimentos = totalRecebimentos;
	}

	public List<ItemVendaDto> getListaItensVenda() {
		return listaItensVenda;
	}

	public void setListaItensVenda(List<ItemVendaDto> listaItensVenda) {
		this.listaItensVenda = listaItensVenda;
	}

}
