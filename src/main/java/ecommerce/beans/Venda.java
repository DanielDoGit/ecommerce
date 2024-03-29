package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Venda implements Serializable {

	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;
	
	@ManyToOne
	@JoinColumn(name = "cliente")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name = "funcionario")
	private Funcionario funcionario;
	
	private LocalDate dataVenda;
	
	private BigDecimal totalVenda;
	
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL ,fetch = FetchType.EAGER, orphanRemoval = true)
	private List<ItemVenda> itensVenda = new ArrayList<>();
	
	@OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Recebimento> recebimentos = new ArrayList<>();
	
	private BigDecimal desconto = BigDecimal.ZERO;
	
	private BigDecimal acrescimo = BigDecimal.ZERO;
	
	
	public Venda(Integer codigo, Cliente cliente, Funcionario funcionario, LocalDate dataVenda, BigDecimal totalVenda,
			List<ItemVenda> itensVenda, List<Recebimento> recebimentos, BigDecimal desconto, BigDecimal acrescimo) {
		super();
		this.codigo = codigo;
		this.cliente = cliente;
		this.funcionario = funcionario;
		this.dataVenda = dataVenda;
		this.totalVenda = totalVenda;
		this.itensVenda = itensVenda;
		this.recebimentos = recebimentos;
		this.desconto = desconto;
		this.acrescimo = acrescimo;
	}
	
	public Venda() {
		super();
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
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

	public List<ItemVenda> getItensVenda() {
		return itensVenda;
	}

	public void setItensVenda(List<ItemVenda> itensVenda) {
		this.itensVenda = itensVenda;
	}

	public List<Recebimento> getRecebimentos() {
		return recebimentos;
	}

	public void setRecebimentos(List<Recebimento> recebimentos) {
		this.recebimentos = recebimentos;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, dataVenda);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Venda other = (Venda) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(dataVenda, other.dataVenda);
	}
	
}
