package org.example.logica;

public class Ingrediente {
    private String nombre;
    private double costoAdicional;

    public Ingrediente(String nombre, double costoAdicional){
        this.nombre = nombre;
        this.costoAdicional = costoAdicional;
    }

    public String getNombre(){
        return nombre;
    }

    public double getCostoAdicional() {
        return costoAdicional;
    }


}
