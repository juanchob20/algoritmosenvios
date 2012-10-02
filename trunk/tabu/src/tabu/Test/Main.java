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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
    //private static MultiKeyMap matrizVuelos;
    private static HashMap indiceCiudades;
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
            Ciudad ciudad = new Ciudad(Integer.parseInt(ciudades.getElement(i,"codigo")),
                    ciudades.getElement(i, "siglas"),ciudades.getElement(i, "nombre"),
                    ciudades.getElement(i, "continente"));            
            listaCiudades.add(ciudad);
        }        
    }

    private static MultiKeyMap armarMatrizVuelos(int n) throws ParserConfigurationException, SAXException, IOException {
        MultiKeyMap matrizVuelos = new MultiKeyMap();
        ArchivoXML vuelos = new ArchivoXML("files\\vuelos\\vuelo"+n+".xml");
        vuelos.setListaNodos("simulador.Vuelo");
        for (int i=0;i<vuelos.getCantidadNodos();i++){
            String codVuelo = vuelos.getElement(i, "codVuelo");
            int codigoCiudadOrigen = Integer.parseInt(vuelos.getElement(i, "codigoCiudadOrigen"));
            int codigoCiudadDestino = Integer.parseInt(vuelos.getElement(i, "codigoCiudadDestino"));
            Double costoPorPaquete = Double.parseDouble(vuelos.getElement(i, "costoPorPaquete"));
            
            String fp = vuelos.getElement(i, "FechaPartida");
            Date FechaPartida;
            SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            try {
                FechaPartida = (Date) df.parse(fp);
            } catch (ParseException e) {
                FechaPartida = null;               
            }
             
            String fll = vuelos.getElement(i, "fechaLlegada");
            Date FechaLlegada;
            df = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
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
            ArrayList<Vuelo> aux = (ArrayList<Vuelo>) matrizVuelos.get(vuelo.getCodigoCiudadOrigen(),
                    vuelo.getCodigoCiudadDestino());
            if (aux == null) {
                ArrayList<Vuelo> auxList = new ArrayList<>();
                auxList.add(vuelo);
                matrizVuelos.put(vuelo.getCodigoCiudadOrigen(), vuelo.getCodigoCiudadDestino(), auxList);
            } else {
                aux.add(vuelo);
                matrizVuelos.put(vuelo.getCodigoCiudadOrigen(), vuelo.getCodigoCiudadDestino(), aux);
            }        
        }
        return matrizVuelos;
        
    }   
    
    
//    private static ArrayList<Ciudad> getReducedList(Envio envio , int c, MultiKeyMap matrizVuelos, HashMap pila) {
//        if (c == envio.getCiudadDestino()){
//            ArrayList<Ciudad> solucion = new ArrayList<>();
//            return solucion;
//        } else {
//            ArrayList<Ciudad> solucion = new ArrayList<>();
//            boolean halloVuelo = false;
//            for (MapIterator it = matrizVuelos.mapIterator(); it.hasNext();) {
//                it.next();
//                MultiKey mk = (MultiKey) it.getKey();
//                int k1 = (int) mk.getKey(0);
//                int k2 = (int) mk.getKey(1);
//                if (k1 == c){
//                    ArrayList<Ciudad> auxSolucion = getReducedList(envio, k2, (MultiKeyMap) matrizVuelos.clone(), pila);
//                    if (auxSolucion==null){
//                        continue;
//                    }
//                    else {
//                        halloVuelo = true;
//                        solucion.addAll(auxSolucion);                        
//                    }
//                }
//            }
//            if (halloVuelo == false) return null;
//            solucion.add((Ciudad)indiceCiudades.get(c));
//            if (envio.getCiudadOrigen()==c) solucion.add((Ciudad)indiceCiudades.get(envio.getCiudadDestino()));            
//            return solucion;
//        }                
//    }
    
    public static void main(String[] args) {
        try {            
            cargaEnvios();
            armarListaCiudades();  
            indiceCiudades = new HashMap();
            Iterator it = listaCiudades.listIterator();
            while (it.hasNext()) {
                Ciudad c = (Ciudad) it.next();
                indiceCiudades.put(c.getCodigo(), c);
            }                 
            for (int i = 0; i < listaEnvios.size(); i++) {
                ArrayList<Ciudad> ciudadesReducidas = armaListaCiudadesReducidas(listaEnvios.get(i));
                if (ciudadesReducidas.isEmpty() || ciudadesReducidas == null){
                    System.out.println("Verificar codigos de ciudades en el envio");
                }
                MultiKeyMap matrizVuelos = armarMatrizVuelos(i+1);                 
                TabuSearch tabu = new TabuSearch();                
                tabu.setEnvio(listaEnvios.get(i));                
                tabu.setListaCiudades(ciudadesReducidas);
                tabu.setMatrizVuelos(matrizVuelos);
                tabu.setNroIteraciones(100);
                tabu.setCiudades(indiceCiudades);
                tabu.setPenalidad(10);
                long tinicial = System.nanoTime();
                tabu.search();
                long tfinal = System.nanoTime();
                System.out.println("Tiempo = "+((tfinal-tinicial)));
            }                                               
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    private static ArrayList<Ciudad> armaListaCiudadesReducidas(Envio envio) {
        String cont1 = ((Ciudad)indiceCiudades.get(envio.getCiudadOrigen())).getContinente();
        String cont2 = ((Ciudad)indiceCiudades.get(envio.getCiudadDestino())).getContinente();
        ArrayList<Ciudad> listaReducida = new ArrayList<>();
        for (int i=0;i<listaCiudades.size();i++){
            if (cont1.equals(listaCiudades.get(i).getContinente())||cont2.equals(listaCiudades.get(i).getContinente())){
                listaReducida.add(listaCiudades.get(i));
            }
        }
        return listaReducida;
    }
}
