package ecommerce.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
public class Parcela implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer codigo;

	private String numeroParcela;

	private LocalDate dataEmissao;
	
	private LocalDate dataPagamento;

	private BigDecimal valorParcela;

	@ManyToOne
	@JoinColumn(name = "recebimento")
	private Recebimento recebimento;
	
	@OneToMany(mappedBy = "parcela", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
	private List<Caixa> listacaixa = new ArrayList<>();
	
	public Parcela(Integer codigo, String numeroParcela, LocalDate dataEmissao, LocalDate dataPagamento,
			BigDecimal valorParcela, Recebimento recebimento, List<Caixa> listacaixa) {
		super();
		this.codigo = codigo;
		this.numeroParcela = numeroParcela;
		this.dataEmissao = dataEmissao;
		this.dataPagamento = dataPagamento;
		this.valorParcela = valorParcela;
		this.recebimento = recebimento;
		this.listacaixa = listacaixa;
	}

	public Parcela() {
		super();
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


	public Recebimento getRecebimento() {
		return recebimento;
	}

	public void setRecebimento(Recebimento recebimento) {
		this.recebimento = recebimento;
	}

	public List<Caixa> getListacaixa() {
		return listacaixa;
	}

	public void setListacaixa(List<Caixa> listacaixa) {
		this.listacaixa = listacaixa;
	}

	public BigDecimal getValorParcela() {
		return valorParcela;
	}

	public void setValorParcela(BigDecimal valorParcela) {
		this.valorParcela = valorParcela;
	}
	
	

}
