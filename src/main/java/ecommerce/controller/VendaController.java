package ecommerce.controller;

import java.io.Serializable;

import ecommerce.beans.Venda;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class VendaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Venda venda;
	

}
