/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.domain;

/**
 *
 * @author Jose Sequeira
 */

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Item extends Funcion {
    private int cantidad; //Almacenar la cantidad de items de un producto

    public Item() {
    }

    public Item(Funcion funcion) {
        super.setIdFuncion(funcion.getIdFuncion());
        super.setNombre(funcion.getNombre());
        super.setDescripcion(funcion.getDescripcion());
        super.setPrecio(funcion.getPrecio());
        super.setTipo(funcion.getTipo());
        this.cantidad = 0;
    }
}

