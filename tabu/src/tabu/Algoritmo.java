/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tabu;

import Data.Ciudad;
import Data.Vuelo;
import com.thoughtworks.xstream.XStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jose
 */
public class Algoritmo {

    private ArrayList<Vuelo> listaVuelos;
    private ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos;
    private ArrayList<Ciudad> listaCiudades;
    private ArrayList<RutaSolucion> listaSolucionesParaTabu = new ArrayList<>();
    private ArrayList<Vuelo> rcl;
    private ArrayList<Vuelo> listaVecinos;
    private RutaSolucion rutaSolucion;
    private RutaSolucion solucionFinal;
    //PARAMETROS:
    //CON ESTO PRUEBAS EL ALGORITMO
    private Ciudad ciudadInicio; // = new Ciudad(20, "C203", "C203", "CON2");
    private Ciudad ciudadDestino; // = new Ciudad(1, "C219", "C219", "CON2");
    //CONSTANTES(PARTE DE LA CONFIGURACION):
    public static long cantHorasMaxContinental = 24;
    public static long cantHorasMaxInter = 48;
    public static double alfa = 1;
    public static int cantMaxVecinos = 15;
    public static int cantMaxCiudades = 100;
    public static int cantMaxVuelosPorArista = 20;
    public static int cantPaquetes = 1;

    /**
     * @param args the command line arguments
     */
    public ArrayList<Integer> ejecutarAlgoritmo() {
        ArrayList<Integer> listaIndCiudades = new ArrayList<>();
        listaVuelos = leerVuelos();
        listaCiudades = leerCiudades();
        estructuraVuelos = new ArrayList<>();
        listaSolucionesParaTabu = new ArrayList<>();
        boolean esPartedeSolucionTabu;
        rellenarVuelos(estructuraVuelos, listaVuelos);
        int i = 0;
        while (i < 1000) {
            boolean hayRcl;
            boolean existeSolucionParcial = false;

            rcl = new ArrayList<>();
            rutaSolucion = new RutaSolucion(getCiudadInicio());
            listaVecinos = new ArrayList<>();

             while (true) {               
                hayRcl = false;                
                listaVecinos = buscarVecinos(getEstructuraVuelos(), rutaSolucion);
                rcl = generarRCL(listaVecinos, rutaSolucion);
                if (rcl.isEmpty()) {
                    existeSolucionParcial = false;
                } else {
                    hayRcl = true;
                }

                if (hayRcl) {
                    rutaSolucion = escogerVecino(rutaSolucion, rcl);
                    if (rutaSolucion.getCiudadActual().getCodigo() == getCiudadDestino().getCodigo()) {
                        existeSolucionParcial = true;
                        break;
                    }                 
                } else {
                    rutaSolucion = null;
                    break;
                }
            }
            
            if (existeSolucionParcial && rutaSolucion != null) {

                esPartedeSolucionTabu = false;
                for (int j = 0; j < listaSolucionesParaTabu.size(); j++) {
                    if (listaSolucionesParaTabu.get(j).getListaVuelos().equals(rutaSolucion.getListaVuelos())) {
                        esPartedeSolucionTabu = true;
                    }
                }
                if (!esPartedeSolucionTabu) {
                    listaSolucionesParaTabu.add(rutaSolucion);
                    for (int j=0;j<rutaSolucion.getListaVuelos().size();j++){
                        if (!listaIndCiudades.contains(rutaSolucion.getListaVuelos().get(j).getCodigoCiudadOrigen())){
                            listaIndCiudades.add(rutaSolucion.getListaVuelos().get(j).getCodigoCiudadOrigen());
                        }
                        if (!listaIndCiudades.contains(rutaSolucion.getListaVuelos().get(j).getCodigoCiudadDestino())){
                             listaIndCiudades.add(rutaSolucion.getListaVuelos().get(j).getCodigoCiudadDestino());
                        }
                    }
                }
            }
            i++;
            //System.out.println(i);
        }
        return listaIndCiudades;
    }

    public void rellenarVuelos(ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos, ArrayList<Vuelo> listaVuelos) {
        int i, j;

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
        for (i = 0; i < estructuraVuelos.get(rutaSolucion.getCiudadActual().getCodigo()).size(); i++) {
            for (j = 0; j < estructuraVuelos.get(rutaSolucion.getCiudadActual().
                    getCodigo()).
                    get(i).size(); j++) {

                Vecinos.add(estructuraVuelos.get(rutaSolucion.getCiudadActual().getCodigo()).get(i).get(j));

            }

        }

        return Vecinos;

    }

