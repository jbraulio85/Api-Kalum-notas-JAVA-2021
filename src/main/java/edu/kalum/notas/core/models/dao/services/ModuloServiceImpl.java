package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.IModuloDao;
import edu.kalum.notas.core.models.entities.Modulo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ModuloServiceImpl implements IModuloService{
    @Autowired
    private IModuloDao moduloDao;

    @Override
    public List<Modulo> findAll() {
        return moduloDao.findAll();
    }

    @Override
    public Modulo findById(String id) {
        return moduloDao.findById(id).orElse(null);
    }

    @Override
    public Modulo save(Modulo modulo) {
        return this.moduloDao.save(modulo);
    }

    @Override
    public void delete(Modulo modulo) {
        this.moduloDao.delete(modulo);
    }

    @Override
    public void deleteById(String id) {
        this.moduloDao.deleteById(id);
    }
}
