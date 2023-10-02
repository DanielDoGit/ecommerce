$(document).ready( () => {
	verificarNotificacao();
});

function verificarNotificacao() {
	var msg = [];
	msg = msg.concat(Array.from($('.mensagemInfo')));
	msg = msg.concat(Array.from($('.mensagemWarning')));
	msg = msg.concat(Array.from($('.mensagemError')));
	msg.forEach(e => {
		if (e != undefined) {
			switch (e.classList[1]) {
			case 'mensagemInfo':
				exibirNotificacaoSucesso(e.textContent);
				break;
			case 'mensagemWarning':
				exibirNotificacaoAlerta(e.textContent);
				break;
			case 'mensagemError':
				exibirNotificacaoErro(e.textContent);
				break;
			}
		}
	});
}

function verificarNotificacaoViaAjax() {
	var msg = [];
	msg = msg.concat(Array.from($('.mensagemInfo')));
	msg = msg.concat(Array.from($('.mensagemWarning')));
	msg = msg.concat(Array.from($('.mensagemError')));
	msg.forEach(e => {
		if (e != undefined) {
			switch (e.classList[1]) {
				case 'mensagemInfo':
					exibirNotificacaoSucesso(e.textContent);
					break;
				case 'mensagemWarning':
					exibirNotificacaoAlerta(e.textContent);
					break;
				case 'mensagemError':
					exibirNotificacaoErro(e.textContent);
					break;
			}
		}
	});
}

var etapaRequisicao = 0;
$( document ).on( "ajaxComplete", function() {
	 etapaRequisicao++;
    if (etapaRequisicao === 3) {
        verificarNotificacaoViaAjax();
    }
} );

function validarSubmit() {
	var forms = document.getElementsByClassName('needs-validation');
	if (forms != null) {
		var form = forms[0];
		if (form.checkValidity() === false) {
			event.preventDefault();
			event.stopPropagation();
			exibirNotificacaoAlerta("Existem campos não preenchidos ou inválidos");
			form.classList.add('was-validated');
			return false;
		} else {
			return true;
		}
	} else {
		return false;
	}
}

function exibirNotificacaoSucesso(str) {

	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"positionClass" : "toast-top-right",
		"onclick" : null,
		"showDuration" : "2000",
		"hideDuration" : "2000",
		"timeOut" : "5000",
		"extendedTimeOut" : "2000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut"
	}
	toastr.success(str, "Notificação")
}

function exibirNotificacaoAlerta(str) {

	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"positionClass" : "toast-top-right",
		"onclick" : null,
		"showDuration" : "2000",
		"hideDuration" : "2000",
		"timeOut" : "5000",
		"extendedTimeOut" : "2000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut"
	}
	toastr.warning(str, "Notificação")
}

function exibirNotificacaoErro(str) {

	toastr.options = {
		"closeButton" : true,
		"debug" : false,
		"positionClass" : "toast-top-right",
		"onclick" : null,
		"showDuration" : "2000",
		"hideDuration" : "2000",
		"timeOut" : "5000",
		"extendedTimeOut" : "2000",
		"showEasing" : "swing",
		"hideEasing" : "linear",
		"showMethod" : "fadeIn",
		"hideMethod" : "fadeOut"
	}
	toastr.error(str, "Notificação")
}

function excluir(nomeClasse) {
	var botao = document.getElementsByClassName(nomeClasse)[0];
	bootbox.confirm({
		message : "Deseja realmente prosseguir com esta ação?",
		animate : true,
		buttons : {
			confirm : {
				label : 'Sim',
				className : 'btn-success'
			},
			cancel : {
				label : 'Não',
				className : 'btn-danger'
			}
		},
		callback : function(result) {
			if (result) {
				botao.click();
			}
		}
	});

}

function setFocus(id) {
	var field = document.getElementById(id);
	if (field != null) {
		field.focus();
	}
}

function cnpj(src, event) {
	formatar(src, "##.###.###/####-##", event);
}

function cpf(src, event) {
	formatar(src, "###.###.###-##", event);
}

function cep(src, event) {
	formatar(src, "#####-###", event);
}

function fone(src, event) {
	formatar(src, "(##)####-####", event);
}

function hora(src, event) {
	formatar(src, "##:##", event);
}

function getKeyCode(event) {
	return event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
}

function formatar(src, mask, event) {
	var i = src.value.length;
	var texto = mask.substring(i);
	if (isNumber(event) && i <= mask.length) {
		if (texto.substring(0, 1) != "#") {
			src.value += texto.substring(0, 1);
		}
	}
}

function isNumber(event) {
	var code = getKeyCode(event);
	return (code >= 48 && code <= 57) ? true : false;
}

function formatarValor(campo, tammax, teclapres, decimal) {
	var tecla = teclapres.keyCode;
	var vr = Limpar(campo.value, "0123456789");
	tam = vr.length;
	dec = decimal;
	if (tam < tammax && tecla != 8) {
		tam = vr.length + 1;
	}
	if (tecla == 8) {
		tam = tam - 1;
	}
	if (tecla == 8 || tecla >= 48 && tecla <= 57 || tecla >= 96 && tecla <= 105) {
		if (tam <= dec) {
			campo.value = vr;
		}
		if ((tam > dec) && (tam <= 5)) {
			campo.value = vr.substr(0, tam - 2) + ","
					+ vr.substr(tam - dec, tam);
		}
		if ((tam >= 6) && (tam <= 8)) {
			campo.value = vr.substr(0, tam - 5) + "." + vr.substr(tam - 5, 3)
					+ "," + vr.substr(tam - dec, tam);
		}
		if ((tam >= 9) && (tam <= 11)) {
			campo.value = vr.substr(0, tam - 8) + "." + vr.substr(tam - 8, 3)
					+ "." + vr.substr(tam - 5, 3) + ","
					+ vr.substr(tam - dec, tam);
		}
		if ((tam >= 12) && (tam <= 14)) {
			campo.value = vr.substr(0, tam - 11) + "." + vr.substr(tam - 11, 3)
					+ "." + vr.substr(tam - 8, 3) + "." + vr.substr(tam - 5, 3)
					+ "," + vr.substr(tam - dec, tam);
		}
		if ((tam >= 15) && (tam <= 17)) {
			campo.value = vr.substr(0, tam - 14) + "." + vr.substr(tam - 14, 3)
					+ "." + vr.substr(tam - 11, 3) + "."
					+ vr.substr(tam - 8, 3) + "." + vr.substr(tam - 5, 3) + ","
					+ vr.substr(tam - 2, tam);
		}
	}
}

function valor(src, event) {
	formatarValor(src, 20, event, 2);
}

function somenteNumero(src) {
	src.value = limpar(src.value, "0123456789");
}

function limpar(valor, validos) {
	var result = "";
	var aux;
	for (var i = 0; i < valor.length; i++) {
		aux = validos.indexOf(valor.substring(i, i + 1));
		if (aux >= 0) {
			result += aux;
		}
	}
	return result;
}