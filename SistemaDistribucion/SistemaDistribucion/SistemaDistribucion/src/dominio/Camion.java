package dominio;

import java.util.Objects;

public class Camion {

    public String matricula;
    public int toneladasMaxSoportadas;

    public Camion(String matricula, int toneladasMaxSoportadas) {
        this.matricula = matricula;
        this.toneladasMaxSoportadas = toneladasMaxSoportadas;
    }

    public Camion(String matricula) {
        this.matricula = matricula;
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
        final Camion other = (Camion) obj;
        return Objects.equals(this.matricula, other.matricula);
    }
    
    public String toString() {
        return "Matricula: " + this.matricula + ", Cdad toneladas: " + this.toneladasMaxSoportadas;
    }
}
