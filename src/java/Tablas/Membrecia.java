/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: Membrecia
 * @autor: Jose Segovia
 * Año: 2017
 */
package Tablas;

/**
 *
 * @autor: Jose Segovia
 */
public class Membrecia {
    private int id_membrecia;
    private int id_cliente;
    private String estado;

    public int getId_membrecia() {
        return id_membrecia;
    }

    public void setId_membrecia(int id_membrecia) {
        this.id_membrecia = id_membrecia;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

}
