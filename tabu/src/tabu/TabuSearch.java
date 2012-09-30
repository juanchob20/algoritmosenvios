package tabu;

import Data.Ciudad;
import Data.Envio;
import Data.Vuelo;
import java.util.*;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 * 
 * @author kat
 */
public class TabuSearch {

    private ArrayList<Ciudad> listaCiudades;
    private HashMap ciudades;
    private MultiKeyMap matrizVuelos;
    private int nroIteraciones;
    private int penalidad;
    private Envio envio;
    private TabuList tabuList;

    public Double getObjectiveFunctionValue(ArrayList<Ciudad> solution){              
        Ciudad ciudadOrigen = (Ciudad) ciudades.get(envio.getCiudadOrigen());
        Ciudad ciudadDestino = (Ciudad) ciudades.get(envio.getCiudadDestino());                        
        int cantPaquetes = envio.getCantPaquetes();
        int indexOrigen = solution.indexOf(ciudadOrigen);
        int indexDestino = solution.indexOf(ciudadDestino);        
        if (indexOrigen > indexDestino) return -1.0;
        List<Ciudad> listaAux =  solution.subList(indexOrigen, indexDestino+1);
        
        Ciudad c1 = listaAux.get(0);   
        Ciudad c2 = listaAux.get(1);
        Vuelo v1 = (Vuelo) matrizVuelos.get(c1.getCodigo(), c2.getCodigo());
        if (v1==null) return -1.0;
        c1 = c2;
        Double costo = v1.getCostoPorPaquete()*cantPaquetes;
        
        for (int i=2; i<listaAux.size(); i++){
            c2 = listaAux.get(i);
            Vuelo v2 = (Vuelo) matrizVuelos.get(c1.getCodigo(), c2.getCodigo());
            if (v2==null) return -1.0;
            if (v1.getFechaLlegada().after(v2.getFechaPartida())){
                return -1.0;
            }
            costo += v2.getCostoPorPaquete()*cantPaquetes;
            c1 = c2;
            v1 = v2;
        }   
        
        return costo;
    }
     
    public ArrayList<Ciudad> getBestNeighbour(ArrayList<Ciudad> initSolution, Envio envio) {

        ArrayList<Ciudad> bestSolution = initSolution;
        Double bestCost = getObjectiveFunctionValue(initSolution);
        
        int city1 = 0;
        int city2 = 0;

        for (int i = 1; i < bestSolution.size() - 1; i++) {
            for (int j = 2; j < bestSolution.size() - 1; j++) {                
                if (i == j) {
                    continue;
                }               
                ArrayList<Ciudad> newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                double newBestCost = getObjectiveFunctionValue(newBestSol);
                if (newBestCost == -1.0) continue;
                if (newBestCost < bestCost  && !tabuList.isTabuMove(i,j)) { 
                    city1 = i;
                    city2 = j;
                    bestSolution = newBestSol;
                    bestCost = newBestCost;
                }
            }
        }

        if (city1 != 0 && city2!=0) {
            tabuList.decrementTabu();
            tabuList.tabuMove(city1, city2);
        }
        
        System.out.println("El mejor costo es" +bestCost);
        return bestSolution;
    }

 
    public ArrayList<Ciudad> swapOperator(int city1, int city2, ArrayList<Ciudad> solution) {
        ArrayList<Ciudad> auxSolution = solution;
        Ciudad temp = auxSolution.get(city1);        
        auxSolution.set(city1, auxSolution.get(city2));
        auxSolution.set(city2, temp);
        return auxSolution;
    }

    public ArrayList<Ciudad> search() {       
        ArrayList<Ciudad> currSolution = getListaCiudades();
        Collections.shuffle(currSolution);
        tabuList = new TabuList();
        tabuList.setPenalidad(penalidad);
        
        ArrayList<Ciudad> bestSolution = currSolution;
        Double bestCost = getObjectiveFunctionValue(bestSolution);

        for (int i = 0; i < getNroIteraciones(); i++) { 
            currSolution = getBestNeighbour(currSolution, envio);         
            Double currCost = getObjectiveFunctionValue(currSolution);
            if (currCost < bestCost || currCost > -1.0) {
                bestSolution = currSolution;      
                bestCost = currCost;
            }
        }
        
        return bestSolution;
    }

    /**
     * @return the listaCiudades
     */
    public ArrayList<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    /**
     * @param listaCiudades the listaCiudades to set
     */
    public void setListaCiudades(ArrayList<Ciudad> listaCiudades) {
        this.listaCiudades = listaCiudades;        
        Collections.sort(this.listaCiudades, new CustomComparator());
        ciudades = new HashMap();
        Iterator it = this.listaCiudades.listIterator();
        while (it.hasNext()){
            Ciudad c = (Ciudad) it.next();
            ciudades.put(c.getCodigo(), c);
        }
    }

    /**
     * @return the matrizVuelos
     */
    public MultiKeyMap getMatrizVuelos() {
        return matrizVuelos;
    }

    /**
     * @param matrizVuelos the matrizVuelos to set
     */
    public void setMatrizVuelos(MultiKeyMap matrizVuelos) {
        this.matrizVuelos = matrizVuelos;
    }

    /**
     * @return the nroIteraciones
     */
    public int getNroIteraciones() {
        return nroIteraciones;
    }

    /**
     * @param nroIteraciones the nroIteraciones to set
     */
    public void setNroIteraciones(int nroIteraciones) {
        this.nroIteraciones = nroIteraciones;
    }

    /**
     * @return the penalidad
     */
    public int getPenalidad() {
        return penalidad;
    }

    /**
     * @param penalidad the penalidad to set
     */
    public void setPenalidad(int penalidad) {
        this.penalidad = penalidad;
    }

    /**
     * @return the envio
     */
    public Envio getEnvio() {
        return envio;
    }

    /**
     * @param envio the envio to set
     */
    public void setEnvio(Envio envio) {
        this.envio = envio;
    }   

    public static class CustomComparator implements Comparator<Ciudad> {

        @Override
        public int compare(Ciudad c1, Ciudad c2) {
            return c1.getCodigo() > c2.getCodigo()?c1.getCodigo():c2.getCodigo();            
        }
    }
}
