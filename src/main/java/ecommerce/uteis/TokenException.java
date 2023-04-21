package ecommerce.uteis;

public class TokenException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public TokenException() {
		super("Essa operação não pode ser realizada por ser uma transação antiga!");
	}

}
