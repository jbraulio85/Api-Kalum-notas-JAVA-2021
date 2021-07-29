package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Seminario;

import java.util.List;

public interface ISeminarioService {
    public List<Seminario> findAll();
    public Seminario findById (String id);
    public Seminario save (Seminario seminario);
    public void delete (Seminario seminario);
    public void deleteById(String id);
}
