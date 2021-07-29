package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.IDetalleActividadDao;
import edu.kalum.notas.core.models.entities.DetalleActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DetalleActividadServiceImpl implements IDetalleActividadService{
    @Autowired
    private IDetalleActividadDao detalleActividadDao;

    @Override
    public List<DetalleActividad> findAll() {
        return detalleActividadDao.findAll();
    }

    @Override
    public DetalleActividad findById(String id) {
        return detalleActividadDao.findById(id).orElse(null);
    }

    @Override
    public DetalleActividad save(DetalleActividad detalleActividad) {
        return this.detalleActividadDao.save(detalleActividad);
    }

    @Override
    public void delete(DetalleActividad detalleActividad) {
        this.detalleActividadDao.delete(detalleActividad);
    }

    @Override
    public void deleteById(String id) {
        this.detalleActividadDao.deleteById(id);
    }
}
