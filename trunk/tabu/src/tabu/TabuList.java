package tabu;

/**
 *
 * @author kat
 */
public class TabuList {
    
    //private MultiKeyMap matrizMovimientos;
    private int penalidad; 
    private int size;
    private int matrizTabu[][];
   
    public TabuList(){
        //matrizMovimientos = new MultiKeyMap();
    }
    
    public void tabuMove(int city1, int city2){
        matrizTabu[city1][city2] = penalidad;        
    }
    
    public void decrementTabu(){
        for (int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                if (matrizTabu[i][j] == 0) continue;
                matrizTabu[i][j] -= 1;
            }
        }
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
        return matrizTabu[i][j] > 0;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
        inicializarMatrizTabu();        
    }

    /**
     * @return the matrizTabu
     */
    public int[][] getMatrizTabu() {
        return matrizTabu;
    }

    /**
     * @param matrizTabu the matrizTabu to set
     */
    public void setMatrizTabu(int[][] matrizTabu) {
        this.matrizTabu = matrizTabu;
    }

    private void inicializarMatrizTabu() {
        this.matrizTabu = new int[size][size];
        for (int i=0;i<size;i++){
            for(int j=i+1;j<size;j++){
                matrizTabu[i][j] = 0;                
            }
        }
    }
    
}
