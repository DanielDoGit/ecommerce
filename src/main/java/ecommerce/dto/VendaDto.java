package ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import ecommerce.beans.Cliente;
import ecommerce.beans.Funcionario;
import ecommerce.beans.ItemVenda;
import ecommerce.beans.Recebimento;
import ecommerce.beans.Venda;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.FuncionarioDao;

public class VendaDto {

	private Integer codigo;

	private Integer idCliente;

	private String nomeCliente;

	private Integer idFuncionario;

	private String nomeFuncionario;

	private LocalDate dataVenda;

	private BigDecimal totalVenda;
	
	public VendaDto() {
		this.dataVenda = LocalDate.now();
	}

	public Venda toVenda(ClienteDao cliDao, FuncionarioDao funcDao, List<ItemVenda> itensVenda,
			List<Recebimento> listaRecebimentos) {
		Cliente c = cliDao.getById(idCliente);
		Funcionario f = funcDao.getById(idFuncionario);
		Venda venda = new Venda();
		venda.setCodigo(codigo);
		venda.setCliente(c);
		venda.setDataVenda(dataVenda);
		venda.setFuncionario(f);
		venda.setTotalVenda(totalVenda);
		venda.setItensVenda(itensVenda);
		venda.setRecebimentos(listaRecebimentos);
		return venda;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}

	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}

	public Integer getIdFuncionario() {
		return idFuncionario;
	}

	public void setIdFuncionario(Integer idFuncionario) {
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
