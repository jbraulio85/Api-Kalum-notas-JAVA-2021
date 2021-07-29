package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.ISeminarioDao;
import edu.kalum.notas.core.models.entities.Seminario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeminarioServiceImpl implements ISeminarioService{
    @Autowired
    private ISeminarioDao seminarioDao;

    @Override
    public List<Seminario> findAll() {
        return seminarioDao.findAll();
    }

    @Override
    public Seminario findById(String id) {
        return seminarioDao.findById(id).orElse(null);
    }

    @Override
    public Seminario save(Seminario seminario) {
        return this.seminarioDao.save(seminario);
    }

    @Override
    public void delete(Seminario seminario) {
        this.seminarioDao.delete(seminario);
    }

    @Override
    public void deleteById(String id) {
        this.seminarioDao.deleteById(id);
    }
}
