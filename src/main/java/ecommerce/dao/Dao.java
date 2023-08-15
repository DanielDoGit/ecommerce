package ecommerce.dao;

import java.io.Serializable;
import java.util.List;

import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Root;

@Dependent
public class Dao<T> implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private final Integer quantidade_registros = 5;
	
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
	
	public void excluir(Integer id) {
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
		cq.select(root).where(cb.like(cb.lower(root.get(atributeName)), "%"+argQuery.toLowerCase()+"%"));
		return em.createQuery(cq).getResultList();
	}

	public T buscarExatidao(String atributeName, String argQuery){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(klass);
		Root<T> root =  cq.from(klass);
		cq.select(root).where(cb.equal(root.get(atributeName), argQuery));
		return em.createQuery(cq).getSingleResult();
	}
	
	@SuppressWarnings("unchecked")
	public List<T> buscarUltimosCadastrados(){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<T> cq = cb.createQuery(klass);
		cq.from(klass);
		Query q = em.createQuery(cq).setMaxResults(quantidade_registros);
		return q.getResultList();
	}
	
	public <K> List<T> buscarSimilaridadeInnerJoin(Class<K> joinClass, String parameterField, String argJoing){
		CriteriaBuilder criBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criBuilder.createQuery(klass);
		Root<T> principal = criteriaQuery.from(klass);
		
		Join<T, K> joinRealized = principal.join("cidade", JoinType.INNER);
		criteriaQuery.select(principal).where(criBuilder.like(criBuilder.lower(joinRealized.get(parameterField)), "%"+argJoing.toLowerCase()+"%"));
		return em.createQuery(criteriaQuery).getResultList();
	}
	
	public <K> T buscarExatidaoInnerJoin(Class<K> joinClass, String parameterField, String argJoing){
		CriteriaBuilder criBuilder = em.getCriteriaBuilder();
		CriteriaQuery<T> criteriaQuery = criBuilder.createQuery(klass);
		Root<T> principal = criteriaQuery.from(klass);
		
		Join<T, K> joinRealized = principal.join("cidade", JoinType.INNER);
		criteriaQuery.select(principal).where(criBuilder.like(criBuilder.lower(joinRealized.get(parameterField)), "%"+argJoing.toLowerCase()+"%"));
		return em.createQuery(criteriaQuery).getSingleResult();
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}
	
	
}
