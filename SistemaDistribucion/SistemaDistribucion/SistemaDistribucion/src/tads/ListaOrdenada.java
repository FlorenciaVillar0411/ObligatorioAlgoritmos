/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package tads;

/**
 *
 * @author florenciavillar
 */
public interface ListaOrdenada<T> {
    void listarOrdenado();
    void agregarOrdenado (T dato);
    T obtener(T dato);

    void eliminar(T dato);
}
