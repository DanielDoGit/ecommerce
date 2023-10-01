package ecommerce.uteis.jsf;

import java.io.Serializable;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Dependent
public class ValidadorBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private Validator validator;

	@PostConstruct
	private void buildValidator() {
		var v = Validation.buildDefaultValidatorFactory();
		validator = v.getValidator();
	}
	
	public ValidadorBean() {
		buildValidator();
	}

	public Set<ConstraintViolation<T>> getConstraints(T e) {
		return validator.validate(e);
	}

	public boolean validarCampo(String campo, Object valor) {
		return validator.validateValue(valor.getClass(), campo, valor).isEmpty();
	}
	
	public boolean validarCampo(Class<T> classe, String campo, Object valor) {
		return validator.validateValue(classe, campo, valor).isEmpty();
	}

	public ConstraintViolation<?> getConstraintField(String campo, T valor) {
		return validator.validateValue(valor.getClass(), campo, valor).iterator().next();
	}

}
