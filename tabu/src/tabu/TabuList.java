package tabu;

import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.keyvalue.MultiKey;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 *
 * @author kat
 */
public class TabuList {
    
    private MultiKeyMap matrizMovimientos;
    private int penalidad; 
   
    public TabuList(){
        matrizMovimientos = new MultiKeyMap();
    }
    
    public void tabuMove(int city1, int city2){
        matrizMovimientos.put(city1, city2, penalidad);        
    }
    
    public void decrementTabu(){
        for (MapIterator  it = matrizMovimientos.mapIterator(); it.hasNext();) {  
            MultiKey mk = (MultiKey) it.getKey();
            int k1 = (int) mk.getKey(0);
            int k2 = (int) mk.getKey(1);
            int value = (int) matrizMovimientos.get(k1, k2);
            if(value == 0){
                matrizMovimientos.remove(k1,k2);
            } else {
                matrizMovimientos.put(k1, k2,value-1);
            }
        }
    }

    /**
     * @return the matrizMovimientos
     */
    public MultiKeyMap getMatrizMovimientos() {
        return matrizMovimientos;
    }

    /**
     * @param matrizMovimientos the matrizMovimientos to set
     */
    public void setMatrizMovimientos(MultiKeyMap matrizMovimientos) {
        this.matrizMovimientos = matrizMovimientos;
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

    boolean isTabuMove(int i, int j) {
        return matrizMovimientos.get(i, j)!=null && matrizMovimientos.get(i, j) != 0;
    }
    
}
