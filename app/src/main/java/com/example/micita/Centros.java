package com.example.micita;

public enum Centros {

//Centros Hospitalarios
    COMPLEJO_HOSPITALARIO_UNIVERSITARIO_DE_OURENSE(1, "Complejo Hospitalario Universitario de Ourense"),
    HOSPITAL_ALVARO_CUNQUEIRO(2, "Hospital Álvaro Cunqueiro"),
    HOSPITAL_UNIVERSITARIO_DE_A_CORUÑA(3, "Hospital Universitario de A Coruña"),
    HOSPITAL_LUCUS_AUGUSTI(4, "Hospital Lucus Augusti");

    private String nombreCentro;
    private int localizacion;

    private Centros() {

    }


    private Centros(int localizacion, String nombreCentro) {
        this.nombreCentro = nombreCentro;
        this.localizacion = localizacion;
    }

    public String getName() {
        return this.nombreCentro;
    }

    public int getLocalizacion() {
        return this.localizacion;
    }

    public static String getCentro(int localizacion){

        switch (localizacion){
            case 0 : return COMPLEJO_HOSPITALARIO_UNIVERSITARIO_DE_OURENSE.getName();

            case 1 : return HOSPITAL_ALVARO_CUNQUEIRO.getName();

            case 2 : return HOSPITAL_UNIVERSITARIO_DE_A_CORUÑA.getName();

            case 3 : return HOSPITAL_LUCUS_AUGUSTI.getName();

            default: return COMPLEJO_HOSPITALARIO_UNIVERSITARIO_DE_OURENSE.getName();
        }

    }

}
