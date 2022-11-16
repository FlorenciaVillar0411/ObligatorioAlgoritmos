package tads;

public class ListaImplementada<T> implements Lista<T> {

    private Nodo<T> inicio;
    private int cantidadElementos;

    @Override
    public void agregarAlPrincipio(T dato) {
        this.inicio = new Nodo<>(dato, inicio);
        this.cantidadElementos++;
    }

    //Pre !esVacia()
    @Override
    public T elminarPrincipio() {
        T dato = this.inicio.getDato();
        this.inicio = this.inicio.getSig();
        this.cantidadElementos--;
        return dato;
    }

    @Override
    public void agregarAlFinal(T dato) {
        if (esVacia()) {
            agregarAlPrincipio(dato);
        } else {
            //No es vacía --> inicio != null
            Nodo<T> nuevo = new Nodo<>(dato);
            Nodo<T> fin = this.inicio;
            while (fin.getSig() != null) {
                fin = fin.getSig();
            }
            fin.setSig(nuevo);
            fin = nuevo;
            this.cantidadElementos++;
        }
    }

    @Override
    public boolean esVacia() {
        return this.inicio == null;
    }

    @Override
    public int largo() {
        return cantidadElementos;
    }

    @Override
    public void vaciar() {
        this.inicio = null;
    }

    @Override
    public void mostrar() {
        System.out.println("");
        //mostrarIterativo();
        mostrarRecursivo(inicio);
    }

    private void mostrarIterativo() {
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            System.out.print(aux.getDato() + " ");
            aux = aux.getSig();
        }
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
    public boolean existe(T dato) {
        return obtener(dato) != null;
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

    @Override
    public T obtenerFinal() {

        Nodo<T> fin = this.inicio;
        while (fin.getSig() != null) {
            fin = fin.getSig();
        }
        return fin.getDato();
    }

    @Override
    public void sort() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mostrarFiltrado(T dato) {
        filtrarRecursivo(this.inicio, dato);
    }

    private void filtrarRecursivo(Nodo<T> nodo, T dato) {
        if (nodo != null) {
            if (nodo.getDato().equals(dato)) {
                System.out.print(nodo.getDato() + " ");
            }
            mostrarRecursivo(nodo.getSig());
        }
    }

    public T obtenerPrincipio(){
        return inicio.getDato();
    }

    public T obtenerSiguiente(T dato){
        Nodo<T> aux = this.inicio;
        while (aux != null) {
            if (aux.getDato().equals(dato)) {
                return aux.getSig().getDato();
            }
            aux = aux.getSig();
        }
        return null;
    }

}
