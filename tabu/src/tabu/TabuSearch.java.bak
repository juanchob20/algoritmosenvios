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
    private HashMap indicesFor;
    private int contFor;
    private int nroIteraciones;
    private int penalidad;
    private Envio envio;
    private TabuList tabuList;

    public Double getObjectiveFunctionValue(ArrayList<Ciudad> solution){              
        Ciudad ciudadOrigen = (Ciudad) getCiudades().get(envio.getCiudadOrigen());
        Ciudad ciudadDestino = (Ciudad) getCiudades().get(envio.getCiudadDestino());                        
        int cantPaquetes = envio.getCantPaquetes();
        
        int indexOrigen = solution.indexOf(ciudadOrigen);                
        int indexDestino = solution.indexOf(ciudadDestino);                
        
        Double tiempoTotal = 0.0;
        Double costo = 0.0;
        
        if (indexOrigen > indexDestino) return Math.random()*100.0+10000.0;
        List<Ciudad> listaAux =  solution.subList(indexOrigen, indexDestino+1);
        
        Ciudad c1 = listaAux.get(0);   
        Ciudad c2 = listaAux.get(1);
        
        ArrayList<Vuelo> listaV1 = (ArrayList<Vuelo>) matrizVuelos.get(c1.getCodigo(), c2.getCodigo());
        if (listaV1 == null || listaV1.isEmpty()) return Math.random()*100.0+10000.0;
        Vuelo v1 = obtieneMenor(null,listaV1);
        c1 = c2;
        costo += v1.getCostoPorPaquete()*cantPaquetes;
        tiempoTotal += v1.getDuracion();
        
        for (int i=2; i<listaAux.size(); i++){
            c2 = listaAux.get(i);
            ArrayList<Vuelo> listaV2 = (ArrayList<Vuelo>) matrizVuelos.get(c1.getCodigo(), c2.getCodigo());
            if (listaV2 == null || listaV2.isEmpty()) return Math.random()*100.0+10000.0;
            Vuelo v2 = obtieneMenor(v1,listaV2);
            if (v2 == null) return Math.random()*100.0+10000.0;  
            long tiempoAlmacen = v2.getFechaPartida().getTime() - v1.getFechaLlegada().getTime();           
            costo += v2.getCostoPorPaquete()*cantPaquetes + (tiempoAlmacen/(1000*60*60))*c2.getCosto();
            tiempoTotal = tiempoTotal + v2.getDuracion() + (tiempoAlmacen/(1000*60*60));
            v1 = v2;
            c1 = c2;            
        }
        
        if (envio.getTarifa()<=costo) return Math.random()*100.0+10000.0;
        return costo*tiempoTotal/(envio.getTarifa()-costo);
    }
     
    public ArrayList<Ciudad> getBestNeighbour(ArrayList<Ciudad> initSolution) {

        ArrayList<Ciudad> bestSolution = (ArrayList<Ciudad>) initSolution.clone();
        Double bestCost = getObjectiveFunctionValue(bestSolution);
        //System.out.println("---- El costo inicial del vecindario es "+bestCost);
        
        int city1 = 0;
        int city2 = 0;
        
        int cont = 0;
        for (int i = 0; i < bestSolution.size()-1; i++) {
            for (int j = i+1; j < bestSolution.size(); j++) {                                              
                cont++;
                ArrayList<Ciudad> newBestSol = swapOperator(i, j, initSolution); //Try swapping cities i and j
                double newBestCost = getObjectiveFunctionValue(newBestSol);
                //System.out.println("------ El costo de la solucion "+cont+" del vecindario es "+newBestCost);                
                if ((newBestCost < bestCost) && !tabuList.isTabuMove(i,j)) { 
                    city1 = i;
                    city2 = j;
                    bestSolution = (ArrayList<Ciudad>) newBestSol.clone();
                    bestCost = newBestCost;
                    //System.out.println("-------- No es tabu move, solucion "+cont+":"+ bestCost);
                }
            }
        }

        if (city1 == 0 && city2 == 0) {
            Collections.shuffle(bestSolution);
        } else {           
            tabuList.decrementTabu();         
            tabuList.tabuMove(city1, city2);
        }
        
        return bestSolution;
    }

 
    public ArrayList<Ciudad> swapOperator(int city1, int city2, ArrayList<Ciudad> solution) {
        ArrayList<Ciudad> auxSolution = (ArrayList<Ciudad>) solution.clone();
        Ciudad temp = auxSolution.get(city1);        
        auxSolution.set(city1, auxSolution.get(city2));
        auxSolution.set(city2, temp);
        return auxSolution;
    }

    public ArrayList<Ciudad> search() {       
        ArrayList<Ciudad> currSolution = getListaCiudades(); //obtieneSolucionInicial();
        Collections.shuffle(currSolution);
        tabuList = new TabuList();
        tabuList.setPenalidad(penalidad);
        tabuList.setSize(listaCiudades.size());
        
        ArrayList<Ciudad> bestSolution = (ArrayList<Ciudad>) currSolution.clone();
        Double bestCost = getObjectiveFunctionValue(bestSolution);
        //System.out.println("El costo de la solucion inicial es "+ bestCost);
        boolean halloSolValida = false;
        int cont = 0;
        while(!halloSolValida){
            cont++;
            //if (cont == 10) break;
            for (int i = 0; i < getNroIteraciones(); i++) { 
                //System.out.println("-- Iteracion "+i);
                currSolution = getBestNeighbour(currSolution);         
                Double currCost = getObjectiveFunctionValue(currSolution);
                //System.out.println("-- El costo del vecindario "+i+" es "+ currCost);
                if (currCost < bestCost) {
                    bestSolution = (ArrayList<Ciudad>) currSolution.clone();      
                    bestCost = getObjectiveFunctionValue(bestSolution);
                }
            }
            if (bestCost < 10000.0) halloSolValida = true;
        }
        System.out.println("El mejor costo es "+bestCost);
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
        //Collections.sort(this.listaCiudades, new CustomComparator());        
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

    /**
     * @return the indicesFor
     */
    public HashMap getIndicesFor() {
        return indicesFor;
    }

    /**
     * @param indicesFor the indicesFor to set
     */
    public void setIndicesFor(HashMap indicesFor) {
        this.indicesFor = indicesFor;
    }

    /**
     * @return the contFor
     */
    public int getContFor() {
        return contFor;
    }

    /**
     * @param contFor the contFor to set
     */
    public void setContFor(int contFor) {
        this.contFor = contFor;
    }

    /**
     * @return the ciudades
     */
    public HashMap getCiudades() {
        return ciudades;
    }

    /**
     * @param ciudades the ciudades to set
     */
    public void setCiudades(HashMap ciudades) {
        this.ciudades = ciudades;
    }

    private void imprimeSolucion(List<Ciudad> listaAux) {
        for (int i=0;i<listaAux.size();i++){
            System.out.print(listaAux.get(i).getCodigo()+" ");
        }
        System.out.println("");
    }

    private Vuelo obtieneMenor(Vuelo v, ArrayList<Vuelo> listaVuelos) {
        if (listaVuelos == null || listaVuelos.isEmpty()) return null;
        if (v == null){
            Vuelo primerVuelo = listaVuelos.get(0);
            for (int i=1;i<listaVuelos.size();i++){
                if (listaVuelos.get(i).getFechaLlegada().before(primerVuelo.getFechaPartida())){
                    primerVuelo = listaVuelos.get(i);
                }
            }
            return primerVuelo;
        } else {            
            Vuelo v1 = listaVuelos.get(0);
            Vuelo primerVuelo = null;
            boolean halloMenor = false;
            if (v.getFechaLlegada().before(v1.getFechaPartida())){
                primerVuelo = v1;
                halloMenor = true;
            }            
            for (int i=1;i<listaVuelos.size();i++){
                if (primerVuelo!=null && listaVuelos.get(i).getFechaLlegada().before(primerVuelo.getFechaPartida())&&
                        v.getFechaLlegada().before(listaVuelos.get(i).getFechaPartida())){
                    primerVuelo = listaVuelos.get(i);
                    halloMenor = true;
                    
                } else if (v.getFechaLlegada().before(listaVuelos.get(i).getFechaPartida())){
                    primerVuelo = listaVuelos.get(i);
                    halloMenor = true;
                }
            }
            if (halloMenor == false) return null;
            else return primerVuelo;
        }        
    }

    public static class CustomComparator implements Comparator<Ciudad> {

        @Override
        public int compare(Ciudad c1, Ciudad c2) {
            return c1.getCodigo() > c2.getCodigo()?c1.getCodigo():c2.getCodigo();            
        }
    }
}
