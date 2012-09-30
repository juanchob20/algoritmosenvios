/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grasp;

import com.thoughtworks.xstream.XStream;
import java.text.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author Jose
 */
public class Algoritmo {

    private ArrayList<Vuelo> listaVuelos;
    private ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos;
    private ArrayList<Ciudad> listaCiudades;
    private Almacen[] listaAlmacenes;
    private ArrayList<Vuelo> rcl;
    private ArrayList<Vuelo> listaVecinos;
    private RutaSolucion rutaSolucion;
    private RutaSolucion solucionFinal;
    //PARAMETROS:
    //CON ESTO PRUEBAS EL ALGORITMO
    private Ciudad ciudadInicio = new Ciudad(1, "C203", "C203", "CON2");
    private Ciudad ciudadDestino = new Ciudad(20, "C219", "C219", "CON2");
    private int cantidadPaquetes;
    private int tipoDestino;
    //CONSTANTES(PARTE DE LA CONFIGURACION):
    public static long cantHorasMaxContinental = 24;
    public static long cantHorasMaxInter = 48;
    public static double alfa = 0.42;
    public static int cantMaxVecinos = 10;
    public static int cantMaxCiudades = 100;
    public static int cantMaxVuelosPorArista = 3;
    public static int cantPaquetes = 10;

