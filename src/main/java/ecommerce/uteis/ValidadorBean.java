package ecommerce.uteis;

import java.io.Serializable;
import java.util.Set;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

@Dependent
public class ValidadorBean<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Uteis uteis;

	private Validator validator;

	@PostConstruct
	private void buildValidator() {
		var v = Validation.buildDefaultValidatorFactory();
		validator = v.getValidator();
	}

	public Set<ConstraintViolation<T>> getConstraints(T e) {
		return validator.validate(e);
	}

	public boolean validarCampo(String campo, T valor) {
		return validator.validateValue(valor.getClass(), campo, valor).iterator().hasNext();
	}

	public ConstraintViolation<?> getConstraintField(String campo, T valor) {
		return validator.validateValue(valor.getClass(), campo, valor).iterator().next();
	}

}
