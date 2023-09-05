package org.example.logica;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Random;

public class Restaurante {

    private ArrayList<Ingrediente> listaIngredientes;
    private ArrayList<Producto> listaMenu;
    private ArrayList<Producto> listaBebidas;
    private ArrayList<Combo> listaCombo;
    private Pedido  pedido;
    public Restaurante(){}

    public void iniciarPedido(String nombreCliente, String direccionCliente){
        pedido = new Pedido(nombreCliente, direccionCliente);
        Random random = new Random();
        int id = random.nextInt(150);
        pedido.setIdPedido(id+1);

    }

    public void cerrarYGuardarPedido(){

        String nombreFactura = "factura"+pedido.getIdPedido()+".txt";
        Path pathFactura= Paths.get("facturas/"+nombreFactura);
        File fileFactura = new File(pathFactura .toUri());
        pedido.guardarFactura(fileFactura);
        pedido = null; ///**
    }

    public Pedido getPedidoEnCurso(){
        return pedido;
    }

    public ArrayList<Producto> getMenuBase(){
        return listaMenu;
    }

    public ArrayList<Producto> getBebidas(){
        return listaBebidas;
    }

    public ArrayList<Combo> getCombos(){
        return listaCombo;
    }

    public ArrayList<Ingrediente> getIngrediente(){
        return listaIngredientes;
    }

    public void cargarInformacionRestaurante(File archivoIngredientes, File archivoMenu, File archivoCombos, File archivoBebidas){
        cargarIngredientes(archivoIngredientes);
        cargarMenu(archivoMenu);
        cargarCombos(archivoCombos);
        cargarBebidas(archivoBebidas);
    }

    private void cargarIngredientes(File archivoIngredientes){

        ArrayList<Ingrediente> ingredientes = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(archivoIngredientes.toPath())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String nombre = line.split(";")[0];
                String precio = line.split(";")[1];
                ingredientes.add(new Ingrediente(nombre, Integer.parseInt(precio)));

            }

            listaIngredientes = ingredientes;
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }


    private void cargarMenu(File archivoMenu){
        ArrayList<Producto> menus = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(archivoMenu.toPath())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String nombre = line.split(";")[0];
                String precio = line.split(";")[1];

                Producto producto = new ProductoMenu(nombre, Integer.parseInt(precio));
                menus.add(producto);

            }

            listaMenu = menus;
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private void cargarBebidas(File archivoBebidas){
        ArrayList<Producto> bebidas = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(archivoBebidas.toPath())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String nombre = line.split(";")[0];
                String precio = line.split(";")[1];

                Producto producto = new ProductoMenu(nombre, Integer.parseInt(precio));
                bebidas.add(producto);

            }

            listaBebidas = bebidas;


        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }


    private void cargarCombos(File archivoCombos){
        ArrayList<Combo> combos = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(archivoCombos.toPath())) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                //System.out.println(line);
                String nombre = line.split(";")[0];
                String descuento = line.split(";")[1];
                descuento = descuento.substring(0,descuento.length()-1);
                String nombreProducto1 = line.split(";")[2];
                String nombreProducto2 = line.split(";")[3];
                String nombreProducto3 = line.split(";")[4];
                Producto producto1 = null;
                Producto producto2 = null;
                Producto producto3 = null;
                for (int i=0; i<listaMenu.size(); i++) {
                    if (nombreProducto1.equals(listaMenu.get(i).getNombre())) {
                        producto1 = listaMenu.get(i);
                    }
                    if (nombreProducto2.equals(listaMenu.get(i).getNombre())) {
                        producto2 = listaMenu.get(i);
                    }
                    if (nombreProducto3.equals(listaMenu.get(i).getNombre())) {
                        producto3 = listaMenu.get(i);
                    }

                }

                Combo combo = new Combo(nombre, Integer.parseInt(descuento));

                combo.agregarItemACombo(producto1);
                combo.agregarItemACombo(producto2);
                combo.agregarItemACombo(producto3);

                combos.add(combo);

            }
            listaCombo = combos;

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }

    }

    public String consultaInformacionPedido(int id){

        String nombreFactura = "factura"+id+".txt";
        Path pathFactura= Paths.get("facturas/"+nombreFactura);
        File fileFactura = new File(pathFactura .toUri());
        String result = "";
        if (fileFactura.exists()){

            try (BufferedReader reader = Files.newBufferedReader(fileFactura.toPath())) {
                String line = "";

                while ((line = reader.readLine()) != null) {
                    //System.out.println(line);
                    result += line+"\n";

                }


            } catch (IOException x) {

                System.err.format("IOException: %s%n", x);
            }
        }else{
            result = "No se encontro la factura con id "+id;
        }
        return result;
    }

   public void verificarPedidosIdenticos(){
       Path dir = Paths.get("facturas");
       String miPedido = pedido.generarTextoFactura();

       try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
           int cont = 0;
           for (Path file: stream) {
               //System.out.println(file.getFileName());
               try (BufferedReader reader = Files.newBufferedReader(file.toAbsolutePath())) {
                   String line = "";


                       //System.out.println(line);
                   String array[] = miPedido.split("\n");
                   while ((line = reader.readLine()) != null) {
                       for (int i = 0; i < array.length-3 ; i++) {
                           //System.out.println("line"+array[i]+"///"+line);

                           if(array[i].equals(line)){

                               cont=cont+1;
                           }
                       }

                   }

                   if (cont == array.length-3){
                       System.out.println("Hay una coincidencia con un pedido con id"+file.getFileName());
                       return;
                   }

                   cont = 0;


               } catch (IOException x) {

                   System.err.format("IOException: %s%n", x);
               }
           }
       } catch (IOException | DirectoryIteratorException x) {
           // IOException can never be thrown by the iteration.
           // In this snippet, it can only be thrown by newDirectoryStream.
           System.err.println(x);
       }
   }
}
