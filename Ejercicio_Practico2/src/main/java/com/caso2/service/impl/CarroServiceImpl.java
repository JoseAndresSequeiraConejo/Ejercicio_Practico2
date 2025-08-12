package com.caso2.service;

import com.caso2.dao.CarroDao;
import com.caso2.domain.Carro;
import com.caso2.service.CarroService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarroServiceImpl implements CarroService {

    @Autowired
    private CarroDao carroDao;

    @Override
    @Transactional(readOnly = true)
    public List<Carro> getCarros(boolean activos) {
        var lista = carroDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Carro getCarro(Carro carro) {
        return carroDao.findById(carro.getIdCarro()).orElse(null);
    }
    
    @Override
    @Transactional
    public void save(Carro carro) {
        carroDao.save(carro);
    }

    @Override
    @Transactional
    public void delete(Carro carro) {
        carroDao.delete(carro);
    }

    // Lista de producto con precio entre ordenados por descripcion ConsultaAmpliada
    @Override
    @Transactional(readOnly=true)
    public List<Carro> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup){
        return carroDao.findByPrecioBetweenOrderByDescripcion(precioInf,precioSup);
    }
    
    @Override
    @Transactional(readOnly=true)
    public List<Carro> metodoJPQL(double precioInf, double precioSup) {
        return carroDao.metodoJPQL(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly=true)
    public List<Carro> metodoNativo(double precioInf, double precioSup) {
        return carroDao.metodoNativo(precioInf, precioSup);
    }
}
