package ecommerce.uteis;

import jakarta.enterprise.inject.Instance;
import jakarta.enterprise.inject.spi.CDI;

public class InjectBean {

	@SuppressWarnings("unchecked")
	public static <T> T newInstanceCDI (Class<T> klass) {
		Instance<?> instancia = CDI.current().select(klass);
		return (T) instancia.get();
	}
	
}
