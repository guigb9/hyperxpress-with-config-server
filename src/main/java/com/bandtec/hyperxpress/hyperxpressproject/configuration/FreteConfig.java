package com.bandtec.hyperxpress.hyperxpressproject.configuration;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class FreteConfig {
        private String nCdEmpresa;
        private String sDsSenha;
        private String nCdServico;
        private String sCepOrigem;
        private String sCepDestino;
        private String nVlPeso;
        private String nCdFormato;
        private String nVlComprimento;
        private String nVlAltura;
        private String nVlLargura;
        private String nVlDiametro;
        private String sCdMaoPropria;
        private String nVlValorDeclarado;
        private String sCdAvisoRecebimento;
        private String StrRetorno;
        private String freteReturn;
        private String freteError;


    public FreteConfig(String sCepOrigem, String sCepDestino) {
        this.nCdEmpresa = "";
        this.sDsSenha = "";
        this.nCdServico = "04510";
        this.sCepOrigem = sCepOrigem;
        this.sCepDestino = sCepDestino;
        this.nVlPeso = "0.100";
        this.nCdFormato = "1";
        this.nVlComprimento = "20";
        this.nVlAltura = "5";
        this.nVlLargura = "11";
        this.nVlDiametro = "0";
        this.sCdMaoPropria = "s";
        this.nVlValorDeclarado = "50";
        this.sCdAvisoRecebimento = "s";
        StrRetorno = "xml";
        //URL do webservice correio para calculo de frete
        String urlString = "http://ws.correios.com.br/calculador/CalcPrecoPrazo.aspx";

        // os parametros a serem enviados
        Properties parameters = new Properties();

        parameters.setProperty("nCdEmpresa", nCdEmpresa);
        parameters.setProperty("sDsSenha", sDsSenha);
        parameters.setProperty("nCdServico", nCdServico);
        parameters.setProperty("sCepOrigem", sCepOrigem);
        parameters.setProperty("sCepDestino", sCepDestino);
        parameters.setProperty("nVlPeso", nVlPeso);
        parameters.setProperty("nCdFormato", nCdFormato);
        parameters.setProperty("nVlComprimento", nVlComprimento);
        parameters.setProperty("nVlAltura", nVlAltura);
        parameters.setProperty("nVlLargura", nVlLargura);
        parameters.setProperty("nVlDiametro", nVlDiametro);
        parameters.setProperty("sCdMaoPropria", sCdMaoPropria);
        parameters.setProperty("nVlValorDeclarado", nVlValorDeclarado);
        parameters.setProperty("sCdAvisoRecebimento", sCdAvisoRecebimento);
        parameters.setProperty("StrRetorno", StrRetorno);

        // o iterador, para criar a URL
        Iterator i = parameters.keySet().iterator();
        // o contador
        int counter = 0;

        // enquanto ainda existir parametros
        while (i.hasNext()) {

            // pega o nome
            String name = (String) i.next();
            // pega o valor
            String value = parameters.getProperty(name);

            // adiciona com um conector (? ou &)
            // o primeiro é ?, depois são &
            urlString += (++counter == 1 ? "?" : "&") + name + "=" + value;

        }

        try {
            // cria o objeto url
            URL url = new URL(urlString);

            // cria o objeto httpurlconnection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // seta o metodo
            connection.setRequestProperty("Request-Method", "GET");

            // seta a variavel para ler o resultado
            connection.setDoInput(true);
            connection.setDoOutput(false);

            // conecta com a url destino
            connection.connect();

            // abre a conexão pra input
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            // le ate o final
            StringBuffer newData = new StringBuffer();
            String s = "";
            while (null != ((s = br.readLine()))) {
                newData.append(s);
            }
            br.close();

            //Prepara o XML que está em string para executar leitura por nodes
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(newData.toString()));
            Document doc = db.parse(is);
            NodeList nodes = doc.getElementsByTagName("cServico");

            //Faz a leitura dos nodes
            for (int j = 0; j < nodes.getLength(); j++) {
                Element element = (Element) nodes.item(j);

                NodeList valor = element.getElementsByTagName("Valor");
                Element line = (Element) valor.item(0);
                freteReturn = getCharacterDataFromElement(line);

                NodeList erro = element.getElementsByTagName("Erro");
                line = (Element) erro.item(0);
                freteError = getCharacterDataFromElement(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


        public static String getCharacterDataFromElement(Element e) {
            Node child = e.getFirstChild();
            if (child instanceof CharacterData) {
                CharacterData cd = (CharacterData) child;
                return cd.getData();
            }
            return "";
    }


    public String getValorFrete(){
        return this.freteReturn;
    }
}
