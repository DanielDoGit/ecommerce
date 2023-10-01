package ecommerce.dao;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import ecommerce.beans.EstoqueTransiente;
import ecommerce.beans.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.FacesException;
import jakarta.inject.Named;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Named
@RequestScoped
public class EstoqueTransienteDao extends Dao<EstoqueTransiente> {

	private static final long serialVersionUID = 1L;

	public EstoqueTransienteDao() {
		super(EstoqueTransiente.class);
	}
	
	public List<EstoqueTransiente> buscarRegistrosAcimaDoPrazo() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<EstoqueTransiente> cq = cb.createQuery(EstoqueTransiente.class);
		Root<EstoqueTransiente> root =  cq.from(EstoqueTransiente.class);
		Predicate hora = cb.lessThan(root.get("horaIsercao"), LocalDateTime.now().minusHours(1));
		Predicate QtdAcesso = cb.lessThanOrEqualTo(root.get("quantidadeAcesso"), 0);
		cq.select(root).where(cb.and(hora,QtdAcesso));
		return em.createQuery(cq).getResultList();
	}

	private void criarEstoqueTransiente(Produto produto, BigDecimal quantidadeDisponivel) {
		EstoqueTransiente e = new EstoqueTransiente();
		e.setProduto(produto);
		e.setQuantidadeAcesso(1);
		e.setQuantidadeUso(quantidadeDisponivel);
		e.setHoraIsercao(LocalDateTime.now());
		this.cadastrar(e);
	}

	public void processarRemocaoEstoqueTransiente(Produto produto, BigDecimal quantidade) {
		validarDados(produto, quantidade);
		EstoqueTransiente e = this.buscarExatidaoInnerJoin(Produto.class, "produto", "codigo", produto.getCodigo().toString());
		e.setQuantidadeAcesso(e.getQuantidadeAcesso() - 1);
		e.setQuantidadeUso(e.getQuantidadeUso().subtract(quantidade));
		if (e.getQuantidadeAcesso() <= 0 || Duration.between(e.getHoraIsercao(), LocalDateTime.now()).toHours() > 1) {
			super.excluir(e);
		} else {
			this.editar(e);
		}
	}

	public void processarAdicaoEstoqueTransiente(Produto produto, BigDecimal quantidade) {
		validarDados(produto, quantidade);
		EstoqueTransiente e = this.buscarExatidaoInnerJoin(Produto.class, "produto", "codigo", produto.getCodigo().toString());
		if (e == null) {
			criarEstoqueTransiente(produto, quantidade);
		}else {
			if (Duration.between(e.getHoraIsercao(), LocalDateTime.now()).toHours() > 2) {
				super.excluir(e);
			}else {
				e.setQuantidadeAcesso(e.getQuantidadeAcesso() + 1);
				e.setQuantidadeUso(quantidade.add(e.getQuantidadeUso()));
				this.editar(e);
			}
		}
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

	public BigDecimal getEstoqueTransiente(LocalDate dataApuracao, Produto produto) {
		String sql = "select sum(et.quantidade_uso) from estoque_transiente as et where et.produto = :produto";
		Query q = em.createNativeQuery(sql);
		q.setParameter("produto", produto.getCodigo());
		BigDecimal b = (BigDecimal) q.getSingleResult();
		if (b == null) {
			b = BigDecimal.ZERO;
		}
		return b;
	}
	
	public BigDecimal getEstoqueDisponivel(Produto p) {
		return getEstoque(LocalDate.now(), p);
	}

	public BigDecimal getEstoque(LocalDate dataApuracao, Produto produto) {
		validarDados(dataApuracao, produto);
		BigDecimal qtdVenda = getQuantidadeVenda(dataApuracao, produto);
		BigDecimal qtdEstoqueEntrada = getQuantidadeAjusteEstoqueEntrada(dataApuracao, produto);
		BigDecimal qtdEstoqueSaida = getQuantidadeAjusteEstoqueSaida(dataApuracao, produto);
		BigDecimal qtdEstoqueTransiente = getEstoqueTransiente(dataApuracao,produto);
		return qtdEstoqueEntrada.subtract(qtdEstoqueSaida).subtract(qtdVenda).subtract(qtdEstoqueTransiente);
	}

	private void validarDados(Object... valores) {
		Arrays.asList(valores).forEach( e -> {if (e == null) throw new FacesException("Não é possível realizar consultas com parametros nulos");});
	}

}
