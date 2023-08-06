package ecommerce.dao;

import java.util.List;
import java.util.Optional;

import ecommerce.beans.Funcionario;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import jakarta.persistence.Query;

@Named
@RequestScoped
public class FuncionarioDao extends Dao<Funcionario>{

	private static final long serialVersionUID = 1L;

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	@SuppressWarnings("unchecked")
	public Optional<Funcionario> realizarlogin(String login, String senha) {
		String sql = "select * from funcionario where login = ? and senha = ?";
		Query q = em.createNativeQuery(sql, Funcionario.class);
		q.setParameter(1, login);
		q.setParameter(2, senha);
		List<Funcionario> listaFuncionario = q.getResultList();
		return Optional.ofNullable(listaFuncionario.size() == 1 ? listaFuncionario.get(0) : null);
	}
	
	


}
