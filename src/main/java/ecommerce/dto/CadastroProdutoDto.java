package ecommerce.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

import ecommerce.beans.Produto;
import ecommerce.dao.EstoqueTransienteDao;
import ecommerce.dao.FornecedorDao;
import ecommerce.dao.GrupoDao;
import ecommerce.uteis.Formatadores;

public class CadastroProdutoDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer codigo;

	private String descricao;

	private BigDecimal margem;

	private BigDecimal precoCusto;

	private BigDecimal precoVenda;

	private boolean ativo;

	private String idFornecedor;

	private String nomeFornecedor;

	private String idGrupo;

	private String nomeGrupo;

	private BigDecimal qtdEstoqueDisponivel;

	public CadastroProdutoDto(Produto produto, EstoqueTransienteDao estoqueDao) {
		this.setCodigo(produto.getCodigo());
		this.setDescricao(produto.getDescricao());
		this.setMargem(produto.getMargem());
		this.setPrecoCusto(produto.getPrecoCusto());
		this.setPrecoVenda(produto.getPrecoVenda());
		this.setAtivo(produto.isAtivo());
		this.setIdFornecedor(produto.getFornecedor().getCodigo().toString());
		this.setNomeFornecedor(produto.getFornecedor().getNome());
		this.setQtdEstoqueDisponivel(estoqueDao.getEstoqueDisponivel(LocalDate.now(), produto));
		this.setIdGrupo(produto.getGrupo().getCodigo().toString());
		this.setNomeGrupo(produto.getGrupo().getDescricao());
	}

	public CadastroProdutoDto(Produto produto) {
		this.setCodigo(produto.getCodigo());
		this.setDescricao(produto.getDescricao());
		this.setMargem(produto.getMargem());
		this.setPrecoCusto(produto.getPrecoCusto());
		this.setPrecoVenda(produto.getPrecoVenda());
		this.setAtivo(produto.isAtivo());
		this.setIdFornecedor(produto.getFornecedor().getCodigo().toString());
		this.setNomeFornecedor(produto.getFornecedor().getNome());
		this.setQtdEstoqueDisponivel(BigDecimal.ZERO);
		this.setIdGrupo(produto.getGrupo().getCodigo().toString());
		this.setNomeGrupo(produto.getGrupo().getDescricao());
	}

	public CadastroProdutoDto() {
		this.setQtdEstoqueDisponivel(BigDecimal.ZERO);
	}

	public Produto toProduto(FornecedorDao fornecedorDao, GrupoDao grupoDao) {
		String f = new Formatadores().removerEspacoDuplicado(descricao);
		return new Produto(codigo, f, margem, precoCusto, precoVenda, ativo,
				fornecedorDao.getById(Integer.valueOf(idFornecedor)), grupoDao.getById(Integer.valueOf(idGrupo)));
	}

	@Override
	public int hashCode() {
		return Objects.hash(codigo, descricao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CadastroProdutoDto other = (CadastroProdutoDto) obj;
		return Objects.equals(codigo, other.codigo) && Objects.equals(descricao, other.descricao);
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

	public BigDecimal getMargem() {
		return margem;
	}

	public void setMargem(BigDecimal margem) {
		this.margem = margem;
	}

	public BigDecimal getPrecoCusto() {
		return precoCusto;
	}

	public void setPrecoCusto(BigDecimal precoCusto) {
		this.precoCusto = precoCusto;
	}

	public BigDecimal getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(BigDecimal precoVenda) {
		this.precoVenda = precoVenda;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(String idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public BigDecimal getQtdEstoqueDisponivel() {
		return qtdEstoqueDisponivel;
	}

	public void setQtdEstoqueDisponivel(BigDecimal qtdEstoqueDisponivel) {
		this.qtdEstoqueDisponivel = qtdEstoqueDisponivel;
	}

	public String getIdGrupo() {
		return idGrupo;
	}

	public void setIdGrupo(String idGrupo) {
		this.idGrupo = idGrupo;
	}

	public String getNomeGrupo() {
		return nomeGrupo;
	}

	public void setNomeGrupo(String nomeGrupo) {
		this.nomeGrupo = nomeGrupo;
	}

}
