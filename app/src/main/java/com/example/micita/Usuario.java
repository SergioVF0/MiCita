package com.example.micita;
//clase usuario
public class Usuario {

    private int id;

    private String dni;

    private String nombre;

    private String apellido1;

    private String apellido2;

    private String contrasena;

    private String centro;



    public Usuario(){}

    public Usuario(int id,String dni, String nombre, String apellido1, String apellido2, String contrasena, String centro) {
        this.id=id;
        this.dni = dni;
        this.nombre = nombre;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.contrasena = contrasena;
        this.centro= centro;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contraseña) {
        this.contrasena = contraseña;
    }

    public String getCentro() {
        return centro;
    }

    public void setCentro(String centro) {
        this.centro = centro;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido1='" + apellido1 + '\'' +
                ", apellido2='" + apellido2 + '\'' +
                ", contrasena='" + contrasena + '\'' +
                ", centro='" + centro + '\'' +
                '}';
    }
}
