package ecommerce.uteis;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;

public class InjectBean {

	public static Object newInstanceCDI (Class<?> klass) throws Exception {
		Instance<?> instancia = CDI.current().select(klass);
		if (instancia.isAmbiguous() || instancia.isUnsatisfied()) {
			throw new Exception("Tipo de objeto ambiguo ou não classificado para injeção.");
		}
		return instancia.get();
	}
	
}
