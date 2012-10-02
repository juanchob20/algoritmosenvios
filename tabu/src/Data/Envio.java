/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author kat
 */
public class Envio {
    private int ciudadOrigen;
    private int ciudadDestino;
    private int cantPaquetes;
    private double tarifa;

    /**
     * @return the ciudadOrigen
     */
    public int getCiudadOrigen() {
        return ciudadOrigen;
    }

    /**
     * @param ciudadOrigen the ciudadOrigen to set
     */
    public void setCiudadOrigen(int ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

    /**
     * @return the ciudadDestino
     */
    public int getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * @param ciudadDestino the ciudadDestino to set
     */
    public void setCiudadDestino(int ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    /**
     * @return the cantPaquetes
     */
    public int getCantPaquetes() {
        return cantPaquetes;
    }

    /**
     * @param cantPaquetes the cantPaquetes to set
     */
    public void setCantPaquetes(int cantPaquetes) {
        this.cantPaquetes = cantPaquetes;
    }

    /**
     * @return the tarifa
     */
    public double getTarifa() {
        return tarifa;
    }

    /**
     * @param tarifa the tarifa to set
     */
    public void setTarifa(double tarifa) {
        this.tarifa = tarifa;
    }
}
