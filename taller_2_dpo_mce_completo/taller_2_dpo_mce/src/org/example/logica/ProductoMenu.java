package org.example.logica;

public class ProductoMenu implements Producto  {
    private String nombre;
    private int precioBase;

    public ProductoMenu(String nombre, int precio){
        this.nombre = nombre;
        this.precioBase = precio;
    }


    @Override
    public int getPrecio() {
        return precioBase;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public String generarTextoFactura() {
        return null;
    }
}