    public ArrayList<Vuelo> generarRCL(ArrayList<Vuelo> listaVecino, RutaSolucion rutaSolucion) {

        boolean vetado;
        vetado = false;
        ArrayList<Vuelo> listarcl = new ArrayList<>();

        Quicksort sorter = new Quicksort();
 
        if (listaVecino.isEmpty()) {
            return null;
        }

        sorter.sort(listaVecino);
//        beta = listaVecino.get(0).getCostoPorPaquete();
//        verificar que el quicksort está ordenando de menor a mayor
//        tao = listaVecino.get(listaVecino.size() - 1).getCostoPorPaquete();
//        limite = beta + alfa * (tao - beta);

        int i = 0;

        //CORE DE LA FUNCION OBJETIVO

        while (i < listaVecino.size()) {
            //Condicion para que llegada sea depues que salida
            //Acá tengo que poner que la fecha salida sea posterior a la hora actual
            //también verificar que el almacen de llegada tenga capacidad
            /*
             * Como tengo la lista de vuelos puedo buscar todos los almacenes
             * que tengan como destino mi ciudad y lleguen antes que yo para
             * tener una cantidad real en el almacen.
             *
             * para esto necesito que cada vuelo tenga la cantidad de pquetes
             * que envia a su destino, mejor dicho de su total, cuantos van
             * directo hasta allá y que luego de esto se actualice la lista de
             * vuelos con los valores que se tiene LUEGO DE TODO ESTO RECIEN SE
             * GENERA
             */
            if (rutaSolucion.getListaVuelos().isEmpty()) {


                listarcl.add(listaVecino.get(i));

            } else {
                if ( //ESTA LINEA ESTA PARA METERLE HORAS  
                        (rutaSolucion.getListaVuelos().get(rutaSolucion.getIndiceActual() - 1).
                        getFechaLlegada().compareTo(listaVecino.get(i).getFechaPartida()) == -1)) {
////                 Con esto comparas fechas                     
////                 (rutaSolucion.getListaVuelos().get(rutaSolucion.getIndiceActual()).
////                 getFechaLlegada().compareTo(listaVecino.get(i).getFechaPartida())==-1) 
////                    
//                    //ACA FALTARIA HACER COMPARACION DE QUE LA FECHA DE SALIDA DEL VUELO SEA POSTERIOR
//                    //A LA FECHA DE LLEGADA DEL ULTIMO VUELO EN rutaSolucion
//                    // Y FILTRAR LOS VUELOS QUE TENGAN DESTINO A ALGUNA CIUDAD QUE YA SE HA TOMADO EN CUENTA
//                    //Por ejemplo 1er viaje lima -miami pero hay vuelo miami-lima, para evitar eso,
//                    // Se haria un filtro que no tome en cuenta los destinos pasados   

                    for (int k = 0; k < rutaSolucion.getListaVuelos().size(); k++) {
                        if (rutaSolucion.getListaVuelos().get(k).getCodigoCiudadOrigen()
                                == listaVecino.get(i).getCodigoCiudadDestino()) {
                            vetado = true;
                            break;
                        }
                    }
                    if (vetado == false) {
                        listarcl.add(listaVecino.get(i));
                    }

                }
            }
            i++;
            vetado = false;
        }
        return listarcl;
    }

    public RutaSolucion escogerVecino(RutaSolucion rutaSol, ArrayList<Vuelo> rcl) {
        Random generadorAleatorio = new Random();
        int valorRandom;
        valorRandom = generadorAleatorio.nextInt(rcl.size());
        rutaSol.add(rcl.get(valorRandom), cantPaquetes, getListaCiudades());
        return rutaSol;
    }

    
    public static ArrayList<Vuelo> leerVuelos() {
        // TODO Auto-generated method stub
        ArrayList<Vuelo> vuelos = null;
        try {
            XStream xs = new XStream();
            try (FileReader fr = new FileReader("vuelos.xml")) {
                vuelos = (ArrayList<Vuelo>) xs.fromXML(fr);
            }
        } catch (IOException | ClassCastException e) {
            System.out.println(e.toString());
        }
        return vuelos;
    }

    public ArrayList<Ciudad> leerCiudades() {
        // TODO Auto-generated method stub
        ArrayList<Ciudad> ciudades = new ArrayList<>();
        try {
            XStream xs = new XStream();
            try (FileReader fr = new FileReader("ciudades.xml")) {
                ciudades = (ArrayList<Ciudad>) xs.fromXML(fr);
            }
        } catch (IOException | ClassCastException e) {
            System.out.println(e.toString());
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

    /**
     * @return the ciudadInicio
     */
    public Ciudad getCiudadInicio() {
        return ciudadInicio;
    }

    /**
     * @param ciudadInicio the ciudadInicio to set
     */
    public void setCiudadInicio(Ciudad ciudadInicio) {
        this.ciudadInicio = ciudadInicio;
    }

    /**
     * @return the ciudadDestino
     */
    public Ciudad getCiudadDestino() {
        return ciudadDestino;
    }

    /**
     * @param ciudadDestino the ciudadDestino to set
     */
    public void setCiudadDestino(Ciudad ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }
}
