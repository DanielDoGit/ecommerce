package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Cidade;
import ecommerce.beans.Fornecedor;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@RequestScoped
@Named
public class FornecedorDao extends Dao<Fornecedor> {

	private static final long serialVersionUID = 1L;
	
	public FornecedorDao() {
		super(Fornecedor.class);
	}
	
	public List<Fornecedor> buscarCidade(String nomeCidade){
		CriteriaBuilder criBuilder = em.getCriteriaBuilder();
		CriteriaQuery<Fornecedor> criteriaQuery = criBuilder.createQuery(Fornecedor.class);
		Root<Fornecedor> principal = criteriaQuery.from(Fornecedor.class);
		
		Join<Fornecedor, Cidade> joinCidade = principal.join("cidade", JoinType.INNER);
		
		criteriaQuery.select(principal).where(criBuilder.like(criBuilder.lower(joinCidade.get("nome")), "%"+nomeCidade.toLowerCase()+"%"));
		return em.createQuery(criteriaQuery).getResultList();
	}

}
