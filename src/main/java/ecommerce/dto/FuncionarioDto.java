package ecommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FuncionarioDto implements Serializable {

	
	private static final long serialVersionUID = 1L;
	List<PermissaoDto> listaPermissoes = new ArrayList<>();
}
