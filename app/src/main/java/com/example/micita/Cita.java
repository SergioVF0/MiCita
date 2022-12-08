package com.example.micita;

import java.text.SimpleDateFormat;
import java.util.Date;
    //Clase cita con constructor, atributos, getters y setters
public class Cita {

    private static  int id;
    private static  String dni;
    private static String fecha;

    public Cita(){}

    public Cita(   String dni, String fecha){
        this.dni=dni;
        this.fecha=fecha;
    }

    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getFecha() {

        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    //Para devolver la cita en formato de String
    @Override
    public String toString() {
        return "Cita{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
