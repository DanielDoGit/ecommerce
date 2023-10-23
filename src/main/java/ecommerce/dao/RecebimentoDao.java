package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Cliente;
import ecommerce.beans.Recebimento;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Named
@RequestScoped
public class RecebimentoDao extends Dao<Recebimento>{

	private static final long serialVersionUID = 1L;
	
	public RecebimentoDao() {
		super(Recebimento.class);
	}
	
	public List<Recebimento> getRecebimentosByCliente(Cliente c) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Recebimento> cq = cb.createQuery(Recebimento.class);
		Root<Recebimento> root =  cq.from(Recebimento.class);
		cq.select(root).where(cb.and(cb.equal(root.get("cliente"), c), cb.equal(root.get("quitado"), true)));
		return em.createQuery(cq).getResultList();
	}

}
