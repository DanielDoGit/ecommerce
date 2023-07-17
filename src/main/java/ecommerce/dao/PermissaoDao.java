package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Permissao;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Named
@SessionScoped
public class PermissaoDao extends Dao<Permissao> {

	private static final long serialVersionUID = 1L;

	public PermissaoDao() {
		super(Permissao.class);
	}

	public List<Permissao> listarTodos() {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Permissao> cq = cb.createQuery(Permissao.class);
		Root<Permissao> root = cq.from(Permissao.class);
		List<Permissao> listaPermissao = em.createQuery(cq.select(root)).getResultList();
		return listaPermissao;
	}

}
