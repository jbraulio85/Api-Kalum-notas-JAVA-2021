package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.DetalleActividad;

import java.util.List;

public interface IDetalleActividadService {
    public List<DetalleActividad> findAll();
    public DetalleActividad findById(String id);
    public DetalleActividad save(DetalleActividad detalleActividad);
    public void delete(DetalleActividad detalleActividad);
    public void deleteById(String id);
}
