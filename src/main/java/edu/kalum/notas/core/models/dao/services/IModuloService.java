package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Modulo;

import java.util.List;

public interface IModuloService {
    public List<Modulo> findAll();
    public Modulo findById (String id);
    public Modulo save (Modulo modulo);
    public void delete (Modulo modulo);
    public void deleteById (String id);
}
