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
        return "Ciudad{" + "codigo=" + codigo + ", siglas=" + siglas + ", nombre=" + nombre + ", continente=" + continente + '}';
    }
}
