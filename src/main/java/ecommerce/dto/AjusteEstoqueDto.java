package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import ecommerce.beans.AjusteEstoque;
import ecommerce.beans.TipoMovimentacao;
import ecommerce.dao.ProdutoDao;

public class AjusteEstoqueDto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	
	private LocalDate dataAjuste;
	
	private String motivo;
	
	private String idProDuto;
	
	private String nomeProduto;
	
	private BigDecimal quantidade;
	
	private TipoMovimentacao tipoMovimentacao;
	
	public AjusteEstoqueDto(AjusteEstoque ajusteEstoque) {
		this.setCodigo(ajusteEstoque.getCodigo());
		this.setDataAjuste(ajusteEstoque.getDataAjuste());
		this.setMotivo(ajusteEstoque.getMotivo());
		this.setIdProDuto(ajusteEstoque.getProduto().getCodigo().toString());
		this.setNomeProduto(ajusteEstoque.getProduto().getDescricao());
		this.setQuantidade(ajusteEstoque.getQuantidade());
		this.setTipoMovimentacao(ajusteEstoque.getTipo());
	}
	
	public AjusteEstoqueDto() {}
	
	public AjusteEstoque toEstoque(ProdutoDao pDao) {
		return new AjusteEstoque(codigo, dataAjuste, motivo, pDao.getById(Integer.valueOf(getIdProDuto())), quantidade, tipoMovimentacao);
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public LocalDate getDataAjuste() {
		return dataAjuste;
	}

	public void setDataAjuste(LocalDate dataAjuste) {
		this.dataAjuste = dataAjuste;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getIdProDuto() {
		return idProDuto;
	}

	public void setIdProDuto(String idProDuto) {
		this.idProDuto = idProDuto;
	}

	public String getNomeProduto() {
		return nomeProduto;
	}

	public void setNomeProduto(String nomeProduto) {
		this.nomeProduto = nomeProduto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
