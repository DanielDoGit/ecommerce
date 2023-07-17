package ecommerce.dao;

import java.util.List;

import ecommerce.beans.Funcionario;
import jakarta.persistence.Query;

public class FuncionarioDao extends Dao<Funcionario>{

	private static final long serialVersionUID = 1L;

	public FuncionarioDao() {
		super(Funcionario.class);
	}
	
	@SuppressWarnings("unchecked")
	public Funcionario realizarlogin(String login, String senha) {
		String sql = "select * from funcionario where login = ? and senha = ?";
		Query q = em.createNativeQuery(sql, Funcionario.class);
		q.setParameter(1, login);
		q.setParameter(2, senha);
		List<Funcionario> listaFuncionario = q.getResultList();
		return listaFuncionario.size() == 1 ? listaFuncionario.get(0): null;
	}


}
