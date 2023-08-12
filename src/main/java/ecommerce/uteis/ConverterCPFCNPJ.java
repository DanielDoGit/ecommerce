package ecommerce.uteis;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlSelectOneMenu;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;

@FacesConverter(value = "converterCPFCNPJ")
public class ConverterCPFCNPJ implements Converter<String>, Serializable {

	private static final long serialVersionUID = 1L;

	private Formatadores formatadores = new Formatadores();

	@CNPJ
	private String cnpj;

	@CPF
	private String cpf;

	private Uteis uteis;

	private ValidadorBean<ConverterCPFCNPJ> validador = new ValidadorBean<ConverterCPFCNPJ>();

	@Override
	public String getAsObject(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		UIComponent componenteEncontrado = arg1.findComponent("tipoPessoa");
		TipoPessoa valorObtido = (TipoPessoa) ((HtmlSelectOneMenu) componenteEncontrado).getValue();
		try {
			uteis = (Uteis) InjectBean.newInstanceCDI(Uteis.class);
		} catch (Exception e) {
			throw new ConverterException(e.getMessage());
		}
		arg2 = uteis.extrairNumeros(arg2);
		if (valorObtido.compareTo(TipoPessoa.FISICA) == 0) {
			if (validador.validarCampo(ConverterCPFCNPJ.class, "cpf", arg2)) {
				return arg2;
			}else {
				throw new ConverterException(new FacesMessage("O CPF informado é invalido!"));
			}
		}else {
			if (validador.validarCampo(ConverterCPFCNPJ.class, "cnpj", arg2)) {
				return arg2;
			}else {
				throw new ConverterException(new FacesMessage("O CNPJ informado é invalido!"));
			}
		}
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, String arg2) throws ConverterException {
		try {
			uteis = (Uteis) InjectBean.newInstanceCDI(Uteis.class);
		} catch (Exception e) {
			throw new ConverterException(e.getMessage());
		}
		boolean cnpj = validador.validarCampo(ConverterCPFCNPJ.class, "cnpj", arg2);
		boolean cpf = validador.validarCampo(ConverterCPFCNPJ.class, "cpf", arg2);

		if (cnpj) {
			return formatadores.formatarCnpj(arg2);
		} else if (cpf) {
			return formatadores.formatarCpf(arg2);
		} else {
			return "";
		}
	}

}
