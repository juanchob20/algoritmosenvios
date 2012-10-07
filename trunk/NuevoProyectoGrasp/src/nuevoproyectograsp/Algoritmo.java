/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package nuevoproyectograsp;

import com.thoughtworks.xstream.XStream;
import java.io.BufferedWriter;
import java.text.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jose
 */
public class Algoritmo {

    private ArrayList<Vuelo> listaVuelos;
    private ArrayList<ArrayList<ArrayList<Vuelo>>> estructuraVuelos;
    private ArrayList<Ciudad> listaCiudades;
    private Almacen[] listaAlmacenes;
    //private ArrayList<RutaSolucion> listaSolucionesParaTabu;
    private ArrayList<Vuelo> rcl;
    private ArrayList<Vuelo> listaVecinos;
    private RutaSolucion rutaSolucion;
    private RutaSolucion solucionFinal;
    public static Envio envio = leerXMLEnvio();
    public static int tarifa = envio.tarifa;
    //PARAMETROS:
    // DE EXPERIMENTACION:
    
        private int numeroExperimentos=10;
        private Ciudad ciudadOrigen = new Ciudad(envio.ciudadOrigen, "", "", "",0);
        private Ciudad ciudadDestino = new Ciudad(envio.ciudadDestino, "", "", "", 0);
       //Cuantas veces corremos el experimento:
       private ArrayList<resultadoExperimentación> listaExperimentos;
       private resultadoExperimentación resulExp;
       
       //
    //CON ESTO PRUEBAS EL ALGORITMO

    private int tipoDestino;
    //CONSTANTES(PARTE DE LA CONFIGURACION):
    
    public static long cantHorasMaxContinental = 200; // esto se tiene que cambiar
    public static long cantHorasMaxInter = 48;
    public static double alfa = 0.6;
    public static int cantMaxVecinos = 15;
    public static int cantMaxCiudades = 100;
    public static int cantMaxVuelosPorArista = 20;
    public static int cantPaquetes = envio.cantidadPaquetes;
    

