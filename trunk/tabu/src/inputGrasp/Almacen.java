package inputGrasp;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */




/**
 *
 * @author Software
 */
public class Almacen {
    private int codigo;
    private Ciudad ciudad;
    private int capacidadCarga;
    private int capacidadDisponible;
    private String estado;
    private int costo;

    public Almacen(int codigo, Ciudad ciudad, int capacidadCarga, int capacidadDisponible, String estado, int costo) {
        this.codigo = codigo;
        this.ciudad = ciudad;
        this.capacidadCarga = capacidadCarga;
        this.capacidadDisponible = capacidadDisponible;
        this.estado = estado;
        this.costo = costo;
    }
    
    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public Ciudad getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad ciudad) {
        this.ciudad = ciudad;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(int capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public int getCapacidadDisponible() {
        return capacidadDisponible;
    }

    public void setCapacidadDisponible(int capacidadDisponible) {
        this.capacidadDisponible = capacidadDisponible;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCosto() {
        return costo;
    }

    public void setCosto(int costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "Almacen{" + "codigo=" + codigo + ", ciudad=" + ciudad + ", capacidadCarga=" + capacidadCarga + ", capacidadDisponible=" + capacidadDisponible + ", estado=" + estado + ", costo=" + costo + '}';
    }
}
