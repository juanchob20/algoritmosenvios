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
        ArchivoXML envios = new ArchivoXML("files/envios.xml");
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
        ArchivoXML ciudades = new ArchivoXML("files/ciudades.xml");
        ciudades.setListaNodos("simulador.Ciudad");
        for (int i=0;i<ciudades.getCantidadNodos();i++){
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(Integer.parseInt(ciudades.getElement(i,"codigo")));
            ciudad.setNombre(ciudades.getElement(i, "nombre"));
            ciudad.setSigla(ciudades.getElement(i, "sigla"));
            ciudad.setContinente(ciudades.getElement(i, "continente"));
            listaCiudades.add(ciudad);
        }        
    }

    private static void armarMatrizVuelos(int n) throws ParserConfigurationException, SAXException, IOException {
        matrizVuelos = new MultiKeyMap();
        ArchivoXML vuelos = new ArchivoXML("files/vuelos/vuelo"+n+".xml");
        vuelos.setListaNodos("simulador.Vuelo");
        for (int i=0;i<vuelos.getCantidadNodos();i++){
            String codVuelo = vuelos.getElement(i, "codVuelo");
            int codigoCiudadOrigen = Integer.parseInt(vuelos.getElement(i, "codigoCiudadOrigen"));
            int codigoCiudadDestino = Integer.parseInt(vuelos.getElement(i, "codigoCiudadDestino"));
            Double costoPorPaquete = Double.parseDouble(vuelos.getElement(i, "costoPorPaquete"));
            Date FechaPartida = Date.valueOf(vuelos.getElement(i, "FechaPartida"));
            Date fechaLlegada = Date.valueOf(vuelos.getElement(i, "fechaLlegada"));
            
            Vuelo vuelo = new Vuelo();
            vuelo.setCodigoCiudadOrigen(codigoCiudadOrigen);
            vuelo.setCodigoCiudadDestino(codigoCiudadDestino);
            vuelo.setCostoPorPaquete(costoPorPaquete);
            vuelo.setFechaPartida(FechaPartida);
            vuelo.setFechaLlegada(fechaLlegada);
            vuelo.setCodVuelo(codVuelo);
            
            ArrayList<Vuelo> aux = (ArrayList<Vuelo>) matrizVuelos.get(vuelo.getCodigoCiudadOrigen(), 
                    vuelo.getCodigoCiudadDestino());
            
            if (aux==null) {
                ArrayList<Vuelo> auxList = new ArrayList<>();
                auxList.add(vuelo);
                matrizVuelos.put(vuelo.getCodigoCiudadOrigen(), vuelo.getCodigoCiudadDestino(), auxList);
            } else {
                aux.add(vuelo);
                matrizVuelos.put(vuelo.getCodigoCiudadOrigen(), vuelo.getCodigoCiudadDestino(), aux);
            }
        }
        
    }   
    
    public static void main(String[] args) {
        try {            
            cargaEnvios();
            armarListaCiudades();            
            for (int i=0; i<listaEnvios.size();i++){                
                armarMatrizVuelos(i); //tsoto - diferentes escenarios
                TabuSearch tabu = new TabuSearch();
                tabu.setEnvio(listaEnvios.get(i));                
                tabu.setListaCiudades(listaCiudades);
                tabu.setMatrizVuelos(matrizVuelos);
                tabu.setNroIteraciones(100);
                tabu.setPenalidad(listaCiudades.size()/5);
                tabu.search();
                //ArrayList<Ciudad> solucion = tabu.search();
            }                        
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
