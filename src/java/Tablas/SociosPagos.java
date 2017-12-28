/*
 * SCM
 * SISTEMA DE CONTROL DE MOTEL
 * FPUNA - Lic. Ciencias Informaticas- Programacion
 * Clase: SociosPagos
 * @autor: Jose Segovia
 * Año: 2017
 */
package Tablas;

/**
 *
 * @autor: Jose Segovia
 */
public class SociosPagos {
    private int id_pagos;
    private int id_socio;
    private String estado;
    private String fecha;

    public int getId_pagos() {
        return id_pagos;
    }

    public void setId_pagos(int id_pagos) {
        this.id_pagos = id_pagos;
    }

    public int getId_socio() {
        return id_socio;
    }

    public void setId_socio(int id_socio) {
        this.id_socio = id_socio;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    
}
