package ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import ecommerce.beans.Cliente;
import ecommerce.beans.Funcionario;
import ecommerce.beans.ItemVenda;
import ecommerce.beans.Venda;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.FuncionarioDao;
import ecommerce.dao.ProdutoDao;

public class VendaDto {

	private Integer codigo;

	private String idCliente;

	private String nomeCliente;

	private BigDecimal limiteCredito;

	private String idFuncionario;

	private String nomeFuncionario;

	private LocalDate dataVenda;

	private BigDecimal totalVenda;

	private BigDecimal desconto;

	private BigDecimal acrescimo;

	private transient BigDecimal recebido;

	private transient BigDecimal troco;

	public VendaDto() {
		this.dataVenda = LocalDate.now();
		this.totalVenda = BigDecimal.ZERO;
		this.limiteCredito = BigDecimal.ZERO;
		this.desconto = BigDecimal.ZERO;
		this.acrescimo = BigDecimal.ZERO;
		this.recebido = BigDecimal.ZERO;
		this.troco = BigDecimal.ZERO;
	}

	public Venda toVenda(ClienteDao cliDao, FuncionarioDao funcDao, ProdutoDao produtoDao, List<ItemVendaDto> listaItensVendaDto, List<RecebimentoDto> listaRecebimentoDto) {
		Cliente c = cliDao.getById(Integer.valueOf(idCliente));
		Funcionario f = funcDao.getById(Integer.valueOf(idFuncionario));
		Venda venda = new Venda();
		venda.setCodigo(codigo);
		venda.setCliente(c);
		venda.setDataVenda(dataVenda);
		venda.setFuncionario(f);
		venda.setTotalVenda(totalVenda);
		venda.setAcrescimo(acrescimo);
		venda.setDesconto(desconto);
		for (ItemVendaDto itemVendaDto : listaItensVendaDto) {
			ItemVenda iv = itemVendaDto.toItemVenda(produtoDao, venda);
			venda.getItensVenda().add(iv);
		}
		for (RecebimentoDto recebimentoDto : listaRecebimentoDto) {
			venda.getRecebimentos().add(recebimentoDto.toRecebimento(venda));
		}
		return venda;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(String idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public String getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(String idFuncionario) {
		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario() {
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario) {
		this.nomeFuncionario = nomeFuncionario;
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

	public BigDecimal getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(BigDecimal limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getAcrescimo() {
		return acrescimo;
	}

	public void setAcrescimo(BigDecimal acrescimo) {
		this.acrescimo = acrescimo;
	}

	public BigDecimal getRecebido() {
		return recebido;
	}

	public void setRecebido(BigDecimal recebido) {
		this.recebido = recebido;
	}

	public BigDecimal getTroco() {
		return troco;
	}

	public void setTroco(BigDecimal troco) {
		this.troco = troco;
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
		VendaDto other = (VendaDto) obj;
		return Objects.equals(codigo, other.codigo);
	}

}
