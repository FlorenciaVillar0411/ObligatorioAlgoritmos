package sistemaDistribucion;

import dominio.*;
import tads.*;

public class Sistema implements IObligatorio {

    private int capacidadCajas;
    private int cantidadCajas;
    private ListaOrdenada<Cliente> listaClienteOrdenada;
    private Lista<Cliente> listaClientes;
    private Lista<Camion> listaCamiones;
    private Lista<Producto> listaProductos;
    private Lista<Stock> listaStock;
    private Lista<Retiro> listaRetiros;
    private Lista<OrdendeEspera> listaOrdenesDeEspera;

    public Sistema() {
        this.listaClienteOrdenada = new ListaOrdenadaImplementada<>();
        this.listaClientes = new ListaImplementada<>();
        this.listaCamiones = new ListaImplementada<>();
        this.listaProductos = new ListaImplementada<>();
        this.listaStock = new ListaImplementada<>();
    }

    @Override
    //pre:      post:
    public Retorno crearSistemaDeDistribucion(int capacidadCajas) {
        if (capacidadCajas <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else {
            this.capacidadCajas = capacidadCajas;
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno agregarCliente(String nombre, String rut, int tel, String direccion) {

        if (listaClientes.existe(new Cliente(rut))) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else {
            Cliente clienteNuevo = new Cliente(nombre, rut, tel, direccion);
            listaClientes.agregarAlFinal(clienteNuevo);
            listaClienteOrdenada.agregarOrdenado(clienteNuevo);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno eliminarCliente(String rut) {
        if (!this.listaClientes.existe(new Cliente(rut))) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if (this.listaRetiros.existe(new Retiro(rut, true))) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            this.listaClientes.eliminar(new Cliente(rut));
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno agregarCamion(String matricula, int toneladasMaxSoportadas) {
        if (this.listaCamiones.existe(new Camion(matricula))) {
            return new Retorno(Retorno.Resultado.ERROR_1);

        } else if (toneladasMaxSoportadas <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            Camion camionNuevo = new Camion(matricula, toneladasMaxSoportadas);
            this.listaCamiones.agregarAlFinal(camionNuevo);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno eliminarCamion(String matricula) {
        if (!this.listaCamiones.existe(new Camion(matricula))) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if (this.listaRetiros.existe(new Retiro(matricula, false))) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            this.listaCamiones.eliminar(new Camion(matricula));
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    public Retorno registrarProducto(String nombre, String descripcion) {
        if (this.listaProductos.existe(new Producto(nombre))) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else if (descripcion == null) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else {
            Producto productoRegistrado = new Producto(nombre, descripcion);
            this.listaProductos.agregarAlFinal(productoRegistrado);
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    //3.1
    @Override
    public Retorno altaDeStockDeProducto(String matriculaCamion, int codigoProd, int nroCaja, int cantUnidades) {
        if (!this.listaCamiones.existe(new Camion(matriculaCamion))) {
            return new Retorno(Retorno.Resultado.ERROR_1);

        } else if (!this.listaProductos.existe(new Producto(codigoProd))) {
            return new Retorno(Retorno.Resultado.ERROR_2);

        } else if (cantUnidades <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_3);

        } else if (this.listaStock.existe(new Stock(nroCaja))) {
            return new Retorno(Retorno.Resultado.ERROR_4);

        } else if (this.capacidadCajas < this.cantidadCajas) {
            return new Retorno(Retorno.Resultado.ERROR_5);
        } else {
            Stock stockNuevo = new Stock(matriculaCamion, codigoProd, nroCaja, cantUnidades);
            this.listaStock.agregarAlFinal(stockNuevo);
            this.cantidadCajas++;
            Producto p = this.listaProductos.obtener(new Producto(codigoProd));
            p.modificarStock(cantUnidades);
            
            revisarOrdenes(stockNuevo, p);
            
            return new Retorno(Retorno.Resultado.OK);
        }
    }
    
    private void revisarOrdenes(Stock stock, Producto prod){
        OrdendeEspera orden = this.listaOrdenesDeEspera.obtener(new OrdendeEspera(stock.codigoProd));
        
         if (prod.stockDisponible >= orden.cant) { //elimina stock si hay disponible
                eliminarStock(prod, orden.cant);
                this.listaRetiros.agregarAlFinal(new Retiro(orden.matriculaCam, orden.rutCliente, orden.codProducto, orden.cant));
                this.listaOrdenesDeEspera.eliminar(orden);
            } else {
                eliminarStock(prod, prod.stockDisponible);
                int cantidadEspera = orden.cant - prod.stockDisponible;
                this.listaRetiros.agregarAlFinal(new Retiro(orden.matriculaCam, orden.rutCliente, orden.codProducto, prod.stockDisponible));
                this.listaOrdenesDeEspera.agregarAlFinal(new OrdendeEspera(orden.matriculaCam, orden.rutCliente, orden.codProducto, cantidadEspera));
                this.listaOrdenesDeEspera.eliminar(orden);             
            }
    }

    //3.2
    @Override
    public Retorno retiroDeProducto(String matriculaCam, String rutCliente, int codProducto, int cant) {
        Producto prod = this.listaProductos.obtener(new Producto(codProducto));
        
        if (!this.listaCamiones.existe(new Camion(matriculaCam))) {
            return new Retorno(Retorno.Resultado.ERROR_1);

        } else if (!this.listaClientes.existe(new Cliente(rutCliente))) {
            return new Retorno(Retorno.Resultado.ERROR_2);
        } else if (prod == null) {
            return new Retorno(Retorno.Resultado.ERROR_3);
        } else {
            if (prod.stockDisponible >= cant) { //elimina stock si hay disponible
                eliminarStock(prod, cant);
                this.listaRetiros.agregarAlFinal(new Retiro(matriculaCam, rutCliente, codProducto, cant));
                return new Retorno(Retorno.Resultado.OK);
            } else {
                eliminarStock(prod, prod.stockDisponible);
                int cantidadEspera = cant - prod.stockDisponible;
                this.listaRetiros.agregarAlFinal(new Retiro(matriculaCam, rutCliente, codProducto, prod.stockDisponible));
                this.listaOrdenesDeEspera.agregarAlFinal(new OrdendeEspera(matriculaCam, rutCliente, codProducto, cantidadEspera));
                return new Retorno(Retorno.Resultado.OK);               
            }
        }
    }

    private void eliminarStock(Producto prod, int cdad) {
        int cantidad = cdad;
        while (cantidad > 0) {
            Stock stock = this.listaStock.obtener(new Stock(prod.codigo));
            if (stock.cantUnidades < cantidad) {
                stock.eliminarUnidades(cantidad);
                prod.modificarStock(-cantidad);
                cantidad = 0;
            } else if (stock.cantUnidades >= cantidad) {               
                prod.modificarStock(-stock.cantUnidades);
                cantidad -= stock.cantUnidades;
                this.listaStock.eliminar(stock);
                this.cantidadCajas --;
            }
        }
    }

    //4.1
    @Override
    public Retorno listarCamiones() {
        this.listaCamiones.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    //4.2
    @Override
    public Retorno listarClientesOrdenado() {
        //ORDENAR CON SORT
        this.listaClientes.sort();
        this.listaClientes.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    public Retorno listarProductos() {
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
        this.listaRetiros.mostrarFiltrado(new Retiro(codProd));
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
