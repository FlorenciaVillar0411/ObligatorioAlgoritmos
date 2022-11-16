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
        //se crean las listas instanciando la listaOrdenadaImplentada
        this.listaClienteOrdenada = new ListaOrdenadaImplementada<>();
        this.listaClientes = new ListaImplementada<>();
        this.listaCamiones = new ListaImplementada<>();
        this.listaProductos = new ListaImplementada<>();
        this.listaStock = new ListaImplementada<>();
    }

    @Override
    //2.1
    //Crea la estructura necesaria para representar el sistema de distribución.
    public Retorno crearSistemaDeDistribucion(int capacidadCajas) {
        if (capacidadCajas <= 0) {
            return new Retorno(Retorno.Resultado.ERROR_1);
        } else {
            this.capacidadCajas = capacidadCajas;
            return new Retorno(Retorno.Resultado.OK);
        }
    }

    @Override
    //2.2
    //Registra al cliente, siempre y cuando ya no exista en el sistema.
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
    //2.3
    //Elimina el cliente con el rut indicado, siempre y cuando no tenga entregas registradas.
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
    //2.4
    //Crea el camión en el sistema
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
    //2.5
    //Elimina el camión del sistema.
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
    //2.6
    //Se registra el producto en el sistema.
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

    @Override
    //3.1
    //Se realiza el despacho de un determinado producto en la Fábrica.
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
            Stock stockNuevo = new Stock(matriculaCamion, codigoProd, nroCaja, cantUnidades);//se genera un stock nuevo del producto
            this.listaStock.agregarAlFinal(stockNuevo);//se agrega al final de la pila de stocks
            this.cantidadCajas++; // la cantidad de cajas en fabrica aumenta
            Producto p = this.listaProductos.obtener(new Producto(codigoProd));
            p.modificarStock(cantUnidades);//se modifica el stock existente
            revisarOrdenesEnEspera(stockNuevo, p);
            return new Retorno(Retorno.Resultado.OK);
        }
    }
    
    private void revisarOrdenesEnEspera(Stock stock, Producto prod){
        OrdendeEspera orden = this.listaOrdenesDeEspera.obtener(new OrdendeEspera(stock.codigoProd));//la orden de espera de un producto dado
         if (prod.stockDisponible >= orden.cant) { //se controla que la capacidad de stock sea mayor a la cantidad requerida para poder hacer el despacho de la caja
                eliminarStock(prod, orden.cant);//se elimina del stock total la orden
                this.listaRetiros.agregarAlFinal(new Retiro(orden.matriculaCam, orden.rutCliente, orden.codProducto, orden.cant));//se retira de la lista de retiros
                this.listaOrdenesDeEspera.eliminar(orden);// se elimina de la lista de ordenes en espera
                
            } else { //si el stock disponible es menor a la cantidad de la orden
                eliminarStock(prod, prod.stockDisponible);
                int cantidadEspera = orden.cant - prod.stockDisponible;
                this.listaRetiros.agregarAlFinal(new Retiro(orden.matriculaCam, orden.rutCliente, orden.codProducto, prod.stockDisponible));
                this.listaOrdenesDeEspera.agregarAlFinal(new OrdendeEspera(orden.matriculaCam, orden.rutCliente, orden.codProducto, cantidadEspera));
                //se agrega una nueva orden de espera para ese producto y esa cantidad, camion, cliente y esa cantidad faltante
                this.listaOrdenesDeEspera.eliminar(orden);             
            }
    }

    @Override
     //3.2
    //Se realiza un retiro de una cantidad de unidades del producto especificado para el envío a un cliente
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
                eliminarStock(prod, cant); //lo elimina del stock
                this.listaRetiros.agregarAlFinal(new Retiro(matriculaCam, rutCliente, codProducto, cant));
                return new Retorno(Retorno.Resultado.OK);
            } else {
                eliminarStock(prod, prod.stockDisponible); //si el stock disponible es menor que la cantidad que se desea retirar...
                int cantidadEspera = cant - prod.stockDisponible; //se genera una cantidad en espera, que resulta de hacer el stock disponible - la cantidad retirada
                this.listaRetiros.agregarAlFinal(new Retiro(matriculaCam, rutCliente, codProducto, prod.stockDisponible));
                this.listaOrdenesDeEspera.agregarAlFinal(new OrdendeEspera(matriculaCam, rutCliente, codProducto, cantidadEspera)); 
                //se agrega en la cola de ordenes en espera para poder cumplir con la solicityd cuando haya stock suficiente
                return new Retorno(Retorno.Resultado.OK);               
            }
        }
    }
//se elimina del stock si la cantidad del producto es mayor a 0
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

    @Override
    //4.1
    // Se listan los camiones registrados en el sistema, mostrando todos sus datos
    public Retorno listarCamiones() {
        this.listaCamiones.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.2
    //Se muestran todos los clientes ordenados en forma alfabétic
    public Retorno listarClientesOrdenado() {
        //ORDENAR CON SORT
        this.listaClientes.sort();
        this.listaClientes.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.3
    //Lista todos los productos con su stock disponible
    public Retorno listarProductos() {
        this.listaProductos.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.4
    //Muestra el último producto registrado
    public Retorno ultimoProductoRegistrado() {
        this.listaProductos.obtenerFinal();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.5
    //Se lista el detalle de cada envío realizado para el producto dado (camión que lo realizó, cliente y cant. de unidades)
    public Retorno listarEnvíosDeProducto(int codProd) {
        this.listaRetiros.mostrarFiltrado(new Retiro(codProd));
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.6
    //Lista las órdenes pendientes de un producto dado, ordenadas por orden de prioridad 
    public Retorno listarOrdenesPendientes(int codProd) {
        this.listaOrdenesDeEspera.mostrar();
        return new Retorno(Retorno.Resultado.OK);
    }

    @Override
    //4.7
    //Se muestra una matriz donde, para cada producto (filas) se muestran la cantidad de unidades enviadas para cada cliente (columnas).
    public int[][] reporteDeEnviosDeProductos() {
        int cantProductos = this.listaProductos.cantidadElementos;
        int cantPersonas = this.listaClientes.cantidadElementos;

        int[][] matriz = new int[cantProductos][cantPersonas];

        Producto producto = this.listaProductos.obtenerPrincipio();
        Cliente cliente = this.listaClientes.obtenerPrincipio();

        for(int prod=0; prod <= cantProductos; prod++)
		{
			for(int pers=0; pers<= cantPersonas; pers++)
			{
                int prodPer = 0;
                Orden orden = this.listaRetiros.obtenerPrincipio();
                for(int i = 0; i >= this.listaRetiros.cantidadElementos; i++){
                    if(orden.rutCliente == cliente.rut && orden.codigoProd == cliente.codigo){
                        prodPer+= orden.cant;
                    }
                    orden = this.listaRetiros.obtenerSiguiente(orden);
                }

				matriz[prod][pers] = prodPer;				            							
                cliente = this.listaClientes.obtenerSiguiente(cliente);
			}
			producto = this.listaProductos.obtenerSiguiente(producto);
		}

        return matriz;
    }

}
