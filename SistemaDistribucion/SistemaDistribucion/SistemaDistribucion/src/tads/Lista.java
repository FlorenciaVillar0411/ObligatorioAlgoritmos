
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
