package ecommerce.dao;

import java.util.List;
import java.util.Optional;

import ecommerce.beans.Funcionario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

@Named
@RequestScoped
public class FuncionarioDao extends Dao<Funcionario>{

	private static final long serialVersionUID = 1L;

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Funcionario> realizarlogin(String login, String senha) {
		String sql = "select * from funcionario where login = :varLogin and senha = :varSenha and ativo = :varAtivo";
		Query q = em.createNativeQuery(sql, Funcionario.class);
		q.setParameter("varLogin", login);
		q.setParameter("varSenha", senha);
		q.setParameter("varAtivo", "T");
		List<Funcionario> listaFuncionario = q.getResultList();
		return Optional.ofNullable(listaFuncionario.size() == 1 ? listaFuncionario.get(0) : null);
	}
	
	public List<Funcionario> buscarFuncionarioAtivo(String nomeFuncionario){
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Funcionario> cq = cb.createQuery(Funcionario.class);
		Root<Funcionario> root =  cq.from(Funcionario.class);
		Predicate pNome = cb.like(cb.lower(root.get("nome")), "%"+nomeFuncionario.toLowerCase()+"%");
		Predicate pAtivo = cb.equal(root.get("ativo"), "T");
		cq.select(root).where(cb.and(pNome, pAtivo));
		return em.createQuery(cq).getResultList();
	}


}
