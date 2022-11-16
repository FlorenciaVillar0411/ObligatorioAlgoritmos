
package tads;

public class ListaOrdenadaImplementada<T extends Comparable> implements ListaOrdenada<T> {
    
    private Nodo<T> inicio;
    private int cantidadElementos;

    @Override
    public void listarOrdenado() {
        mostrarRecursivo(inicio);
    }
    
    @Override
    public void agregarOrdenado (T dato){
        if (inicio == null || dato.compareTo(inicio.getDato()) < 0){
            inicio = new Nodo<> (dato, inicio);
        } else {
            Nodo<T> aux = inicio;
            while(aux.getSig() != null && dato.compareTo(aux.getSig().getDato()) > 0){
                aux = aux.getSig();
            }
            aux.setSig(new Nodo<>(dato, aux.getSig()));
        }
        this.cantidadElementos ++;
    }

    private void mostrarRecursivo(Nodo<T> nodo) {
        if (nodo != null) {
            System.out.print(nodo.getDato().toString() + " ");
            mostrarRecursivo(nodo.getSig());
        }
    }    
    
    @Override
    public T obtener(T dato) {
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

    @Override
    public void eliminar(T dato) {
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getSig().getDato().equals(dato)) {
                aux.setSig(aux.getSig().getSig());
            }
            aux = aux.getSig();
        }
    }
}
