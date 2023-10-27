package ecommerce.relatorios;

import java.util.HashMap;

import ecommerce.controller.ItemVendaController;
import ecommerce.dto.VendaDto;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class ComprovanteVendaDataSource {

	private HashMap<String, Object> hasmapDatasource;
	private JRBeanCollectionDataSource collectionDataSource;
	
	public ComprovanteVendaDataSource(ItemVendaController controller) {
		VendaDto vendaDto = controller.getVendaDto();
		hasmapDatasource = new HashMap<String, Object>();
		hasmapDatasource.put("codigoVenda", vendaDto.getCodigo().toString());
		hasmapDatasource.put("codigoCliente", vendaDto.getIdCliente());
		hasmapDatasource.put("nomeCliente",  vendaDto.getNomeCliente());
		hasmapDatasource.put("dataVenda", vendaDto.getDataVenda());
		hasmapDatasource.put("totalVenda", vendaDto.getTotalVenda());
		hasmapDatasource.put("descontos", vendaDto.getDesconto());
		hasmapDatasource.put("acrescimos", vendaDto.getAcrescimo());
		hasmapDatasource.put("totalRecebimentos", controller.getTotalRecebimento());
		this.collectionDataSource = new JRBeanCollectionDataSource(controller.getListItemsVenda());
	}

	public HashMap<String, Object> getHasmapDatasource() {
		return hasmapDatasource;
	}

	public JRBeanCollectionDataSource getCollectionDataSource() {
		return collectionDataSource;
	}


}
