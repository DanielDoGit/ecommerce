package ecommerce.dao;

import java.time.LocalDateTime;
import java.util.List;

import ecommerce.beans.Caixa;
import ecommerce.beans.Funcionario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Named
@RequestScoped
public class CaixaDao extends Dao<Caixa>{

	private static final long serialVersionUID = 1L;
	
	public CaixaDao() {
		super(Caixa.class);
	}
	
	public List<Caixa> getLancamentos(LocalDateTime data1, LocalDateTime data2, Funcionario funcionario){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Caixa> cq = cb.createQuery(getKlass());
		Root<Caixa> root = cq.from(getKlass());
		Predicate entreDatas = cb.between(root.get("dataLancamento"), data1, data2);
		if (funcionario != null) {
			 cq.where(cb.and(cb.equal(root.get("funcionario"), funcionario), entreDatas));
		}else {
			 cq.where(entreDatas);
		}
		return em.createQuery(cq).getResultList();
	}
	
}
