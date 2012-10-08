/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nuevoproyectograsp;

import java.io.IOException;

/**
 *
 * @author Jose
 */
public class NuevoProyectoGrasp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
           // TODO code application logic here
        
        for (int i=1; i<=45;i++){
            System.out.println("Iteracion "+i);
            Algoritmo grasp = new Algoritmo();
            grasp.leerCiudades();
            grasp.leerVuelos(i);
            grasp.rellenarVuelos();
            grasp.ejecutarAlgoritmo(i);
        }
    }
}
