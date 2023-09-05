package org.example.logica;

import java.util.ArrayList;

public class Combo implements Producto{
    private double descuento;
    private String nombreCombo;
    private ArrayList<Producto> listaProductos;

    public Combo(String nombre, double descuento){
        this.nombreCombo = nombre;
        this.descuento = descuento;
        listaProductos = new ArrayList<>();
    }

    public void agregarItemACombo(Producto itemCombo){
        listaProductos.add(itemCombo);
    }

    public ArrayList<Producto> getListaProductos(){
        return listaProductos;
    }

    public double getDescuento(){
        return  descuento;
    }

    @Override
    public int getPrecio() {
        int suma = 0;
        for (int i = 0; i < listaProductos.size(); i++) {
            suma += listaProductos.get(i).getPrecio();
        }
        int total = (int) (suma - (suma*descuento/100));

        return total;
    }

    @Override
    public String getNombre() {
        return nombreCombo;
    }

    @Override
    public String generarTextoFactura() {
        return null;
    }
}
