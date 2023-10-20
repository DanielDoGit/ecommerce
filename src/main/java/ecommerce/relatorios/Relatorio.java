package ecommerce.relatorios;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Named
@RequestScoped
@SuppressWarnings("deprecation")
public class Relatorio {
	
	private String nomeRelatorio;
	private Map<String, Object> mapa;
	private JRDataSource dataSource;

	public Relatorio() {}
	
	public void nomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}
	
	public void setMap(Map<String, Object> mapa) {
		this.mapa = mapa;
	}
	
	public void setDataSource(JRDataSource dataSource) {
		if (dataSource == null) {
			this.dataSource = new JREmptyDataSource();
		}else {
			this.dataSource = dataSource;
		}
	}
	
	public JasperPrint getJasperPrint() throws JRException {
		InputStream io = getClass().getResourceAsStream(nomeRelatorio);
		return JasperFillManager.fillReport(io, mapa, dataSource);
	}
	
	public void executarRelatorio() throws JRException, IOException{
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		JRPdfExporter exporter = new JRPdfExporter();
		response.setHeader("Content-disposition", "attachment; filename=relatorio.pdf");
		response.setContentType("application/pdf");
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, response.getOutputStream());
		exporter.setParameter(JRExporterParameter.JASPER_PRINT, getJasperPrint());
		exporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
}
