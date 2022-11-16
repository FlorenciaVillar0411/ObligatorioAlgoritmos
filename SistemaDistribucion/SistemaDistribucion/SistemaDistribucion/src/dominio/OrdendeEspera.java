
package dominio;

import java.util.Objects;


public class OrdendeEspera {
    
    public String matriculaCam;
    public String rutCliente;
    public int codProducto;
    public int cant;

    public OrdendeEspera(String matriculaCam, String rutCliente, int codProducto, int cant) {
        this.matriculaCam = matriculaCam;
        this.rutCliente = rutCliente;
        this.codProducto = codProducto;
        this.cant = cant;
    }
    
        public OrdendeEspera(String atributo, boolean rut) {
        if (rut) {
            this.rutCliente = atributo;
        } else {
            this.matriculaCam = atributo;
        }
    }
    
    public OrdendeEspera(int codProducto){
        this.codProducto = codProducto;
    }
    
    public String toString() {
        return "matriculaCam: " + this.matriculaCam + ", rutCliente: " + this.rutCliente + ", codProducto: " + this.codProducto + ", cant: " + this.cant;
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
        final Retiro other = (Retiro) obj;
        if (Objects.equals(this.rutCliente, other.rutCliente) || Objects.equals(this.matriculaCam, other.matriculaCam) || Objects.equals(this.codProducto, other.codProducto)) {
            return true;
        }
        return false;
    }
}
