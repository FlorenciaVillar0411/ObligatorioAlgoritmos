
package tads;


public interface ListaOrdenada<T> {
    void listarOrdenado();
    void agregarOrdenado (T dato);
    T obtener(T dato);

    void eliminar(T dato);
}
