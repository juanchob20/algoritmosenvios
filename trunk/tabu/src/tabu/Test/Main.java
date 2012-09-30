/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabu.Test;

import Data.Ciudad;
import Data.Envio;
import Data.Vuelo;
import Utils.ArchivoXML;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.collections.map.MultiKeyMap;
import org.xml.sax.SAXException;
import tabu.TabuSearch;

/**
 *
 * @author kat
 */

public class Main {
    
    //private static String PATH;
    private static ArrayList <Envio> listaEnvios;
    private static MultiKeyMap matrizVuelos;
    private static ArrayList<Ciudad> listaCiudades;
    
    private static void cargaEnvios() throws ParserConfigurationException, SAXException, IOException {       
        listaEnvios = new ArrayList<>();
        ArchivoXML envios = new ArchivoXML("files\\envios\\envios.xml");
        envios.setListaNodos("simulador.Envio");        
        for (int i=0;i<envios.getCantidadNodos();i++){
            Envio envio = new Envio();
            envio.setCiudadOrigen(Integer.parseInt(envios.getElement(i,"codigoCiudadOrigen")));
            envio.setCiudadDestino(Integer.parseInt(envios.getElement(i,"codigoCiudadDestino")));
            envio.setCantPaquetes(Integer.parseInt(envios.getElement(i,"cantidadPaquetes")));
            listaEnvios.add(envio);
        }
    }
     
    private static void armarListaCiudades() throws ParserConfigurationException, SAXException, IOException {
        listaCiudades = new ArrayList<>();
        ArchivoXML ciudades = new ArchivoXML("files\\ciudades\\ciudades.xml");
        ciudades.setListaNodos("simulador.Ciudad");
        for (int i=0;i<ciudades.getCantidadNodos();i++){
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(Integer.parseInt(ciudades.getElement(i,"codigo")));
            ciudad.setNombre(ciudades.getElement(i, "nombre"));
            ciudad.setSigla(ciudades.getElement(i, "siglas"));
            ciudad.setContinente(ciudades.getElement(i, "continente"));
            listaCiudades.add(ciudad);
        }        
    }

    private static void armarMatrizVuelos(int n) throws ParserConfigurationException, SAXException, IOException {
        matrizVuelos = new MultiKeyMap();
        ArchivoXML vuelos = new ArchivoXML("files\\vuelos\\vuelo"+n+".xml");
        vuelos.setListaNodos("simulador.Vuelo");
        for (int i=0;i<vuelos.getCantidadNodos();i++){
            String codVuelo = vuelos.getElement(i, "codVuelo");
            int codigoCiudadOrigen = Integer.parseInt(vuelos.getElement(i, "codigoCiudadOrigen"));
            int codigoCiudadDestino = Integer.parseInt(vuelos.getElement(i, "codigoCiudadDestino"));
            Double costoPorPaquete = Double.parseDouble(vuelos.getElement(i, "costoPorPaquete"));
            
            String fp = vuelos.getElement(i, "FechaPartida");
            Date FechaPartida;
            SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            try {
                FechaPartida = (Date) df.parse(fp);
            } catch (ParseException e) {
                FechaPartida = null;               
            }
             
            String fll = vuelos.getElement(i, "fechaLlegada");
            Date FechaLlegada;
            df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            try {
                FechaLlegada = (Date) df.parse(fll);
            } catch (ParseException e) {
                FechaLlegada = null;
            }
            
            Vuelo vuelo = new Vuelo();
            vuelo.setCodigoCiudadOrigen(codigoCiudadOrigen);
            vuelo.setCodigoCiudadDestino(codigoCiudadDestino);
            vuelo.setCostoPorPaquete(costoPorPaquete);
            vuelo.setFechaPartida(FechaPartida);
            vuelo.setFechaLlegada(FechaLlegada);
            vuelo.setCodVuelo(codVuelo);
            
            matrizVuelos.put(vuelo.getCodigoCiudadOrigen(), vuelo.getCodigoCiudadDestino(), vuelo);            
        }
        
    }   
    
    public static void main(String[] args) {
        try {            
            cargaEnvios();
            armarListaCiudades();            
            for (int i=0; i<listaEnvios.size();i++){                
                armarMatrizVuelos(i+1); //tsoto - diferentes escenarios
                TabuSearch tabu = new TabuSearch();
                tabu.setEnvio(listaEnvios.get(i));                
                tabu.setListaCiudades(listaCiudades);
                tabu.setMatrizVuelos(matrizVuelos);
                tabu.setNroIteraciones(100);
                tabu.setPenalidad(listaCiudades.size()/5);
                tabu.search();
            }                        
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
