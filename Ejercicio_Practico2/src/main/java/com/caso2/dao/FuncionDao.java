/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.caso2.dao;

/**
 *
 * @author Jose Sequeira
 */
import com.caso2.domain.Funcion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FuncionDao extends JpaRepository<Funcion, Long> {

    //Ejemplo de método utilizando Métodos de Query
        public List<Funcion> findByPrecioBetweenOrderByDescripcion(double precioInf,double precioSup);
        //Ejemplo de método utilizando Consultas con JPQL
        @Query(value="SELECT a FROM Funcion a where a.precio BETWEEN :precioInf AND :precioSup ORDER BY a.descripcion ASC")
        public List<Funcion> metodoJPQL(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
        
        //Ejemplo de método utilizando Consultas con SQL nativo
        @Query(nativeQuery=true,
               value="SELECT * FROM funcion where funcion.precio BETWEEN :precioInf AND :precioSup ORDER BY producto.descripcion ASC")
        public List<Funcion> metodoNativo(@Param("precioInf") double precioInf, @Param("precioSup") double precioSup);
}
 
