package tabu;

import Data.Ciudad;
import Data.Envio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 * 
 * @author kat
 */
public class TabuSearch {

    private ArrayList<Ciudad> listaCiudades;
    private MultiKeyMap matrizVuelos;
    private int nroIteraciones;
    private int penalidad;
    private Envio envio;
    private TabuList tabuList;

    public Double getObjectiveFunctionValue(Envio envio, ArrayList<Ciudad> solution){      
        Double cost = 0.0;
        return cost;            
    }
     
    public ArrayList<Ciudad> getBestNeighbour(ArrayList<Ciudad> initSolution, Envio envio) {

        ArrayList<Ciudad> bestSolution = initSolution;
        Double bestCost = getObjectiveFunctionValue(envio, initSolution);
        int city1 = 0;
        int city2 = 0;

        for (int i = 1; i < bestSolution.size() - 1; i++) {
            for (int j = 2; j < bestSolution.size() - 1; j++) {                
                if (i == j) {
                    continue;
                }               
                ArrayList<Ciudad> newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                double newBestCost = getObjectiveFunctionValue(envio, newBestSol);
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
        Double bestCost = getObjectiveFunctionValue(envio, bestSolution);

        for (int i = 0; i < getNroIteraciones(); i++) { 
            currSolution = getBestNeighbour(currSolution, envio);         
            Double currCost = getObjectiveFunctionValue(envio, currSolution);
            if (currCost < bestCost) {
                bestSolution = currSolution;                
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
        this.setListaCiudades(listaCiudades);
        Collections.sort(listaCiudades, new CustomComparator());
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
