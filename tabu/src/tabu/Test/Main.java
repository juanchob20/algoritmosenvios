/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabu.Test;

import Data.Ciudad;
import Data.Envio;
import Data.Vuelo;
import Utils.ArchivoXML;
import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
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
            envio.setTarifa(Double.parseDouble(envios.getElement(i,"tarifa")));
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
            ciudad.setCosto(Integer.parseInt(ciudades.getElement(i, "costo")));
            listaCiudades.add(ciudad);
        }        
    }
    
    public static ArrayList<Vuelo> leerVuelos(String fileName) {        
        ArrayList<Vuelo> vuelos = null;
        try {
            XStream xs = new XStream();
            try (FileReader fr = new FileReader(fileName)) {
                vuelos = (ArrayList<Vuelo>) xs.fromXML(fr);
            }
        } catch (IOException | ClassCastException e) {
            System.out.println(e.toString());
        }
        return vuelos;
    }
    
    private static MultiKeyMap armarMatrizVuelos(int n) throws ParserConfigurationException, SAXException, IOException {        
        ArrayList<Vuelo> vuelos = leerVuelos("files\\vuelos\\vuelo"+n+".xml");
        MultiKeyMap matrizVuelos = new MultiKeyMap();
        for (int i=0;i<vuelos.size();i++){  
            Vuelo vuelo = vuelos.get(i);
            
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
            
            for (int i = 0; i < 10; i++) {
                ArrayList<Ciudad> ciudadesReducidas = armaListaCiudadesReducidas(listaEnvios.get(0));
                if (ciudadesReducidas.isEmpty() || ciudadesReducidas == null){
                    System.out.println("Verificar codigos de ciudades en el envio");
                }                
                MultiKeyMap matrizVuelos = armarMatrizVuelos(1);                 
                TabuSearch tabu = new TabuSearch();                
                tabu.setEnvio(listaEnvios.get(0));                
                tabu.setListaCiudades(ciudadesReducidas);                
                tabu.setMatrizVuelos(matrizVuelos);               
                tabu.setNroIteraciones(1000);
                tabu.setCiudades(indiceCiudades);
                tabu.setPenalidad(10);
                
                long tinicial = System.nanoTime();
                
                tabu.search();
                
                long tfinal = System.nanoTime();
                long tduracion = tfinal-tinicial;
                
                System.out.println("Tiempo = " + tduracion);
              
                XStream xs = new XStream();
                Scanner in = new Scanner(System.in);
                String temp = xs.toXML(tabu.getBestFlightSolution());
                try {
                    FileWriter fw = new FileWriter("Solucion.xml");
                    fw.write(temp);
                    fw.close();
                } catch (IOException e) {
                    System.out.println(e.toString());
                }

                FileWriter fstream = new FileWriter("resultadosTabu.txt");
                try (BufferedWriter out = new BufferedWriter(fstream)) {
                    out.write(tabu.getBestCosto()+","+tduracion);
                    out.close();
                }          
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
