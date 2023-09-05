package org.example.console;

import org.example.logica.*;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;


public class Main {
 //mapa de las opciones que va a ofrecer su aplicación
    Restaurante restaurante;
//. el combo especial incluye una hamburguesa sencilla y unas papas medianas y tiene un descuento
//del 7% sobre el precio de los productos por separado)
    Scanner scan = new Scanner(System.in).useDelimiter("\n");
    Map<String, Producto> mapProductosBases;
    Map<String, Ingrediente> mapIngredientes;
    Map<String, Combo> mapCombos;
    public void mostrarMenu(){
        String menuPrincipal =
        "1. Mostrar el menú \n"+
        "2. Iniciar un nuevo pedido\n"+
        "3. Agregar un elemento a un pedido\n"+
        "4. Cerrar un pedido y guardar la factura\n"+
        "5. Consultar la información de un pedido dado su id\n"+
                "6. Salir\n";

        System.out.println(menuPrincipal);
    }


    public  void cargarHashMap(){
       mapProductosBases = new LinkedHashMap<String, Producto>();
       mapIngredientes = new LinkedHashMap<String, Ingrediente>();
       mapCombos = new LinkedHashMap<String, Combo>();

        int keyEnProductosBase=1;
        int keyEnIngredientes=1;
        int keyEnCombo=1;
        for (int i=0; i<restaurante.getMenuBase().size(); i++) {
            mapProductosBases.put(keyEnProductosBase+"A",restaurante.getMenuBase().get(i));
            keyEnProductosBase++;
        }

        for (int i=0; i<restaurante.getIngrediente().size(); i++) {
            mapIngredientes.put(keyEnIngredientes+"B",restaurante.getIngrediente().get(i));
            keyEnIngredientes++;
        }


        for (int i=0; i<restaurante.getCombos().size(); i++) {
            Combo combo = restaurante.getCombos().get(i);
            mapCombos.put(keyEnCombo+"C",combo);


            keyEnCombo++;
        }



    }

    public void ejecutarOpcion(int opcion_seleccionada){
        try
        {
            if (opcion_seleccionada == 1) {
                System.out.println("================ MENU ====================");

                for (String key : mapProductosBases.keySet())
                    System.out.println(key  + ": " + mapProductosBases.get(key).getNombre()+ " Costo:" +  mapProductosBases.get(key).getPrecio());


                System.out.println("============ INGREDIENTES =============");

                for (String key : mapIngredientes.keySet())
                    System.out.println(key  + ": " + mapIngredientes.get(key).getNombre()+ " Costo:" +  mapIngredientes.get(key).getCostoAdicional());

                System.out.println("============ COMBOS =============");
                for (String key : mapCombos.keySet()){
                    System.out.println(key  + ": " + mapCombos.get(key).getNombre()+ " Descuento:"+mapCombos.get(key).getDescuento());
                    for (int j = 0; j < mapCombos.get(key).getListaProductos().size(); j++) {
                        System.out.println("    +"+mapCombos.get(key).getListaProductos().get(j).getNombre());
                    }
                }

            }else if (opcion_seleccionada == 2 ) {
                System.out.print("Nombre del cliente:");
                String nombre = "";
                nombre =scan.next();
                System.out.print("Direccion del cliente:");
                String direccion = scan.next();



                //Ingrediente i = mapIngredientes.get("3B");


                restaurante.iniciarPedido(nombre, direccion);

            }
            else if (opcion_seleccionada == 3 ) {

                if(restaurante.getPedidoEnCurso() != null) {
                    System.out.print("Digite el codigo del producto (ejemplo 1A):");
                    String codigo = scan.next();
                    Producto elemento = null;
                    if (codigo.contains("A")) {
                        elemento = mapProductosBases.get(codigo);
                        if (elemento != null) {
                            int op = 1;
                            ProductoMenu pm = (ProductoMenu) elemento; //***

                            //validaci[on
                            ProductoAjustado pa = new ProductoAjustado(pm);
                            do {
                                System.out.println("Opciones para ingredientes");
                                System.out.println("1. Agregar");
                                System.out.println("2. Eliminar");
                                System.out.println("3. Ninguno");
                                System.out.print("Seleccione una opción:");
                                op = scan.nextInt();
                                if (op == 3) {
                                    return;
                                }

                                if (op == 1) {
                                    System.out.print("Digite el codigo del ingrediente a agregar (ejemplo 1B):");
                                    String codigoIngrediente = scan.next();
                                    Ingrediente ingrediente = mapIngredientes.get(codigoIngrediente);
                                    //validaci[on
                                    pa.agregarIngrediente(ingrediente);

                                } else if (op == 2) {
                                    System.out.print("Digite el codigo del ingrediente a eliminar (ejemplo 1B):");
                                    String codigoIngrediente = scan.next();
                                    Ingrediente ingrediente = mapIngredientes.get(codigoIngrediente);
                                    pa.eliminarIngrediente(ingrediente);

                                }

                                restaurante.getPedidoEnCurso().agregarProducto(pa);

                            } while (op != 3);
                        }else{
                            System.out.println("No existe este producto");
                        }
                    }
                    else if (codigo.contains("C")) {
                        elemento = mapCombos.get(codigo);
                        if (elemento != null) {
                            restaurante.getPedidoEnCurso().agregarProducto(elemento);
                        }else{
                            System.out.println("No existe este producto");
                        }
                    }
                }else{
                    System.out.println("No hay un pedido en curso");
                }
            }
            else if (opcion_seleccionada == 4 ) {
                if (restaurante.getPedidoEnCurso() != null) {
                    if (restaurante.getPedidoEnCurso().getListaProductos().size() > 0) {
                        restaurante.cerrarYGuardarPedido();
                    } else {
                        System.out.println("No hay productos agregados al pedido");
                    }
                } else {
                    System.out.println("No hay un pedido en curso");
                }
            }
            else if (opcion_seleccionada == 5 ) {
                System.out.print("Ingrese el id del pedido para ver su factura:");
                int id = scan.nextInt();
                System.out.println(restaurante.consultaInformacionPedido(id));
            }
            else if (opcion_seleccionada == 6 ) {
                System.out.println("Saliendo de la aplicación ...");
            }

            System.out.println("------------------------------------");
        }
        catch (NumberFormatException e)
        {
            System.out.println("Debe seleccionar uno de los números de las opciones.");
        }





    }
    public static void main(String[] args) {
        Main main = new Main();
        main.restaurante = new Restaurante();
        Path pathIngredientes = Paths.get("data/ingredientes.txt");
        Path pathMenu= Paths.get("data/menu.txt");
        Path pathCombo= Paths.get("data/combos.txt");
        File fileIngredientes = new File(pathIngredientes .toUri());
        File fileMenu= new File(pathMenu .toUri());
        File fileCombo= new File(pathCombo .toUri());
        main.restaurante.cargarInformacionRestaurante(fileIngredientes, fileMenu, fileCombo);

        main.cargarHashMap();
        int opcion = -1;

        do{
            main.mostrarMenu();
            System.out.println("Seleccione una opción:");
            opcion = main.scan.nextInt();

            main.ejecutarOpcion(opcion);
        }while(opcion!=6);

    }
}