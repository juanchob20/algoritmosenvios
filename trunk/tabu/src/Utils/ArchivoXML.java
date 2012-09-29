/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

/**
 *
 * @author kat
 */

import java.io.FileInputStream;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ArchivoXML {
    private Document xmlFile;
    private String nodo;
    private NodeList listaNodos;
    private int cantidadNodos;
    
    public ArchivoXML(String fileName) throws ParserConfigurationException, SAXException, IOException{
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
        xmlFile = (Document) docBuilder.parse(new FileInputStream(fileName));
        xmlFile.getDocumentElement().normalize();       
    }
    
    public void setListaNodos(String nodo){
        setListaNodos(getXmlFile().getElementsByTagName(nodo));  
        setCantidadNodos(getListaNodos().getLength());
    }
    
    public String getElement(int i,String element){
        Node nodo = listaNodos.item(i);
        if (nodo.getNodeType() == Node.ELEMENT_NODE){
            return getTagValue(element,(Element)nodo);
        }
        return "";        
    }
    
    public String getTagValue(String tag, Element elemento) {
        NodeList lista = elemento.getElementsByTagName(tag).item(0).getChildNodes();
        Node valor = (Node) lista.item(0);
        return valor.getNodeValue();
    }

    /**
     * @return the xmlFile
     */
    public Document getXmlFile() {
        return xmlFile;
    }

    /**
     * @param xmlFile the xmlFile to set
     */
    public void setXmlFile(Document xmlFile) {
        this.xmlFile = xmlFile;
    }

    /**
     * @return the cantidadNodos
     */
    public int getCantidadNodos() {
        return cantidadNodos;
    }

    /**
     * @param cantidadNodos the cantidadNodos to set
     */
    public void setCantidadNodos(int cantidadNodos) {
        this.cantidadNodos = cantidadNodos;
    }

    /**
     * @return the nodo
     */
    public String getNodo() {
        return nodo;
    }

    /**
     * @param nodo the nodo to set
     */
    public void setNodo(String nodo) {
        this.nodo = nodo;
    }

    /**
     * @return the listaNodos
     */
    public NodeList getListaNodos() {
        return listaNodos;
    }

    /**
     * @param listaNodos the listaNodos to set
     */
    public void setListaNodos(NodeList listaNodos) {
        this.listaNodos = listaNodos;
    }
    
}
