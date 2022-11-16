package sistemaDistribucion;

public interface IObligatorio {
 
//2.1    
    //pre: capacidadCajas  != 0 &&  capacidadCajas > 0   
    //post: Retorna ERROR_1 si la capacidadCajas es menor o igual a cero, retorna OK si se pudo inicializar el sistema correctamente
    public Retorno crearSistemaDeDistribucion(int capacidadCajas);
    
//2.2    
     //pre: nombre != null &&  rut != null && tel != 0  && direccion != null 
    //post: Retorna OK si se logra registrar el cliente, ERROR_1 si ya existe un cliente con el mismo rut
    public Retorno agregarCliente(String nombre, String rut,int tel, String direccion);

//2.3    
     //pre: rut != null
    //post: Retorna OK si se pudo eliminar el cliente, ERROR_1 si existe un cliente con el mismo rut que otro, ERROR_2 si tiene entregas registradas
    public Retorno eliminarCliente(String rut);

//2.4    
     //pre: matricula != null &&  toneladasMaxSoportadas != 0 && toneladasMaxSoportadas > 0
    //post: Retorna OK si se pudo agregar el camión, ERROR_1 si se repite matricula, ERROR_2 si las toneladasMaxSoportadas es menor o igual a 0 
    public Retorno agregarCamion(String matricula, int toneladasMaxSoportadas); 

//2.5    
     //pre: matricula != null    
    //post: Retorna OK si se pudo eliminar el camión, ERROR_1 si no existe la matricula, ERROR_2 si el camión tiene entregas realizadas
    public Retorno eliminarCamion(String matricula);
    
//2.6
    //pre: nombre != null &&  descripcion != null    
    //post: Retorna OK si se pudo registrar el producto, ERROR_1 si hay un producto que se llame igual, ERROR_2 si la descripcion es vacia
    public Retorno registrarProducto(String nombre, String descripcion);
    
//3.1    
     //pre:  matriculaCamion != null &&  codigoProd != null && nroCaja != 0, cantUnidades >= 0
    //post: Retorna OK si se pudo despachar el producto, ERROR_1 si no existe la matricula, 
            //ERROR_2 si no existe el codigoProd, ERROR_3 si la cantUnidades es menor o igual a cero, ERROR_4 si ya existe el numero de caja, 
            //ERROR_5 si no existe capacidad en la fabrica para despachar
    public Retorno altaDeStockDeProducto(String matriculaCamion, int codigoProd, int nroCaja, int cantUnidades);
   
//3.2    
     //pre: matriculaCam != null &&  rutCliente != null && codProducto != 0
    //post: Retorna OK si se pudo retirar  total o parcialmente, ERROR_1 si no existe la matricula, 
            //ERROR_2 si no existe el rutCliente, ERROR_3 si no existe el codProducto
    public Retorno retiroDeProducto(String matriculaCam, String rutCliente, int codProducto, int cant); 
    
//4.1    
     //pre:      post: Retorna la lista de camiones
    public Retorno listarCamiones();
    
//4.2      
     //pre:      post: Retorna la lista de clientes ordenados alfabeticamente
    public Retorno listarClientesOrdenado(); 

//4.3    
     //pre:      post: Retorna la lista de productos con el stock disponible
    public Retorno listarProductos(); 

//4.4    
     //pre:      post: Retorna el ultimo producto registrado
    public Retorno ultimoProductoRegistrado();
    
 //4.5
     //pre: codProd != null     post: Retorna OK si se muestran los envios correctamente, ERROR_1 si no existe el codProd
    public Retorno listarEnvíosDeProducto(int codProd);
    
//4.6       
    //pre: codProd != null      post: Retorna OK si se listan las ordenes pendientes de manera correcta, ERROR_1 si no existe el codProd
    public Retorno listarOrdenesPendientes(int codProd);
    
//4.7     
    //pre:      post: Retorna el reporte de manera correcta
    public int[][] reporteDeEnviosDeProductos();
    
    
}
