package ecommerce.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.Charset;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.ServletContext;

@Named
@RequestScoped
public class ErroGenericoController {

    private String mensagem;

    @Inject
    private ServletContext servletContext;

    @PostConstruct
    public void inicializar() {
        String caminhoLog;
        if (System.getProperty("os.name").toUpperCase().equals("LINUX")) {
            caminhoLog = servletContext.getInitParameter("caminhoLogLinux");
        } else {
            caminhoLog = servletContext.getInitParameter("caminhoLogWindows");
        }
        FileInputStream fis = null;
        try {
            File arquivo = new File(caminhoLog);
            arquivo.createNewFile();
            fis = new FileInputStream(arquivo);
            byte[] bytes = new byte[fis.available()];
            fis.read(bytes);
            mensagem = new String(bytes, Charset.forName("UTF-8"));
        } catch (Exception e) {
            mensagem = "Não foi possível carregar o erro ocorrido. Por favor contate o suporte!";
        } finally {
            try {
                if (fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                mensagem += System.lineSeparator();
                mensagem += e.getMessage();
            }
        }
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

}
