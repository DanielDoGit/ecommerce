package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Cliente;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@RequestScoped
@Named
public class ClienteDao extends Dao<Cliente> {

	private static final long serialVersionUID = 1L;

	public ClienteDao() {
		super(Cliente.class);
	}

	public List<Cliente> buscarClienteAtivo(String nomeCliente) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Cliente> cq = cb.createQuery(Cliente.class);
		Root<Cliente> root = cq.from(Cliente.class);
		Predicate pNome = cb.like(cb.lower(root.get("nome")), "%" + nomeCliente.toLowerCase() + "%");
		Predicate pAtivo = cb.equal(root.get("ativo"), true);
		cq.select(root).where(cb.and(pNome, pAtivo));
		return em.createQuery(cq).getResultList();
	}

}
