package ecommerce.uteis;

import java.io.Serializable;

public class Formatadores implements Serializable {

	private static final long serialVersionUID = 1L;

	private Uteis uteis = new Uteis();

	public String formatarCpf(String cpf) {
		try {
			if (cpf != null && !cpf.isBlank()) {
				StringBuffer s = new StringBuffer(uteis.extrairNumeros(cpf));
				s.insert(3, ".");
				s.insert(7, ".");
				s.insert(11, "-");
				return s.toString();
			} else {
				return "";
			}
		} catch (Exception exe) {
			return "";
		}
	}

	public String formatarCnpj(String arg) {
		try {
			if (arg != null && !arg.isBlank()) {
				StringBuilder st = new StringBuilder(uteis.extrairNumeros(arg));
				st.insert(2, ".");
				st.insert(6, ".");
				st.insert(10, "/");
				st.insert(15, "-");
				return st.toString();
			} else {
				return "";
			}
		} catch (Exception exe) {
			return "";
		}
	}

}
