/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

import java.util.Date;

/**
 *
 * @author kat
 */
public class Vuelo {
    
  /*  -Código Ciudad Inicio
-Código Ciudad Destino
-Capacidad de carga
-Capacidad disponible
-Fecha Partida
-Fecha Llegada
-Duración
-Fecha Partida Real
-Fecha Llegada Real
-Costo por paquete
-Estado*/
        
    private int vuelo;
    private int ciudadOrigen;
    private int ciudadDestino;
    private Date fechaSalida;
    private Date fechaLlegada;
    private Double costo;
    private int capacidad;
    private int capacidadDisponible;

    /**
     * @return the vuelo
     */
    public int getVuelo() {
        return vuelo;
    }

    /**
     * @param vuelo the vuelo to set
     */
    public void setVuelo(int vuelo) {
        this.vuelo = vuelo;
    }

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
     * @return the fechaSalida
     */
    public Date getFechaSalida() {
        return fechaSalida;
    }

    /**
     * @param fechaSalida the fechaSalida to set
     */
    public void setFechaSalida(Date fechaSalida) {
        this.fechaSalida = fechaSalida;
    }

    /**
     * @return the fechaLlegada
     */
    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    /**
     * @param fechaLlegada the fechaLlegada to set
     */
    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    /**
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }

    /**
     * @return the capacidad
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * @return the capacidadDisponible
     */
    public int getCapacidadDisponible() {
        return capacidadDisponible;
    }

    /**
     * @param capacidadDisponible the capacidadDisponible to set
     */
    public void setCapacidadDisponible(int capacidadDisponible) {
        this.capacidadDisponible = capacidadDisponible;
    }
}
