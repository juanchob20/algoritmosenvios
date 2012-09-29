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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        ArchivoXML envios = new ArchivoXML("../archivos/envio.xml");
        envios.setListaNodos("envios");        
        for (int i=0;i<envios.getCantidadNodos();i++){
            Envio envio = new Envio();
            envio.setCiudadOrigen(Integer.parseInt(envios.getElement(i,"corigen")));
            envio.setCiudadDestino(Integer.parseInt(envios.getElement(i,"cdestino")));
            envio.setCantPaquetes(Integer.parseInt(envios.getElement(i,"cantidad")));
            listaEnvios.add(envio);
        }
    }
     
    private static void armarListaCiudades() throws ParserConfigurationException, SAXException, IOException {
        listaCiudades = new ArrayList<>();
        ArchivoXML ciudades = new ArchivoXML("C:\\XML\\ciudades.xml");
        ciudades.setListaNodos("ciudad");
        for (int i=0;i<ciudades.getCantidadNodos();i++){
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(Integer.parseInt(ciudades.getElement(i,"codigo")));
            ciudad.setNombre(ciudades.getElement(i, "nombre"));
            ciudad.setSigla(ciudades.getElement(i, "sigla"));
            listaCiudades.add(ciudad);
        }
        //Collections.sort(listaCiudades, CustomComparator);    
    }

    private static void armarMatrizVuelos(int n) throws ParserConfigurationException, SAXException, IOException {
        matrizVuelos = new MultiKeyMap();
        ArchivoXML vuelos = new ArchivoXML("..\\archivos\\vuelos\\vuelo"+n+".xml");
        vuelos.setListaNodos("vuelo");
        for (int i=0;i<vuelos.getCantidadNodos();i++){
            int ciudadOrig = Integer.parseInt(vuelos.getElement(i, "cinicio"));
            int ciudadDest = Integer.parseInt(vuelos.getElement(i, "cdestino"));
            Double costo = Double.parseDouble(vuelos.getElement(i, "costo"));
            Date fechaSalida = Date.valueOf(vuelos.getElement(i, "fechapartida"));
            Date fechaLlegada = Date.valueOf(vuelos.getElement(i, "fechallegada"));
            
            Vuelo vuelo = new Vuelo();
            vuelo.setCiudadOrigen(ciudadOrig);
            vuelo.setCiudadDestino(ciudadDest);
            vuelo.setCosto(costo);
            vuelo.setFechaSalida(fechaSalida);
            vuelo.setFechaLlegada(fechaLlegada);
            
            ArrayList<Vuelo> aux = (ArrayList<Vuelo>) matrizVuelos.get(vuelo.getCiudadOrigen(), vuelo.getCiudadDestino());
            
            if (aux==null) {
                ArrayList<Vuelo> auxList = new ArrayList<>();
                auxList.add(vuelo);
                matrizVuelos.put(vuelo.getCiudadOrigen(), vuelo.getCiudadDestino(), auxList);
            } else {
                aux.add(vuelo);
                matrizVuelos.put(vuelo.getCiudadOrigen(), vuelo.getCiudadDestino(), aux);
            }
        }
        
    }   
    
    public static void main(String[] args) {
        try {            
            cargaEnvios();
            armarListaCiudades();            
            for (int i=0; i<listaEnvios.size();i++){                
                armarMatrizVuelos(i);
                TabuSearch tabu = new TabuSearch();
                tabu.setEnvio(listaEnvios.get(i));                
                tabu.setListaCiudades(listaCiudades);
                tabu.setMatrizVuelos(matrizVuelos);
                tabu.setNroIteraciones(100);
                tabu.setPenalidad(listaCiudades.size()/5);
                ArrayList<Ciudad> solucion = tabu.search();
            }                        
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
