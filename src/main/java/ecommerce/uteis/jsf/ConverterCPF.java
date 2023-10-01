package ecommerce.uteis.jsf;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "converterCPF")
public class ConverterCPF implements Converter<String>, Serializable {

	private static final long serialVersionUID = 1L;

	private Formatadores formatadores = new Formatadores();

	@CPF
	private String cpf;

	private Uteis uteis = new Uteis();

	@Override
	public String getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		ValidadorBean<ConverterCPF> validador = new ValidadorBean<ConverterCPF>();
		if (!arg2.toString().equals("") && !validador.validarCampo(ConverterCPF.class, "cpf", arg2)) {
			throw new ConverterException(new FacesMessage("O CPF informado é invalido!"));
		} else {
			return uteis.extrairNumeros(arg2);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		String result = "";
		if (arg2 != null) {
			result = formatadores.formatarCpf(arg2.toString());
		}
		return result;
	}

}
