package tabu;

import Data.Ciudad;
import Data.Envio;
import java.util.ArrayList;
import org.apache.commons.collections.map.MultiKeyMap;

/**
 *
 * @author http://voidException.weebly.com
 * Use this code at your own risk ;)
 */
public class TSPEnvironment { //Tabu Search Environment
    
    private MultiKeyMap matrizVuelos;
    
    public TSPEnvironment(){
        
    }
    
    public Double getObjectiveFunctionValue(Envio envio, ArrayList<Ciudad> solution){      
        Double cost = 0.0;
        return cost;        
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
    
   

}