    /**
     * @param args the command line arguments
     */
    public void   /*ArrayList<RutaSolucion>*/ ejecutarAlgoritmo() throws IOException {
        // TODO code application logic here

        //INICIALIZAR VARIABLES 

        //CARGO LA INFORMACION A VUELOS
        // Get current time
        
        listaVuelos = leerVuelos();
        listaCiudades = leerCiudades();
        estructuraVuelos = new ArrayList<>();
        
        rellenarVuelos(estructuraVuelos, listaVuelos);
        //DE LA LISTA DE VUELOS SE CREA EL ARREGLO 3D QUE FACILITA EL MANEJO
        int i = 0;
        int j = 0;
        listaExperimentos = new ArrayList<>();
 
        
        resulExp = new resultadoExperimentación();
        long start = System.nanoTime(); 
        while (i < 1000) { /*Condicion de Parada, iteraciones del grasp*/
            boolean hayRcl =false ;
            boolean existeSolucionParcial=false;
            rcl = null;
            rcl = new ArrayList<>();
            //SE INICIALIZA LA RUTA SOLUCION SIN VUELOS PERO CON CIUDAD ORIGEN
            rutaSolucion = null;
            rutaSolucion = new RutaSolucion(ciudadOrigen);
            listaVecinos = new ArrayList<>();
            
            do { //ACA SE BUSCA ITERAR HASTA FORMAR  UNA SOLUCION
                //
                hayRcl =false ;
                //SE ESCOGE UNA LISTA DE VECINOS Y EN BASE A ELLOS SE FORMA UNA RCL
                listaVecinos = buscarVecinos(getEstructuraVuelos(), rutaSolucion);
                rcl = generarRCL(listaVecinos, rutaSolucion);
                if (rcl.isEmpty()) {
                    existeSolucionParcial=false;
                }
                else{
                    hayRcl=true;
                    
                }
                    
                if (hayRcl) {
                    
                    //SE AGREGA UN NODO A LA SOLUCION DE FORMA ALEATORIA
                    rutaSolucion = escogerVecino(rutaSolucion, rcl);
                    if (rutaSolucion.getCiudadActual().getCodigo()==ciudadDestino.getCodigo()) {
                        existeSolucionParcial=true;
                        break;
                    }
                    //System.out.println("Llega a los nodos");
                }
                
                else {
                    
                    break;
                }
            } while (true);
            
//                    XStream xs = new XStream();
//                    Scanner in = new Scanner(System.in);
//
//                    String temp = xs.toXML(rutaSolucion);
//                    try {
//                        FileWriter fw = new FileWriter("Solucion" +i+".xml");
//                        fw.write(temp);
//                        fw.close();
//                    } catch (IOException e) {
//                        System.out.println(e.toString());
//                    }
//            
            
            if (solucionFinal == null && existeSolucionParcial){
                //listaSolucionesParaTabu.add(rutaSolucion);
                solucionFinal = rutaSolucion;
                
            }
            
            if ((existeSolucionParcial) && ((funcObjetivoSol(solucionFinal) >funcObjetivoSol(rutaSolucion) ) &&(funcObjetivoSol(rutaSolucion)>0))) {
                //descomentar el if para caso de contar solo con costo
            //if ((existeSolucionParcial) && ((solucionFinal.getCostoTotal() > rutaSolucion.getCostoTotal()))) {
                //listaSolucionesParaTabu.add(rutaSolucion); 
                solucionFinal = rutaSolucion;
            }
            
            i++;
            //System.out.println(i);
        }
        //return listaSolucionesParaTabu;
         
        long elapsedTimeNano = System.nanoTime()-start;
        System.out.println("Nanosegundos:" + elapsedTimeNano);
        float elapsedTimeSeg = elapsedTimeNano/1000000000F;
        //System.out.println("MIliSegundos:" + elapsedTimeMili);
        
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
        
        // Get elapsed time in milliseconds
        if (resulExp!=null){
        resulExp.setCodigoCiudadOrigen(ciudadOrigen.getCodigo());
        resulExp.setCodigoCiudadDestino(ciudadDestino.getCodigo());
        
        resulExp.setCostoSolucion(solucionFinal.getCostoTotal());
        resulExp.setTiempoNanoSegundos(elapsedTimeNano);
        resulExp.setTiempoSegundos(elapsedTimeSeg);
        resulExp.setValorFuncObjetivo(funcObjetivoSol(solucionFinal));
        System.out.println(j);
       
    
       
        temp = xs.toXML(resulExp);
       
        try {
            FileWriter fw = new FileWriter("Experimentacion Nodos" + ciudadOrigen.getCodigo() + "-" + ciudadDestino.getCodigo()+ " hash "+ System.currentTimeMillis()+ ".xml");
            fw.write(temp);
            fw.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        
        FileWriter fstream = new FileWriter("resultadosGrasp" + " hash "+ System.currentTimeMillis()+".txt" );
                try (BufferedWriter out = new BufferedWriter(fstream)) {
                    out.write(resulExp.getValorFuncObjetivo()+","+resulExp.getTiempoNanoSegundos());
                    out.close();
                }
                
        }
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
        boolean vetado;
        vetado = false;
        ArrayList<Vuelo> listarcl = new ArrayList<>();

        Quicksort sorter = new Quicksort();

        //SI ESTÁ VACIO MOCHAMOS
        if (listaVecino.isEmpty()) {
            return null;
        }

        sorter.sort(listaVecino);
        //PARA COSTO SE USA EL DE ARRIBA
        //beta = listaVecino.get(0).getCostoPorPaquete();
        beta = funcObjetivoVuelo( listaVecino.get(0));
        //verificar que el quicksort está ordenando de menor a mayor
        tao = funcObjetivoVuelo(listaVecino.get(listaVecino.size() - 1));
        limite = beta + alfa * (tao - beta);
        
        int i = 0;

        //CORE DE LA FUNCION OBJETIVO

        while (i < listaVecino.size() && funcObjetivoVuelo( listaVecino.get(i)) <= limite) {
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
            if (rutaSolucion.getListaVuelos().isEmpty()){
                if (rutaSolucion.getCantHorasActual() + listaVecino.
                 get(i).getDuracion() < cantHorasMaxContinental && (funcObjetivoVuelo(listaVecino.get(i))>0)) {  
                    
                listarcl.add(listaVecino.get(i));
                }
            }
            
            else{
                if (
                      //ESTA LINEA ESTA PARA METERLE HORAS  
                (rutaSolucion.getListaVuelos().get(rutaSolucion.getIndiceActual()-1).
                getFechaLlegada().compareTo(listaVecino.get(i).getFechaPartida())==-1) 
                && (rutaSolucion.getCantHorasActual() + listaVecino.
                get(i).getDuracion() < cantHorasMaxContinental )&& (funcObjetivoVuelo(listaVecino.get(i))>0) ){  
//                 Con esto comparas fechas                     
//                 (rutaSolucion.getListaVuelos().get(rutaSolucion.getIndiceActual()).
//                 getFechaLlegada().compareTo(listaVecino.get(i).getFechaPartida())==-1) 
//                    
                    //ACA FALTARIA HACER COMPARACION DE QUE LA FECHA DE SALIDA DEL VUELO SEA POSTERIOR
                    //A LA FECHA DE LLEGADA DEL ULTIMO VUELO EN rutaSolucion
                    // Y FILTRAR LOS VUELOS QUE TENGAN DESTINO A ALGUNA CIUDAD QUE YA SE HA TOMADO EN CUENTA
                    //Por ejemplo 1er viaje lima -miami pero hay vuelo miami-lima, para evitar eso,
                    // Se haria un filtro que no tome en cuenta los destinos pasados   
                   
                 for (int k=0;k<rutaSolucion.getListaVuelos().size();k++){
                     if(rutaSolucion.getListaVuelos().get(k).getCodigoCiudadOrigen()==
                        listaVecino.get(i).getCodigoCiudadDestino()) {
                         vetado=true;
                         break;
                     }
                 }   
                if (vetado==false){
                   listarcl.add(listaVecino.get(i)); 
                }
                
                }
            }       
            i++;
            vetado=false;
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
            FileReader fr = new FileReader("vuelos.xml");
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
            FileReader fr = new FileReader("ciudades Victor.xml");
            ciudades = (ArrayList<Ciudad>) xs.fromXML(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
            System.out.println(e2.toString());
        }
        return ciudades;
    }
    
    public static Envio leerXMLEnvio() {
        // TODO Auto-generated method stub
        Envio envioaux = null;
        try {
            XStream xs = new XStream();
            FileReader fr = new FileReader("Envio.xml");
            envioaux= (Envio) xs.fromXML(fr);
            fr.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        } catch (ClassCastException e2) {
            System.out.println(e2.toString());
        }
        return envioaux;
    }

    
    public double funcObjetivoVuelo(Vuelo vuelo){
        double valorObjetivo;
        valorObjetivo = (vuelo.getDuracion()*vuelo.getCostoPorPaquete()*cantPaquetes)/(tarifa-vuelo.getCostoPorPaquete()*cantPaquetes);
        return valorObjetivo;
    }
    
    public double funcObjetivoSol(RutaSolucion sol){
        if (sol.getListaVuelos()==null || sol.getListaVuelos().isEmpty()){
            return 1000000;
        }
        else{
             double valorObjetivo;
        valorObjetivo = (sol.getCantHorasActual()*sol.getCostoTotal() /(tarifa -sol.getCostoTotal()));
        return valorObjetivo;    
        }
        
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
