package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Produto;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Named
@RequestScoped
public class ProdutoDao extends Dao<Produto> {

	private static final long serialVersionUID = 1L;
	
	public ProdutoDao() {
		super(Produto.class);
	}
	
	public List<Produto> buscarProdutosAtivos(String nomeProduto){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Produto> cq = cb.createQuery(Produto.class);
		Root<Produto> root = cq.from(Produto.class);
		Predicate predicateProduto = cb.like(cb.lower(root.get("descricao")), "%"+nomeProduto.toLowerCase()+"%");
		Predicate predicateAtivo = cb.equal(root.get("ativo"), true);
		cq.select(root).where(cb.and(predicateProduto, predicateAtivo));
		return em.createQuery(cq).getResultList();
	}
	
	
}
