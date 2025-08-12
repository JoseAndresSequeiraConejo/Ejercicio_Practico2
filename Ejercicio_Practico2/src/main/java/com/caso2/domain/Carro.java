package com.caso2.domain;

import java.util.Date;
import lombok.Data;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Entity
@Table(name="carro")
public class Carro implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_carro")
    private long idCarro;
    private String descripcion;
    private int cilindros;
    private String modelo;
    private int precio;
    private boolean activo;

    
    @ManyToOne
    @JoinColumn(name="id_categoria")
    Categoria categoria;
    
    public Carro() {
    }

    public Carro(int idCarro, String descripcion, int cilindros,boolean activo) {
        this.idCarro = idCarro;
        this.descripcion = descripcion;
        this.cilindros = cilindros;
        this.activo = activo;
        this.modelo = modelo;
    }
    
        
}
