package org.example.logica;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class Pedido implements Producto {
    private int numeroPedidos;
    private int idPedido;
    private String nombreCliente;
    private String direccionCliente;

    private ArrayList<Producto> listaProductos;

    public Pedido(String nombreCliente, String direccionCliente){
        this.nombreCliente = nombreCliente;
        this.direccionCliente = direccionCliente;
        listaProductos = new ArrayList<>();
    }

    public void setIdPedido(int id){
        this.idPedido = id;
    }
    public int getIdPedido(){
        return idPedido;
    }

    public void agregarProducto(Producto nuevoProducto){

        listaProductos.add(nuevoProducto);

    }

    public ArrayList<Producto> getListaProductos() {
        return listaProductos;
    }

    private double getPrecioNetoPedido(){
        int suma = 0;
        double costoAdicional = 0;
        /*for (int i=0; i<listaProductos.size(); i++) {
            suma += listaProductos.get(i).getPrecio();
        }*/
        for (int i=0; i<listaProductos.size(); i++) {
            if (listaProductos.get(i) instanceof ProductoAjustado) {
                suma += listaProductos.get(i).getPrecio();
                ArrayList<Ingrediente> ingredientes = ((ProductoAjustado) listaProductos.get(i)).getIngredientes();
                for (int j = 0; j < ingredientes.size(); j++) {
                    costoAdicional += ingredientes.get(j).getCostoAdicional();
                }
            }else {
                suma += listaProductos.get(i).getPrecio();
            }
        }

        return suma+costoAdicional;
    }

    private double getPrecioTotalPedido(){
        return getPrecioNetoPedido()+getPrecioIVAPedido();
    }

    private double getPrecioIVAPedido(){

        return getPrecioNetoPedido()*0.19;
    }


    @Override
    public int getPrecio() {
        return 0;
    }

    @Override
    public String getNombre() {
        return null;
    }

    @Override
    public String generarTextoFactura() {
        //Las facturas de los pedidos deben discriminar el valor de cada elemento, el valor neto total, el IVA (19%) y el valor
        //total (neto + IVA).
        String text = "";

        for (int i = 0; i < listaProductos.size(); i++) {

            if (listaProductos.get(i) instanceof ProductoAjustado) {
                text += listaProductos.get(i).getNombre() + " $" + listaProductos.get(i).getPrecio() + "\n";

                ArrayList<Ingrediente> ingredientes = ((ProductoAjustado) listaProductos.get(i)).getIngredientes();
                for (int j = 0; j < ingredientes.size(); j++) {
                    text += "****" + ingredientes.get(j).getNombre() + " $" + ingredientes.get(j).getCostoAdicional() + "\n";

                }
                text += "------------------------------------\n";
            } else {
                text += listaProductos.get(i).getNombre() + " $" + listaProductos.get(i).getPrecio() + "\n";
            }


        }

        text += "\nValor neto total: " + getPrecioNetoPedido();
        text += "\nIVA(19%): " + getPrecioIVAPedido();
        text += "\nTotal (neto + IVA): " + getPrecioTotalPedido();
        return text;
    }

    public void guardarFactura(File archivo){
        //Charset charset = Charset.forName("US-ASCII");
        String s = generarTextoFactura();

        try (BufferedWriter writer = Files.newBufferedWriter(archivo.toPath())) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }
}
