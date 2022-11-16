
package sistemaDistribucion;

import dominio.*;
import tads.*;


public class Sistema implements IObligatorio {
    
    private int capacidadCajas;
    private int cantidadCajas;
    private Lista<Cliente> listaClientes;
    private Lista<Camion> listaCamiones;
    private Lista<Producto> listaProductos;
    private Lista<Stock> listaStock;
    private Lista<Retiro> listaRetiros;
    private Lista<OrdendeEspera> listaOrdenesDeEspera;

    public Sistema(){
        this.listaClientes = new ListaImplementada<>();
        this.listaCamiones = new ListaImplementada<>();
        this.listaProductos = new ListaImplementada<>();
        this.listaStock = new ListaImplementada<>();
    }
    
    @Override
    //pre:      post:
    public Retorno crearSistemaDeDistribucion(int capacidadCajas){
        if(capacidadCajas <= 0){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        else{
            this.capacidadCajas = capacidadCajas;
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno agregarCliente(String nombre, String rut, int tel, String direccion) {
        
        if(listaClientes.existe(rut)){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }
        else{
            Cliente clienteNuevo = new Cliente(nombre, rut, tel, direccion);
            listaClientes.agregarAlFinal(clienteNuevo);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno eliminarCliente(String rut) {
        if (!this.listaClientes.existe(rut)){
            return new Retorno(Retorno.Resultado.ERROR_1);
        }else if(tieneEntregas(rut)){
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            this.listaClientes.eliminar();
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    private boolean tieneEntregas(String rut) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }


    @Override
    public Retorno agregarCamion(String matricula, int toneladasMaxSoportadas) {
        
        if (this.listaCamiones.existe(matricula)){ 
            return new Retorno(Retorno.Resultado.ERROR_1);
        }else if( toneladasMaxSoportadas <= 0) {
              return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            Camion camionNuevo = new Camion(matricula,toneladasMaxSoportadas);
            this.listaCamiones.agregarAlFinal(camionNuevo);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno eliminarCamion(String matricula) {
        if (!this.listaCamiones.existe(matricula)){ 
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if(tieneEntregas(matricula)){
             return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            this.listaCamiones.eliminar(matricula);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno registrarProducto(String nombre, String descripcion) {
        if (this.listaProductos.existe(nombre)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if(descripcion == null ){
             return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            Producto productoRegistrado = new Producto(nombre,descripcion);
            this.listaProductos.agregarAlFinal(productoRegistrado);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno altaDeStockDeProducto(String matriculaCamion, int codigoProd, int nroCaja, int cantUnidades) {
        if (!this.listaCamiones.existe(matriculaCamion)) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        }else if( !this.listaProductos.existe(codigoProd)){
             return new Retorno(Retorno.Resultado.ERROR_2);
        } else if (cantUnidades <= 0){
             return new Retorno(Retorno.Resultado.ERROR_3);
        } else if ( this.listaStock.existe(nroCaja)){
              return new Retorno(Retorno.Resultado.ERROR_4);
        } else if ( this.capacidadCajas == this.cantidadCajas) {
              return new Retorno(Retorno.Resultado.ERROR_5);
        } else {
            Stock stockNuevo = new Stock(matriculaCamion, codigoProd, nroCaja, cantUnidades);
            this.listaStock.agregarAlFinal(stockNuevo);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno retiroDeProducto(int matriculaCam, int rutCliente, int codProducto, int cant) {
        if (!this.listaCamiones.existe(matriculaCam)) { 
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if (!this.listaClientes.existe(rutCliente)){
                 return new Retorno(Retorno.Resultado.ERROR_2);
        } else if(!this.listaProductos.existe(codProducto)){
             return new Retorno(Retorno.Resultado.ERROR_3);
        } else {
            Stock stockRestante = this.listaStock.eliminar() ;
            return new Retorno(Retorno.Resultado.OK);
            //HACER REGISTRO DE RETIRO
            //RESOLVER STOCK
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno listarCamiones() {
        this.listaCamiones.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno listarClientesOrdenado() {
        //ORDENAR CON SORT
        this.listaClientes.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno listarProductos() {
        //OBTENER STOCK DE CADA PRODUCTO
        this.listaProductos.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno ultimoProductoRegistrado() {
        this.listaProductos.obtenerFinal();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno listarEnvíosDeProducto(int codProd) {
        this.listaRetiros.mostrarPorProducto(codProd);
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno listarOrdenesPendientes(int codProd) {
        this.listaOrdenesDeEspera.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno reporteDeEnviosDeProductos() {
        return new Retorno(Retorno.Resultado.NO_IMPLEMENTADA);
    }
       
}
