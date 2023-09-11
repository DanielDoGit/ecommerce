package ecommerce.uteis;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;

public class InjectBean {

	public static <T> T newInstanceCDI (Class<T> klass) {
		Instance<T> instancia = CDI.current().select(klass);
		return instancia.get();
	}
	
}
