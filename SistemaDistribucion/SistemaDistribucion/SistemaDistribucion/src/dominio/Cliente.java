/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dominio;

import java.util.Objects;

/**
 *
 * @author flore
 */
public class Cliente  implements Comparable<Cliente>{
    
    public String nombre;
    public String rut;
    public int telefono;
    public String direccion;

    public Cliente(String nombre, String rut, int telefono, String direccion) {
        this.nombre = nombre;
        this.rut = rut;
        this.telefono = telefono;
        this.direccion = direccion;
    }    

    public Cliente(String rut) {
        this.rut = rut;
    }
    
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(obj == null){
        return false;
        }
        if(getClass() != obj.getClass()){
            return false;
        }
        final Cliente other = (Cliente) obj;
        return Objects.equals(this.rut, other.rut);
    }

    @Override
    public int compareTo(Cliente o) {
        return this.nombre.compareTo(o.nombre);
    }
    
    
    public String toString() {
        return "Nombre: " + this.nombre + ", rut: " + this.rut + ", telefono: " + this.telefono + ", rut: " + this.direccion;
    }
}
