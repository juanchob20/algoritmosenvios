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
    private String codVuelo;
    private int codigoCiudadOrigen;
    private int codigoCiudadDestino;
    private int capacidadCarga;
    private int capacidadDisponible;
    private Date FechaPartida;
    private Date fechaLlegada;
    private double duracion;
    private double costoPorPaquete;
    private String codigoEstado;
    
    public Vuelo() {
    }

    public String getCodVuelo() {
        return codVuelo;
    }

    public int getCodigoCiudadOrigen() {
        return codigoCiudadOrigen;
    }

    public int getCodigoCiudadDestino() {
        return codigoCiudadDestino;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public int getCapacidadDisponible() {
        return capacidadDisponible;
    }

    public Date getFechaPartida() {
        return FechaPartida;
    }

    public Date getFechaLlegada() {
        return fechaLlegada;
    }

    public double getDuracion() {
        return duracion;
    }

    public double getCostoPorPaquete() {
        return costoPorPaquete;
    }

    public String getCodigoEstado() {
        return codigoEstado;
    }

    public void setCodVuelo(String codVuelo) {
        this.codVuelo = codVuelo;
    }

    public void setCodigoCiudadOrigen(int codigoCiudadOrigen) {
        this.codigoCiudadOrigen = codigoCiudadOrigen;
    }

    public void setCodigoCiudadDestino(int codigoCiudadDestino) {
        this.codigoCiudadDestino = codigoCiudadDestino;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public void setCapacidadDisponible(int capacidadDisponible) {
        this.capacidadDisponible = capacidadDisponible;
    }

    public void setFechaPartida(Date FechaPartida) {
        this.FechaPartida = FechaPartida;
    }

    public void setFechaLlegada(Date fechaLlegada) {
        this.fechaLlegada = fechaLlegada;
    }

    public void setDuracion(double duracion) {
        this.duracion = duracion;
    } 

    public void setCostoPorPaquete(double costoPorPaquete) {
        this.costoPorPaquete = costoPorPaquete;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
}
