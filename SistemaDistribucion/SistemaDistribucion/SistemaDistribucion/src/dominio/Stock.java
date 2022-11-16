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
public class Stock {

    public String matriculaCamion;
    public int codigoProd;
    public int numeroCaja;
    public int cantUnidades;

    public Stock(String matriculaCamion, int codigoProd, int numeroCaja, int cantUnidades) {
        this.matriculaCamion = matriculaCamion;
        this.codigoProd = codigoProd;
        this.numeroCaja = numeroCaja;
        this.cantUnidades = cantUnidades;
    }

    public Stock(int nroCaja) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public String toString() {
        return "matriculaCamion: " + this.matriculaCamion + ", codigoProd: " + this.codigoProd + ", numeroCaja: " + this.numeroCaja + ", cantUnidades: " + this.cantUnidades;
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
        final Stock other = (Stock) obj;
        return Objects.equals(this.numeroCaja, other.numeroCaja);
    }

    public void eliminarUnidades(int cantidad) {
        this.cantUnidades -= cantidad;
    }

}
