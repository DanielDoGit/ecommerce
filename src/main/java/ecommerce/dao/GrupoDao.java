package ecommerce.dao;

import ecommerce.beans.Grupo;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@RequestScoped
@Named
public class GrupoDao extends Dao<Grupo> {

	private static final long serialVersionUID = 1L;

	public GrupoDao() {
		super(Grupo.class);
	}
	
	
}
