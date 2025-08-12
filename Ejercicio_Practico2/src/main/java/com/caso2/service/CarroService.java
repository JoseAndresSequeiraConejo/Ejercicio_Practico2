package com.caso2.service;


import com.caso2.domain.Carro;
import java.util.ArrayList;
import java.util.List;

public interface CarroService {
    
    public List<Carro> listaCarros=new ArrayList<>();
    
    public List<Carro> getCarros(boolean activos);
    
    public void save(Carro carro);
    
    public void delete(Carro carro);
    
    public Carro getCarro(Carro carro);
    
    //Lista de productos con precion entre ordenados por descripcion ConsultaAmpliada
    public List<Carro> findByPrecioBetweenOrderByDescripcion(double precioInf,double precioSup);
    
    
    //Lista de productos utilizando consultas con JPQL
    public List<Carro> metodoJPQL(double precioInf, double precioSup);
    
    //Lista de productos utilizando consultas con SQL nativo
    public List<Carro> metodoNativo(double precioInf, double precioSup);
}
