package ecommerce.uteis;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CNPJ;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "converterCNPJ")
public class ConverterCNPJ implements Converter<String>, Serializable {

	private static final long serialVersionUID = 1L;

	private Formatadores formatadores = new Formatadores();

	@CNPJ
	private String cnpj;

	private Uteis uteis = new Uteis();

	@Override
	public String getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		ValidadorBean<ConverterCNPJ> validador = new ValidadorBean<ConverterCNPJ>();
		if (!arg2.toString().equals("") && !validador.validarCampo(ConverterCNPJ.class, "cnpj", arg2)) {
			throw new ConverterException(new FacesMessage("O CNPJ informado é invalido!"));
		} else {
			return uteis.extrairNumeros(arg2);
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		String result = "";
		if (arg2 != null) {
			result = formatadores.formatarCnpj(arg2.toString());
		}
		return result;
	}

}
