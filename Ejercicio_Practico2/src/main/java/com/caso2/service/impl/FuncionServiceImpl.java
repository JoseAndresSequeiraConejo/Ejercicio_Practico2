package com.caso2.service;

import com.caso2.dao.FuncionDao;
import com.caso2.domain.Funcion;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FuncionServiceImpl implements FuncionService {

    @Autowired
    private FuncionDao funcionDao;

    @Override
    @Transactional(readOnly = true)
    public List<Funcion> getFunciones(boolean activos) {
        var lista = funcionDao.findAll();
        if (activos) {
            lista.removeIf(e -> !e.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Funcion getFuncion(Funcion funcion) {
        return funcionDao.findById(funcion.getIdFuncion()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Funcion funcion) {
        funcionDao.save(funcion);
    }

    @Override
    @Transactional
    public void delete(Funcion funcion) {
        funcionDao.delete(funcion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Funcion> findByPrecioBetweenOrderByDescripcion(double precioInf, double precioSup) {
        return funcionDao.findByPrecioBetweenOrderByDescripcion(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Funcion> metodoJPQL(double precioInf, double precioSup) {
        return funcionDao.metodoJPQL(precioInf, precioSup);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Funcion> metodoNativo(double precioInf, double precioSup) {
        return funcionDao.metodoNativo(precioInf, precioSup);
    }
}