    /**
     * @param args the command line arguments
     */
    public void ejecutarAlgoritmo() {
        // TODO code application logic here

        //INICIALIZAR VARIABLES 

        //CARGO LA INFORMACION A VUELOS
        listaVuelos = leerVuelos();
        listaCiudades = leerCiudades();
        estructuraVuelos = new ArrayList<>();
        rellenarVuelos(estructuraVuelos, listaVuelos);
        //DE LA LISTA DE VUELOS SE CREA EL ARREGLO 3D QUE FACILITA EL MANEJO
        int i = 0;
        while (i < 10) { /*Condicion de Parada, iteraciones del grasp*/
            //SE INICIALIZA LA RUTA SOLUCION SIN VUELOS PERO CON CIUDAD ORIGEN
            rutaSolucion = new RutaSolucion(ciudadInicio);
            listaVecinos = new ArrayList<>();

            do { //ACA SE BUSCA ITERAR HASTA FORMAR  UNA SOLUCION
                //

                //SE ESCOGE UNA LISTA DE VECINOS Y EN BASE A ELLOS SE FORMA UNA RCL
                listaVecinos = buscarVecinos(getEstructuraVuelos(), rutaSolucion);
                rcl = generarRCL(listaVecinos, rutaSolucion);
                if (rcl == null) {
                } else {
                    //SE AGREGA UN NODO A LA SOLUCION DE FORMA ALEATORIA
                    rutaSolucion = escogerVecino(rutaSolucion, rcl);
                    //System.out.println("Llega a los nodos");
                }
            } while (rutaSolucion.getCiudadActual().getCodigo() != ciudadDestino.getCodigo());

            if (solucionFinal == null || (solucionFinal.getCostoTotal() > rutaSolucion.getCostoTotal())) {
                solucionFinal = rutaSolucion;
            }
            i++;
            System.out.println(i);
        }
        XStream xs = new XStream();
        Scanner in = new Scanner(System.in);

        String temp = xs.toXML(solucionFinal);
        try {
            FileWriter fw = new FileWriter("Solucion.xml");
            fw.write(temp);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
    }

    public void rellenarVuelos(ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos, ArrayList<Vuelo> listaVuelos) {
        int i, j, k;

        for (i = 0; i < cantMaxCiudades; i++) {
            estructuraVuelos.add(new ArrayList<ArrayList<Vuelo>>());
            for (j = 0; j < cantMaxCiudades; j++) {
                estructuraVuelos.get(i).add(new ArrayList<Vuelo>());
            }

        }
        for (i = 0; i < listaVuelos.size(); i++) {

            estructuraVuelos.get(listaVuelos.get(i).getCodigoCiudadOrigen()).
                    get(listaVuelos.get(i).getCodigoCiudadDestino()).add(listaVuelos.get(i));
        }

    }

    ;
    
    public ArrayList<Vuelo> buscarVecinos(ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos, RutaSolucion rutaSolucion) {
        ArrayList<Vuelo> Vecinos = new ArrayList<>();
        int i;
        int j;
        for (i = 0; i < estructuraVuelos.get(rutaSolucion.
                getCiudadActual().getCodigo()).size(); i++) {
            for (j = 0; j < estructuraVuelos.get(rutaSolucion.
                    getCiudadActual().
                    getCodigo()).
                    get(i).size(); j++) {

                Vecinos.add(estructuraVuelos.get(rutaSolucion.getCiudadActual().getCodigo())
                        .get(i)
                        .get(j));

            }

        }

        return Vecinos;

    }

    public ArrayList<Vuelo> generarRCL(ArrayList<Vuelo> listaVecino, RutaSolucion rutaSolucion) {

        double beta;
        double tao;
        double limite;

        ArrayList<Vuelo> listarcl = new ArrayList<>();

        Quicksort sorter = new Quicksort();

        //SI ESTÁ VACIO MOCHAMOS
        if (listaVecino.isEmpty()) {
            return null;
        }

        sorter.sort(listaVecino);
        beta = listaVecino.get(0).getCostoPorPaquete();
        //verificar que el quicksort está ordenando de menor a mayor
        tao = listaVecino.get(listaVecino.size() - 1).getCostoPorPaquete();
        limite = beta + alfa * (tao - beta);

        int i = 0;

        //CORE DE LA FUNCION OBJETIVO

        while (i < listaVecino.size() && listaVecino.get(i).getCostoPorPaquete() <= limite) {
            //Condicion para que llegada sea depues que salida
            //Acá tengo que poner que la fecha salida sea posterior a la hora actual
            //también verificar que el almacen de llegada tenga capacidad
            /*Como tengo la lista de vuelos  puedo buscar todos los almacenes que tengan 
             *como destino mi ciudad  y lleguen antes que yo para tener una cantidad real
             * en el almacen.
             * 
             * para esto necesito que cada vuelo tenga la cantidad de pquetes que envia 
             * a su destino, mejor dicho de su total, cuantos van directo hasta allá
             * y que luego de esto se actualice la lista de vuelos con los valores que se tiene
             * LUEGO DE TODO ESTO RECIEN SE GENERA
             */

            if (rutaSolucion.getCantHorasActual() + listaVecino.
                    //ACA FALTARIA HACER COMPARACION DE QUE LA FECHA DE SALIDA DEL VUELO SEA POSTERIOR
                    //A LA FECHA DE LLEGADA DEL ULTIMO VUELO EN rutaSolucion
                    // Y FILTRAR LOS VUELOS QUE TENGAN DESTINO A ALGUNA CIUDAD QUE YA SE HA TOMADO EN CUENTA
                    //Por ejemplo 1er viaje lima -miami pero hay vuelo miami-lima, para evitar eso,
                    // Se haria un filtro que no tome en cuenta los destinos pasados   
                    get(i).getDuracion() < cantHorasMaxContinental) {
                listarcl.add(listaVecino.get(i));
            }

            i++;
        }
        return listarcl;
    }

    public RutaSolucion escogerVecino(RutaSolucion rutaSol, ArrayList<Vuelo> rcl) {
        Random generadorAleatorio = new Random();

        rutaSol.add(rcl.get(generadorAleatorio.nextInt(rcl.size())), cantPaquetes, getListaCiudades());
        return rutaSol;
    }

    //GETS Y SETS
    /**
     * @return the estructuraVuelo
     */
    /**
     * @return the listaCiudades
     */
    /**
     * @return the listaAlmacenes
     */
    public Almacen[] getListaAlmacenes() {
        return listaAlmacenes;
    }

    /**
     * @param listaAlmacenes the listaAlmacenes to set
     */
    public void setListaAlmacenes(Almacen[] listaAlmacenes) {
        this.listaAlmacenes = listaAlmacenes;
    }

//    public ArrayList<Vuelo> leerVuelos() {
//	// TODO Auto-generated method stub
//	ArrayList<Vuelo> vuelos=new ArrayList<>();
//	try {
//	    XStream xs = new XStream();
//	    FileReader fr = new FileReader("C:\\Users\\Cesar\\Documents\\NetBeansProjects\\Grasp\\vuelos.xml");
//  	    vuelos = (ArrayList<Vuelo>)xs.fromXML(fr);
//	    fr.close();
//	} 
//        catch (IOException e) {
//	    System.out.println(e.toString());
//	}
//        catch (ClassCastException e2){
//            System.out.println(e2.toString());
//	}
//	return vuelos;
//    }
    public static ArrayList<Vuelo> leerVuelos() {
        // TODO Auto-generated method stub
        ArrayList<Vuelo> vuelos = null;
        try {
            XStream xs = new XStream();
            FileReader fr = new FileReader("C:\\Users\\Cesar\\Documents\\NetBeansProjects\\Grasp\\vuelos.xml");
            vuelos = (ArrayList<Vuelo>) xs.fromXML(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
            System.out.println(e2.toString());
        }
        return vuelos;
    }

    public ArrayList<Ciudad> leerCiudades() {
        // TODO Auto-generated method stub
        ArrayList<Ciudad> ciudades = new ArrayList<Ciudad>();
        try {
            XStream xs = new XStream();
            FileReader fr = new FileReader("C:\\Users\\Cesar\\Documents\\NetBeansProjects\\Grasp\\ciudades.xml");
            ciudades = (ArrayList<Ciudad>) xs.fromXML(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
            System.out.println(e2.toString());
        }
        return ciudades;
    }

    /**
     * @return the estructuraVuelos
     */
    public ArrayList<ArrayList<ArrayList<Vuelo>>> getEstructuraVuelos() {
        return estructuraVuelos;
    }

    /**
     * @param estructuraVuelos the estructuraVuelos to set
     */
    public void setEstructuraVuelos(ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos) {
        this.estructuraVuelos = estructuraVuelos;
    }

    /**
     * @return the listaCiudades
     */
    public ArrayList<Ciudad> getListaCiudades() {
        return listaCiudades;
    }

    /**
     * @param listaCiudades the listaCiudades to set
     */
    public void setListaCiudades(ArrayList<Ciudad> listaCiudades) {
        this.listaCiudades = listaCiudades;
    }
}
