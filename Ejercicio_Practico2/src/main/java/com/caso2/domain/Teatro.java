package com.caso2.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

@Data
@Entity
@Table(name = "teatro")
public class Teatro implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_teatro")
    private Long idTeatro;
    private String nombre;
    private String ubicacion;
    private boolean activo;
    
    @OneToMany
    @JoinColumn (name="id_teatro")
    List<Funcion> funciones;
    public Teatro() {
    }

    public Teatro(String nombre, boolean activo) {
        this.nombre = nombre;
        this.activo = activo;
    }
}
