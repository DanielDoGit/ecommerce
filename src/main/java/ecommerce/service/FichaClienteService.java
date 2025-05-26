package ecommerce.service;

import java.io.Serializable;

import ecommerce.beans.Cliente;
import ecommerce.beans.Recebimento;
import ecommerce.dao.CidadeDao;
import ecommerce.dao.ClienteDao;
import ecommerce.dao.RecebimentoDao;
import ecommerce.dao.VendaDao;
import ecommerce.dto.ClienteDto;
import ecommerce.dto.RecebimentoDto;
import ecommerce.dto.RecebimentoDtoFichaCliente;
import ecommerce.uteis.jsf.Uteis;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

@RequestScoped
public class FichaClienteService implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ClienteDao clienteDao;

	@Inject
	private RecebimentoDao recebimentoDao;
	
	@Inject
	private VendaDao vendaDao;

	@Inject
	private CidadeDao cidadeDao;

	@Inject
	private Uteis uteis;

	public ClienteDto carregarClienteDto(String codigoCliente) {
		Cliente cliente = clienteDao.getById(Integer.valueOf(codigoCliente));
		return new ClienteDto(cliente);
	}
	
	public java.util.List<ClienteDto> pesquisarClienteNome(String nomeCliente) throws Exception {
		java.util.List<Cliente> listaClientes = clienteDao.buscarSimilaridade("nome", nomeCliente);
		return uteis.transformListToDto(listaClientes, ClienteDto.class);
	}

	public java.util.List<RecebimentoDtoFichaCliente> listarRecebimentos(ClienteDto dto, boolean quitado) throws Exception {
		java.util.List<Recebimento> listaRecebimentos = null;
		if (quitado) {
			listaRecebimentos = recebimentoDao.getRecebimentosQuitadosByCliente(dto.toCliente(cidadeDao));
		} else {
			listaRecebimentos = recebimentoDao.getRecebimentosAReceberByCliente(dto.toCliente(cidadeDao));
		}
		return uteis.transformListToDto(listaRecebimentos, RecebimentoDtoFichaCliente.class);
	}
	
	public void realizarBaixaTotal(RecebimentoDtoFichaCliente recebimentoDto) {
		 recebimentoDto.toRecebimento(vendaDao);
	}

}
