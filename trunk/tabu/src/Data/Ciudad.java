/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Data;

/**
 *
 * @author kat
 */
public class Ciudad {
    private int codigo;
    private String siglas;
    private String nombre;
    private String continente;
    private int costo;
    
    public Ciudad(int codigo, String siglas, String nombre, String continente){
        this.codigo = codigo;
        this.siglas = siglas;
        this.nombre = nombre;
        this.continente = continente;
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
        return "Ciudad{" + "codigo=" + getCodigo() + ", siglas=" + siglas + ", nombre=" + nombre + ", continente=" + continente + '}';
    }

    /**
     * @param codigo the codigo to set
     */
    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    /**
     * @param siglas the siglas to set
     */
    public void setSiglas(String siglas) {
        this.siglas = siglas;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param continente the continente to set
     */
    public void setContinente(String continente) {
        this.continente = continente;
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
