package com.caso2.service;


import com.caso2.domain.Funcion;
import java.util.ArrayList;
import java.util.List;

public interface FuncionService {
    
    public List<Funcion> listaFunciones=new ArrayList<>();
    
    public List<Funcion> getFunciones(boolean activos);
    
    public void save(Funcion funcion);
    
    public void delete(Funcion funcion);
    
    public Funcion getFuncion(Funcion funcion);
    
    //Lista de productos con precion entre ordenados por descripcion ConsultaAmpliada
    public List<Funcion> findByPrecioBetweenOrderByDescripcion(double precioInf,double precioSup);
    
    
    //Lista de productos utilizando consultas con JPQL
    public List<Funcion> metodoJPQL(double precioInf, double precioSup);
    
    //Lista de productos utilizando consultas con SQL nativo
    public List<Funcion> metodoNativo(double precioInf, double precioSup);
}
