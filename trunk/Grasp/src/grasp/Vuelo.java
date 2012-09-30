package grasp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 *
 * @author Software
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
    private Date FechaPartidaReal;
    private Date FechaLlegadaReal;
    private double costoPorPaquete;
    private String codigoEstado;
    
    public Vuelo(int cOrigen, int cDestino, int cCarga, int cDisponible, Date fPartida, Date fLlegada,
                double durac, double costoPaquete, String codigo){
        this.codigoCiudadOrigen = cOrigen;
        this.codigoCiudadDestino = cDestino;
        this.capacidadCarga = cCarga;
        this.capacidadDisponible = cDisponible;
        this.FechaPartida = fPartida;
        this.fechaLlegada = fLlegada;
        //this.FechaPartidaReal = fPartidaReal;
        //this.FechaLlegadaReal = fLlegadaReal;
        this.duracion = durac;
        this.costoPorPaquete =  costoPaquete;
        //this.codigoEstado = codEstado;
        this.codVuelo = codigo;
    }

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
/*
    public Date getFechaPartidaReal() {
        return FechaPartidaReal;
    }

    public Date getFechaLlegadaReal() {
        return FechaLlegadaReal;
    }*/

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

   /* public void setFechaPartidaReal(Date FechaPartidaReal) {
        this.FechaPartidaReal = FechaPartidaReal;
    }

    public void setFechaLlegadaReal(Date FechaLlegadaReal) {
        this.FechaLlegadaReal = FechaLlegadaReal;
    }*/

    public void setCostoPorPaquete(double costoPorPaquete) {
        this.costoPorPaquete = costoPorPaquete;
    }

    public void setCodigoEstado(String codigoEstado) {
        this.codigoEstado = codigoEstado;
    }
    
    
}


//   Ejemplo de como usar deferencia de horas
//   public long getDifference(Date a, Date b, TimeUnit units) {
//   return units.convert(a.getTime()-b.getTime(), TimeUnit.MILLISECONDS);
//   }
    

