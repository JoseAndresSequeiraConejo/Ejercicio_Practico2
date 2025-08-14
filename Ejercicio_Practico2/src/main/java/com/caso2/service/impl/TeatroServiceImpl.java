package com.caso2.service.impl;

import com.caso2.dao.TeatroDao;
import com.caso2.domain.Teatro;
import com.caso2.service.TeatroService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeatroServiceImpl implements TeatroService {

    @Autowired
    private TeatroDao teatroDao;

    @Override
    @Transactional(readOnly = true)
    public List<Teatro> getTeatros(boolean activos) {
        var lista = teatroDao.findAll();
        if (activos) {
            lista.removeIf(t -> !t.isActivo());
        }
        return lista;
    }

    @Override
    @Transactional(readOnly = true)
    public Teatro getTeatro(Teatro teatro) {
        if (teatro == null || teatro.getIdTeatro() == null) return null;
        return teatroDao.findById(teatro.getIdTeatro()).orElse(null);
    }

    @Override
    @Transactional
    public void save(Teatro teatro) {
        teatroDao.save(teatro);
    }

    @Override
    @Transactional
    public void delete(Teatro teatro) {
        if (teatro == null) return;
        teatroDao.delete(teatro);
    }
}
