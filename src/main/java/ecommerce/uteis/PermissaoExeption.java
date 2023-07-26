package ecommerce.uteis;

public class PermissaoExeption extends Exception{

	private static final long serialVersionUID = 1L;

	public PermissaoExeption(String nomePermissao) {
		super("Usuário sem permissão para funcionalidade: "+nomePermissao);
	}
	
}
