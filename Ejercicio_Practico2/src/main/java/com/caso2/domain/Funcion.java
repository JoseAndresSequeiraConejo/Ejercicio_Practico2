package com.caso2.domain;

import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "funcion")
public class Funcion implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_funcion")
    private Long idFuncion;
    private String nombre;
    private String descripcion;
    private double precio;
    private boolean activo = true;
    private String tipo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_teatro", nullable = false)
    private Teatro teatro;

    public Funcion() {
    }

    public Funcion(String descripcion, boolean activo) {
        this.descripcion = descripcion;
        this.activo = activo;
    }
}
