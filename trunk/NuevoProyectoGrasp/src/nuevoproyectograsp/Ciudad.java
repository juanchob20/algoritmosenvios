package nuevoproyectograsp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author Software
 */

public class Ciudad {
    private int codigo;
    private String siglas;
    private String nombre;
    private String continente;
    private int costo;
    
    public Ciudad(int codigo, String siglas, String nombre, String continente, int costo){
        this.codigo = codigo;
        this.siglas = siglas;
        this.nombre = nombre;
        this.continente = continente;
        this.costo = costo;
    }
    
    public int getCodigo(){
        return this.codigo;
    }
    public String getSiglas(){
        return this.siglas;
    }
    public String getNombre(){
        return this.nombre;
    }
    public String getContinente(){
        return this.continente;
    }
    @Override
    public String toString() {
        return "Ciudad{" + "codigo=" + codigo + ", siglas=" + siglas + ", nombre=" + nombre + ", continente=" + continente + ", costo=" + getCosto()+'}';
    }

    /**
     * @return the costo
     */
    public int getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(int costo) {
        this.costo = costo;
    }
}

