package ecommerce.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

@Dependent
public class Dao<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private Class<T> klass;

	@PersistenceContext
	protected EntityManager em;
	
	public Dao(Class<T> klass) {
		this.klass = klass;
	}
	
	public Dao() {}
	
	public void cadastrar(T object) {
		em.persist(object);
	}
	
	public void editar(T object) {
		em.merge(object);
	}
	
	public void excluir(Integer id ) {
		T t = getById(id);
		excluir(t);
	}
	
	public void excluir(T object) {
		em.remove(object);
	}
	
	
	public T getById(Integer id) {
		return em.find(klass, id);
	}
	
	public List<T> buscarSimilaridade(String atributeName, String argQuery){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(klass);
		Root<T> root =  cq.from(klass);
		cq.select(root).where(cb.equal(root.get(atributeName), argQuery));
		return em.createQuery(cq).getResultList();
	}
	
	
}
