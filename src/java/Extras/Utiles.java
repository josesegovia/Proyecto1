/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: Utiles
 * @autor: Jose Segovia
 * Año: 2017
 */
package Extras;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import Controladores.*;
import Tablas.*;
import java.util.ArrayList;

/**
 *
 * @author Jose Segovia
 */
public class Utiles {

    /**
     * Recibe un string como fecha inicial y un 
     * string tiempo que son los minutos sumados a la fecha inicial.
     * @param fechaInicial
     * La fecha que se desea sumar, en formato "HH:mm:ss dd/MM/yyyy"
     * @param tiempo
     * Tiempo en minutos que se desea sumar
     * @return String
     * Devuelve la suma de la fecha
     * @throws ParseException 
     */
    public static String SumarTiempo(String fechaInicial, String tiempo) throws ParseException{
        String formato = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(formato);
        Date fecha_ini = df.parse(fechaInicial);
        
        int segundos_pasados = Integer.parseInt(tiempo);
        
        Calendar c = Calendar.getInstance();
        c.setTime(fecha_ini);
        c.add(Calendar.MINUTE, segundos_pasados);
        
        return df.format(c.getTime());
        
        
    }
    
    /**
     * Recibe una fecha inicial y una fecha final y devuelve la diferencia en dias, horas, minutos y segundos
     * @param fechaInicial
     * String "HH:mm:ss dd/MM/yyyy"
     * @param fechaFinal
     * @return String
     * @throws ParseException
     */
    public static String DiferenciaFechas(String fechaInicial, String fechaFinal) throws ParseException{
        String formato = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(formato);
        Date fecha_ini = df.parse(fechaInicial);
        Date fecha_fini = df.parse(fechaFinal);
        int diff = (int) ((fecha_fini.getTime()-fecha_ini.getTime())/1000);
//        long diff = ((fecha_fini.getTime()-fecha_ini.getTime()));
        int dias = 0;
        int horas = 0;
        int minutos = 0;
        int segundos = 0;
        
        if(diff>=86400){
            dias=(int)Math.floor(diff/86400);
            diff=diff-(dias*86400);
        }
        if(diff>=3600){
            horas=(int)Math.floor(diff/3600);
            diff=diff-(horas*3600);
        }
        if(diff>=60){
            minutos=(int)Math.floor(diff/60);
            diff=diff-(minutos*60);
        }
        segundos = diff;
        if(dias == 0){
            return horas +" Horas y " + minutos +" minutos " + segundos +" segundos";
        }else{
            return dias +" Dias "+ horas +" Horas y " + minutos +" minutos " + segundos +" segundos";
        }
    }
    /**
     * Recibe una 2 fechas y devuelve la diferencia en un String 
     * con formato dd HH:mm:ss
     * @param fechaInicial String formato "HH:mm:ss dd/MM/yyyy"
     * @param fechaFinal String formato "HH:mm:ss dd/MM/yyyy";
     * @return String con formato "HH:mm:ss" y si sobrepasa 24 horas dd HH:mm:ss
     * @throws ParseException
     */
    public static String DiferenciaFechas2(String fechaInicial, String fechaFinal) throws ParseException{
        String formato = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(formato);
        Date fecha_ini = df.parse(fechaInicial);
        Date fecha_fini = df.parse(fechaFinal);
        int diff = (int) ((fecha_fini.getTime()-fecha_ini.getTime())/1000);
//        long diff = ((fecha_fini.getTime()-fecha_ini.getTime()));
        int dias = 00;
        int horas = 00;
        int minutos = 00;
        int segundos;
        
        if(diff>=86400){
            dias=(int)Math.floor(diff/86400);
            diff=diff-(dias*86400);
        }
        if(diff>=3600){
            horas=(int)Math.floor(diff/3600);
            diff=diff-(horas*3600);
        }
        if(diff>=60){
            minutos=(int)Math.floor(diff/60);
            diff=diff-(minutos*60);
        }
        segundos = diff;
        if(dias == 0){
            return horas +":" + minutos +":" + segundos +":";
        }else{
            return dias +" "+ horas +":" + minutos +":" + segundos +":";
        }
    }
    /**
     * Recibe dos string de fechas y regresa el numero de dias entre ellos
     * @param fechaInicial con formato dd/MM/yyyy
     * @param fechaFinal con formato dd/MM/yyyy
     * @return int numero de dias
     * @throws ParseException 
     */
    public static int DiferenciaFechasenDias(String fechaInicial, String fechaFinal) throws ParseException{
        String formato = "dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(formato);
        Date fecha_ini = df.parse(fechaInicial);
        Date fecha_fini = df.parse(fechaFinal);
        int diff = (int) ((fecha_fini.getTime()-fecha_ini.getTime())/1000);
//        long diff = ((fecha_fini.getTime()-fecha_ini.getTime()));
        int dias = 00;
        
        if(diff>=86400){
            dias=(int)Math.floor(diff/86400);
        }
        
        return dias;
    }
    /**
     * Recibe la fecha de entrada, el tiempo que fue ocupado y el tipo de habitacion y calcula la tarifa
     * @param fecha_entrada 
     * @param tiempo 
     * @param tipo_habitacion  
     * @return Un int de la tarifa
     * @throws ParseException
     */
    public static int CalcularTarifa(String fecha_entrada,String tiempo ,String tipo_habitacion) throws ParseException{
        int total = 0;
        int separador1;
        int separador2;
        boolean entre_semana = false;
        boolean dormido = false;
        
        //Cambia el String fecha de Entrada a un Date
        String formato = "HH:mm:ss dd/MM/yyyy";
        SimpleDateFormat df = new SimpleDateFormat(formato);
        Date fecha_ini = df.parse(fecha_entrada);
        
        //Halla el dia de semana de la fecha de entrada
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(fecha_ini);
        int dia = calendario.get(Calendar.DAY_OF_WEEK);
        
        //Verifica si es entre semana o fin de semana para saber cual tarifa necesita
        if(1<dia && dia < 7){
            entre_semana = true;
        }
        
        //Busca saber si la fecha de entrada es entre semana y fue entre las 22 y las 06 
        separador1 = fecha_entrada.indexOf(":");
        
        if(entre_semana==true){
            String h1 = fecha_entrada.substring(0, separador1);
            int h2 = Integer.parseInt(h1);
            if(22 <= h2 && h2<= 24 ){
                dormido = true;
            }
            if(0<= h2 && h2 <= 6){
                dormido = true;
            }
        }
        
        //Busca saber cuanto tiempo en minutos se uso la habitacion
        //Aqui se saca las horas usadas de el String tiempo
        separador1 = tiempo.indexOf(":");
        separador2 = tiempo.indexOf(":",separador1+1);
        
        String h = tiempo.substring(0, separador1);
        int horas = 0;
        //Se verifica si la hora no es 0
        if(!"0".equals(h)){
            horas = Integer.parseInt(h);
        }
        
        //Aqui se extrae los minutos del String tiempo
        String m = tiempo.substring(separador1+1, separador2);
        int minutos=0;
        if(!"0".equals(m)){
            minutos = Integer.parseInt(m);
        }
        //Se calcula el tiempo en minutos transcurrido
        minutos = minutos + (horas*60);
        
        //Se busca saber cuanto tiempo adicional(mayor a 90 minutos) fue usado
        int adicional = 0;
        if(minutos>90){
            minutos = minutos - 90;
            adicional = (int)Math.floor(minutos/30);
        }
        
        // Dependiendo del tipo de habitacion y la hora de entrada se devuelve la tarifa
        switch(tipo_habitacion){
            case "Economico":
                if(dormido==true){
                    total = 120000;
                }else{
                    total = 40000 + (adicional*15000);
                }
                break;
            case "Ejecutivo":
                if(dormido==true){
                    total = 120000;
                }else{
                    total = 55000 + (adicional*18000);
                }
                break;
            case "VIP":
                if(dormido==true){
                    total = 120000;
                }else{
                    total = 65000 + (adicional*20000);
                }
                break;
        }
        
        return total;
    }
    /**
     * Recibe un String numeral del dia de semana y regresa el nombre del dia de semana
     * @param dia_semana 
     * numero en string
     * @return Dia de la semana en String
     */
    public static String NumeroToDiaSemana(String dia_semana){
        String dia = "";
        switch(dia_semana){
            case "0":
                dia="Lunes";
                break;
            case "1":
                dia="Martes";
                break;
            case "2":
                dia="Miercoles";
                break;
            case "3":
                dia="Jueves";
                break;
            case "4":
                dia="Viernes";
                break;
            case "5":
                dia="Sabado";
                break;
            case "6":
                dia="Domingo";
                break;
        }
        return dia;
    }
    /**
     * Recibe un int del dia de semana y regresa el nombre del dia de semana
     * @param dia_semana
     * int
     * @return String
     */
    public static String NumeroToDiaSemana2(int dia_semana){
        String dia = "";
        switch(dia_semana){
            case 2:
                dia="Lunes";
                break;
            case 3:
                dia="Martes";
                break;
            case 4:
                dia="Miercoles";
                break;
            case 5:
                dia="Jueves";
                break;
            case 6:
                dia="Viernes";
                break;
            case 7:
                dia="Sabado";
                break;
            case 1:
                dia="Domingo";
                break;
        }
        return dia;
    }
    /**
     * Regresa una lista de los productos que necesitan reponerse
     * @return ArrayList de Inventario
     */
    public static ArrayList<Inventario> ProductosBajo(){
        ControlInventario ci = new ControlInventario();
        Inventario i;
        ArrayList<Inventario> productosbajos;
        
        ArrayList<Inventario> productos = ci.GetAll();
        
        productosbajos = new ArrayList<>();
        for (Inventario p : productos) {
            int cant = p.getCantidad();
            int min = p.getMinimo();
            if (cant <= min) {
                productosbajos.add(p);
            }
        }
        return productosbajos;
        
    }
    
            
    
}
