package ecommerce.relatorios;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import ecommerce.uteis.jsf.AppException;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.pdf.JRPdfExporter;
import net.sf.jasperreports.pdf.SimplePdfExporterConfiguration;

@Named
@RequestScoped
public class Relatorio {

	private String nomeRelatorio;
	private Map<String, Object> mapa;
	private JRDataSource dataSource;

	public Relatorio() {
	}

	public void nomeRelatorio(String nomeRelatorio) {
		this.nomeRelatorio = nomeRelatorio;
	}

	public void setMap(Map<String, Object> mapa) {
		if (mapa == null) {
			this.mapa = new HashMap<String, Object>();
		} else {
			this.mapa = mapa;
		}

	}

	public void setDataSource(JRDataSource dataSource) {
		if (dataSource == null) {
			this.dataSource = new JREmptyDataSource();
		} else {
			this.dataSource = dataSource;
		}
	}

	public JasperPrint getJasperPrint() throws JRException {
		InputStream io = getClass().getResourceAsStream(nomeRelatorio);
		return JasperFillManager.fillReport(io, mapa, dataSource);
	}

	public void executarRelatorio() throws JRException, IOException, AppException{
		JasperPrint pr = getJasperPrint();
		ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletResponse response = (HttpServletResponse) context.getResponse();
		response.setHeader("Content-disposition", "inline; filename=relatorio.pdf");
		response.setContentType("application/pdf");
		JRPdfExporter exporter = new JRPdfExporter();
		exporter.setExporterInput(SimpleExporterInput.getInstance(Arrays.asList(pr)));  
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(response.getOutputStream()));
		SimplePdfExporterConfiguration config = new SimplePdfExporterConfiguration();
		config.setCreatingBatchModeBookmarks(true);
		exporter.setConfiguration(config);
		exporter.exportReport();
		FacesContext.getCurrentInstance().responseComplete();
	}

}
