package tabu;

import Data.Ciudad;
import Data.Envio;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 * @author tsoto Use this code at your own risk ;)
 */
public class TabuSearch {

    private ArrayList<Ciudad> listaCiudades;
    private MultiKeyMap matrizVuelos;
    private int nroIteraciones;
    private int penalidad;
    private Envio envio;

    public ArrayList<Ciudad> getBestNeighbour(TabuList tabuList, TSPEnvironment tspEnviromnet,
            ArrayList<Ciudad> initSolution, Envio envio) {

        ArrayList<Ciudad> bestSolution = initSolution;
        Double bestCost = tspEnviromnet.getObjectiveFunctionValue(envio, initSolution);
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
                double newBestCost = tspEnviromnet.getObjectiveFunctionValue(envio, newBestSol);

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
    public ArrayList<Ciudad> swapOperator(int city1, int city2, ArrayList<Ciudad> solution) {
        Ciudad temp = solution.get(city1);
        solution.set(city1, solution.get(city2));
        solution.set(city2, temp);
        return solution;
    }

    public ArrayList<Ciudad> search() {
        
        TSPEnvironment tspEnvironment = new TSPEnvironment();        
        
        ArrayList<Ciudad> currSolution = getListaCiudades();
        Collections.shuffle(currSolution);
        
        TabuList tabuList = new TabuList(getPenalidad());

        ArrayList<Ciudad> bestSolution = currSolution;
        Double bestCost = tspEnvironment.getObjectiveFunctionValue(envio, bestSolution);

        for (int i = 0; i < getNroIteraciones(); i++) { 

            currSolution = getBestNeighbour(tabuList, tspEnvironment, currSolution, envio);         
            Double currCost = tspEnvironment.getObjectiveFunctionValue(envio, currSolution);
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
            if (c1.getCodigo() > c2.getCodigo()) {
                return c1.getCodigo();
            } else {
                return c2.getCodigo();
            }

        }
    }
}
