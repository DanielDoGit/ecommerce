package ecommerce.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import ecommerce.beans.Cliente;
import ecommerce.beans.Funcionario;
import ecommerce.beans.ItemVenda;
import ecommerce.beans.Recebimento;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

public class VendaDto {

	private Integer codigo;

	private Integer idCliente;

	private String nomeCliente;

	private Integer idFuncionario;

	private String nomeFuncionario;

	private LocalDate dataVenda;

	private BigDecimal totalVenda;

	private List<ItemVendaDto> itensVendaDto = new ArrayList<>();

	private List<Recebimento> recebimentos = new ArrayList<>();

}
