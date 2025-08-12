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
public class Item extends Carro {
    private int cantidad; //Almacenar la cantidad de items de un producto

    public Item() {
    }

    public Item(Carro carro) {
        super.setIdCarro(carro.getIdCarro());
        super.setDescripcion(carro.getDescripcion());
        super.setCilindros(carro.getCilindros());
        super.setModelo(carro.getModelo());
        super.setCategoria(carro.getCategoria());
        super.setPrecio(carro.getPrecio());
        super.setActivo(carro.isActivo());
        this.cantidad = 0;
    }
}

