package ecommerce.dao;

import java.math.BigDecimal;
import java.time.LocalDate;

import ecommerce.beans.EstoqueTransiente;
import ecommerce.beans.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.inject.Named;
import jakarta.persistence.Query;

@Named
@RequestScoped
public class EstoqueTransienteDao extends Dao<EstoqueTransiente> {

	private static final long serialVersionUID = 1L;
	
	public EstoqueTransienteDao() {
		super(EstoqueTransiente.class);
	}
	
	public void criarEstoqueTransiente(Produto produto, BigDecimal quantidadeDisponivel) {
		EstoqueTransiente e = new EstoqueTransiente();
		e.setProduto(produto);
		e.setQuantidadeAcesso(1);
		e.setQuantidadeDisponivel(quantidadeDisponivel);
		this.cadastrar(e);
	}
	
	public void atualizarEstoqueTransiente(Produto produto, BigDecimal quantidadeDisponivel) {
		EstoqueTransiente e = this.buscarExatidaoInnerJoin(Produto.class, "codigo", produto.getCodigo().toString());
		if (e == null) {
			throw new FacesException("Não é possível atualizar um produto nulo!");
		}
		e.setProduto(produto);
		e.setQuantidadeAcesso(e.getQuantidadeAcesso()+1);
		e.setQuantidadeDisponivel(quantidadeDisponivel);
		this.editar(e);
	}
	
	public BigDecimal getQuantidadeVenda(LocalDate dataApuracao, Produto produto) {
		String sql = "select sum(iv.quantidade) from itemvenda as iv, venda as v where v.datavenda <= :dataBase and iv.produto = :produto";
		Query q = em.createNativeQuery(sql);
		q.setParameter("dataBase", dataApuracao);
		q.setParameter("produto", produto.getCodigo());
		BigDecimal b = (BigDecimal) q.getSingleResult();
		if (b == null) {
			b = BigDecimal.ZERO;
		}
		return b;
	}
	
	public BigDecimal getQuantidadeAjusteEstoqueSaida(LocalDate dataApuracao, Produto produto) {
		String sql = "select sum(aj.quantidade) from ajusteestoque as aj where aj.dataajuste <= :dataBase and aj.produto = :produto and tipo = 3";
		Query q = em.createNativeQuery(sql);
		q.setParameter("dataBase", dataApuracao);
		q.setParameter("produto", produto.getCodigo());
		BigDecimal b = (BigDecimal) q.getSingleResult();
		if (b == null) {
			b = BigDecimal.ZERO;
		}
		return b;
	}
	
	public BigDecimal getQuantidadeAjusteEstoqueEntrada(LocalDate dataApuracao, Produto produto) {
		String sql = "select sum(aj.quantidade) from ajusteestoque as aj where aj.dataajuste <= :dataBase and aj.produto = :produto and tipo = 2";
		Query q = em.createNativeQuery(sql);
		q.setParameter("dataBase", dataApuracao);
		q.setParameter("produto", produto.getCodigo());
		BigDecimal b = (BigDecimal) q.getSingleResult();
		if (b == null) {
			b = BigDecimal.ZERO;
		}
		return b;
	}
	
	public BigDecimal getEstoqueDisponivel(LocalDate dataApuracao,Produto produto) {
		validarDados(dataApuracao, produto);
		BigDecimal qtdVenda = getQuantidadeVenda(dataApuracao,produto);
		BigDecimal qtdEstoqueEntrada = getQuantidadeAjusteEstoqueEntrada(dataApuracao,produto);
		BigDecimal qtdEstoqueSaida = getQuantidadeAjusteEstoqueSaida(dataApuracao,produto);
		return qtdEstoqueEntrada.subtract(qtdEstoqueSaida).subtract(qtdVenda);
	}
	
	private void validarDados(LocalDate dataApuracao, Produto produto) {
		if (dataApuracao == null || produto == null) {
			throw new FacesException("Não é possível realizar a consulta sem parâmetros");
		}
	}
	
}
