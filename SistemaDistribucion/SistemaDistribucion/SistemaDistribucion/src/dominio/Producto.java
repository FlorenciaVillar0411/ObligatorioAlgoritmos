
package dominio;

import java.util.Objects;

public class Producto {

    public String nombre;
    public String descripcion;
    public int codigo;
    public static int ultimoCodigo = 1;
    public int stockDisponible;

    public Producto(String nombre, String descripcion) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.codigo = Producto.ultimoCodigo;
        Producto.ultimoCodigo++;
        this.stockDisponible = 0;
    }

    public Producto(int codProd) {
        this.codigo = codProd;
    }

    public Producto(String nombre) {
        this.nombre = nombre;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (Objects.equals(this.codigo, other.codigo) || Objects.equals(this.nombre, other.nombre)) {
            return true;
        } else {
            return false;
        }
    }

    public String toString() {
        return "Nombre: " + this.nombre + ", descripcion: " + this.descripcion + ", codigo: " + this.codigo + ", stock disponible: " + this.stockDisponible;
    }
    
    public void modificarStock(int stock) {
        this.stockDisponible = this.stockDisponible + stock;
    }
}
