/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.service;

/**
 *
 * @author Jose Sequeira
 */

import com.caso2.domain.Teatro;
import java.util.List;

public interface TeatroService {

    public List<Teatro> getTeatros(boolean activo);

    // Se obtiene un Categoria, a partir del id de un categoria
    public Teatro getTeatro(Teatro teatro);
    
    // Se inserta un nuevo categoria si el id del categoria esta vacío
    // Se actualiza un categoria si el id del categoria NO esta vacío
    public void save(Teatro teatro);
    
    // Se elimina el categoria que tiene el id pasado por parámetro
    public void delete(Teatro teatro);
}
 
 