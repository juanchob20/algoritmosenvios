package grasp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import java.util.ArrayList;

/**
 *
 * @author Software
 */
public class RutaSolucion {
    
    public RutaSolucion() {
        
        indiceActual = 0;
    }
    public RutaSolucion(Ciudad ciudad) {
        listaVuelos = new ArrayList<>();
        ciudadActual=ciudad;
        indiceActual = 0;
        costoTotal = 0;
        cantHorasActual= 0;
    }
    
 private  ArrayList<Vuelo> listaVuelos ;
 private  double costoTotal;
 private  Ciudad ciudadActual;
 private  int    indiceActual;
 private  double   cantHorasActual;
 
 
 
 
 
 public void add(Vuelo vuelo, int cantidadPaquetes, ArrayList<Ciudad> listaCiudades){
     getListaVuelos().add(vuelo);
     indiceActual++;
     costoTotal+=vuelo.getCostoPorPaquete()*cantidadPaquetes;
     this.cantHorasActual+=vuelo.getDuracion();
     int i=0;
     //DEBIERA IR ACA?
     while (listaCiudades.get(i).getCodigo()!=(vuelo.getCodigoCiudadDestino())) 
     {i++;}
     
     ciudadActual = listaCiudades.get(i);
 }
 
 
    
    /**
     * @return the costoTotal
     */
    public double getCostoTotal() {
        return costoTotal;
    }

    /**
     * @param costoTotal the costoTotal to set
     */
    public void setCostoTotal(double costoTotal) {
        this.costoTotal = costoTotal;
    }

    /**
     * @return the costoActual
     */
    
    /**
     * @return the ciudadActual
     */
    public Ciudad getCiudadActual() {
        return ciudadActual;
    }

    /**
     * @param ciudadActual the ciudadActual to set
     */
    public void setCiudadActual(Ciudad ciudadActual) {
        this.ciudadActual = ciudadActual;
    }

    /**
     * @return the indiceActual
     */
    public int getIndiceActual() {
        return indiceActual;
    }

    /**
     * @param indiceActual the indiceActual to set
     */
    public void setIndiceActual(int indiceActual) {
        this.indiceActual = indiceActual;
    }

    /**
     * @return the listaVuelos
     */
    public ArrayList<Vuelo> getListaVuelos() {
        return listaVuelos;
    }

    /**
     * @param listaVuelos the listaVuelos to set
     */
    public void setListaVuelos(ArrayList<Vuelo> listaVuelos) {
        this.listaVuelos = listaVuelos;
    }

    /**
     * @return the cantHorasActual
     */
    public double getCantHorasActual() {
        return cantHorasActual;
    }

    /**
     * @param cantHorasActual the cantHorasActual to set
     */
    public void setCantHorasActual(double cantHorasActual) {
        this.cantHorasActual = cantHorasActual;
    }

    /**
     * @return the listaVuelos
     */

}
