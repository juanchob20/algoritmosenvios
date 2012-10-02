/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grasp;

/**
 *
 * @author Jose
 */
public class resultadoExperimentación {
    private int codigoCiudadOrigen;
    private int codigoCiudadDestino;
    private long tiempoNanoSegundos;
    private float tiempoSegundos;
    private double costoSolucion;
    private double valorFuncObjetivo;
    

    /**
     * @return the codigoCiudadOrigen
     */
    public int getCodigoCiudadOrigen() {
        return codigoCiudadOrigen;
    }

    /**
     * @param codigoCiudadOrigen the codigoCiudadOrigen to set
     */
    public void setCodigoCiudadOrigen(int codigoCiudadOrigen) {
        this.codigoCiudadOrigen = codigoCiudadOrigen;
    }

    /**
     * @return the codigoCiudadDestino
     */
    public int getCodigoCiudadDestino() {
        return codigoCiudadDestino;
    }

    /**
     * @param codigoCiudadDestino the codigoCiudadDestino to set
     */
    public void setCodigoCiudadDestino(int codigoCiudadDestino) {
        this.codigoCiudadDestino = codigoCiudadDestino;
    }

    /**
     * @return the tiempoNanoSegundos
     */
    public long getTiempoNanoSegundos() {
        return tiempoNanoSegundos;
    }

    /**
     * @param tiempoNanoSegundos the tiempoNanoSegundos to set
     */
    public void setTiempoNanoSegundos(long tiempoNanoSegundos) {
        this.tiempoNanoSegundos = tiempoNanoSegundos;
    }

    /**
     * @return the costoSolucion
     */
    public double getCostoSolucion() {
        return costoSolucion;
    }

    /**
     * @param costoSolucion the costoSolucion to set
     */
    public void setCostoSolucion(double costoSolucion) {
        this.costoSolucion = costoSolucion;
    }

    /**
     * @return the tiempoSegundos
     */
    public float getTiempoSegundos() {
        return tiempoSegundos;
    }

    /**
     * @param tiempoSegundos the tiempoSegundos to set
     */
    public void setTiempoSegundos(float tiempoSegundos) {
        this.tiempoSegundos = tiempoSegundos;
    }

    /**
     * @return the valorFuncObjetivo
     */
    public double getValorFuncObjetivo() {
        return valorFuncObjetivo;
    }

    /**
     * @param valorFuncObjetivo the valorFuncObjetivo to set
     */
    public void setValorFuncObjetivo(double valorFuncObjetivo) {
        this.valorFuncObjetivo = valorFuncObjetivo;
    }
    
    
    
}
