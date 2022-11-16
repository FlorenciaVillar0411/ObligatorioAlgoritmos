
package tads;

    
    public interface Lista<T> {

    void agregarAlPrincipio(T dato);

    T elminarPrincipio();

    void agregarAlFinal(T dato);
    
    T obtener(T dato);

    boolean existe(T dato);
    
    boolean esVacia();

    int largo();

    void vaciar();

    void mostrar();
    
    void eliminar(T dato);

    public T obtenerFinal();

    public void sort();
    
    public void mostrarFiltrado(T dato);
    
}

    
//    @Override
//    public Iterator<T> iterator() {
//        return new Iterator<T>() {
//
//            private NodoLista<T> aux = inicio;
//
//            @Override
//            public boolean hasNext() {
//                return aux != null;
//            }
//
//            @Override
//            public T next() {
//                T dato = aux.getDato();
//                aux = aux.getSig();
//                return dato;
//            }
//
//            @Override
//            public void remove(){
//            }
//            
//        };
//    }
//    
//    
//    public class Lista<T> implements ILista<T> {
//        
//    }
//    
    /*public interface ILista<T> extends Iterable<T>{
        
        public void insertar(T dato);
        public void largo(T dato);
        public void esta_vacia(T dato);
        public void esta_llena(T dato);
    }*/

