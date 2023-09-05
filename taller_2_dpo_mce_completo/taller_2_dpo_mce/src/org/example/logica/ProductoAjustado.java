package org.example.logica;

import java.util.ArrayList;

public class ProductoAjustado implements Producto {

    private ArrayList<Ingrediente> ingredientes ;
    private ProductoMenu productoMenu;

    public ProductoAjustado(ProductoMenu base){
        this.productoMenu = base;
        ingredientes = new ArrayList<>();
    }

    public ArrayList<Ingrediente> getIngredientes(){
        return  ingredientes;
    }

    public void agregarIngrediente(Ingrediente ingrediente){
        ingredientes.add(ingrediente);
    }
    public void eliminarIngrediente(Ingrediente ingrediente){
        ingredientes.remove(ingrediente);
    }

    @Override
    public int getPrecio() {
        /*int suma = 0;
        for (int i = 0; i < ingredientes.size(); i++) {
            suma += ingredientes.get(i).getCostoAdicional();
        }

        return suma;*/
        return productoMenu.getPrecio();
    }

    @Override
    public String getNombre() {
        return productoMenu.getNombre();
    }

    @Override
    public String generarTextoFactura() {
        return null;
    }
}
