package ecommerce.controller;

import java.io.Serializable;

import ecommerce.dao.ItemVendaDao;
import ecommerce.dao.VendaDao;
import ecommerce.dto.VendaDto;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.transaction.Transactional;

@Named
@ConversationScoped
@Transactional
public class VendaController implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private VendaDao vendaDao;
	
	@Inject
	private ItemVendaDao itemVendaDao;
	
	private VendaDto vendaDto;
	
	
	

}
