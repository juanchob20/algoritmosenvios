package tabu;

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


/**
 *
 * @author http://voidException.weebly.com
 * Use this code at your own risk ;)
 */
public class TabuSearch {

    ArrayList<Ciudad> listaCiudades = new ArrayList<>(); 
    MultiKeyMap matrizVuelos = new MultiKeyMap();
    ArrayList<Envio> listaEnvios = new ArrayList<>();
    
    public ArrayList<Ciudad> getBestNeighbour(TabuList tabuList,TSPEnvironment tspEnviromnet,
            ArrayList <Ciudad> initSolution, Envio envio) {

        ArrayList<Ciudad> bestSolution = initSolution;
        Double bestCost = tspEnviromnet.getObjectiveFunctionValue(envio,initSolution);
        int city1 = 0;
        int city2 = 0;
        boolean firstNeighbor = true;

        for (int i = 1; i < bestSolution.size() - 1; i++) {
            for (int j = 2; j < bestSolution.size() - 1; j++) {
                if (i == j) {
                    continue;
                }

                ArrayList<Ciudad> newBestSol = bestSolution; //this is the best Solution So Far

                newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                // , maybe we get a bettersolution
                double newBestCost = tspEnviromnet.getObjectiveFunctionValue(envio,newBestSol);

                if ((newBestCost < bestCost || firstNeighbor) && tabuList.tabuList[i][j] == 0) { //if better move found, store it
                    firstNeighbor = false;
                    city1 = i;
                    city2 = j;
                    bestSolution = newBestSol;                    
                    bestCost = newBestCost;
                }
            }
        }

        if (city1 != 0) {
            tabuList.decrementTabu();
            tabuList.tabuMove(city1, city2);
        }       
        return bestSolution;
    }

    //swaps two cities
    public ArrayList<Ciudad> swapOperator(int city1, int city2, ArrayList<Ciudad>solution) {
        Ciudad temp = solution.get(city1);
        solution.set(city1,solution.get(city2));
        solution.set(city2, temp);
        return solution;
    }

    
    public void search(int numberOfIterations){
        try {
            cargaEnvio();
            for(int j=0;j<listaEnvios.size();j++){
                armarListaCiudades();
                armarMatrizVuelos();

                TSPEnvironment tspEnvironment = new TSPEnvironment();
                tspEnvironment.setMatrizVuelos(matrizVuelos);

                ArrayList<Ciudad> currSolution = listaCiudades;
                Collections.shuffle(currSolution);

                int tabuLength = listaCiudades.size()/4;
                TabuList tabuList = new TabuList(tabuLength);

                ArrayList<Ciudad> bestSolution = currSolution; 
                Double bestCost = tspEnvironment.getObjectiveFunctionValue(listaEnvios.get(j),bestSolution);

                for (int i = 0; i < numberOfIterations; i++) { // perform iterations here

                    currSolution = getBestNeighbour(tabuList, tspEnvironment, currSolution,listaEnvios.get(j));
                    //printSolution(currSolution);
                    Double currCost = tspEnvironment.getObjectiveFunctionValue(listaEnvios.get(j),currSolution);

                    //System.out.println("Current best cost = " + tspEnvironment.getObjectiveFunctionValue(currSolution));

                    if (currCost < bestCost) {
                        bestSolution = currSolution;
                        bestCost = currCost;
                    }
                }

                System.out.println("Search done! \nBest Solution cost found = " + bestCost + "\nBest Solution :");
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(TabuSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }        


    private void armarListaCiudades() throws ParserConfigurationException, SAXException, IOException {
        ArchivoXML ciudades = new ArchivoXML("C:\\XML\\ciudades.xml");
        ciudades.setListaNodos("ciudad");
        for (int i=0;i<ciudades.getCantidadNodos();i++){
            Ciudad ciudad = new Ciudad();
            ciudad.setCodigo(Integer.parseInt(ciudades.getElement(i,"codigo")));
            ciudad.setNombre(ciudades.getElement(i, "nombre"));
            ciudad.setSigla(ciudades.getElement(i, "sigla"));
            listaCiudades.add(ciudad);
        }
        Collections.sort(listaCiudades, new CustomComparator());
    
    }

    private void armarMatrizVuelos() throws ParserConfigurationException, SAXException, IOException {
        ArchivoXML vuelos = new ArchivoXML("C:\\XML\\vuelos.xml");
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

    private void cargaEnvio() throws ParserConfigurationException, SAXException, IOException {
        ArchivoXML envios = new ArchivoXML("C:\\XML\\envio.xml");
        envios.setListaNodos("envios");        
        for (int i=0;i<envios.getCantidadNodos();i++){
            Envio envio = new Envio();
            envio.setCiudadOrigen(Integer.parseInt(envios.getElement(i,"corigen")));
            envio.setCiudadDestino(Integer.parseInt(envios.getElement(i,"cdestino")));
            envio.setCantPaquetes(Integer.parseInt(envios.getElement(i,"cantidad")));
            listaEnvios.add(envio);
        }
    }
    
    public class CustomComparator implements Comparator<Ciudad> {
        @Override
        public int compare(Ciudad c1, Ciudad c2) {
            if (c1.getCodigo()>c2.getCodigo())
                return c1.getCodigo();
            else return c2.getCodigo();

        }
    }
}
