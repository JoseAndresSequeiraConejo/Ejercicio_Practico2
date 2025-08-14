/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

/**
 *
 * @author Jose Sequeira
 */
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.Data;

@Data
@Entity
@Table(name="venta")
public class Venta implements Serializable {    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_venta")
    private Long idVenta;
    private Long idFactura;
    private Long idFuncion;    
    private double precio;
       
    
    public Venta() {
    }

    public Venta(Long idFactura, Long idFuncion, double precio) {
        this.idFactura = idFactura;
        this.idFuncion = idFuncion;
        this.precio = precio;
    }
}

