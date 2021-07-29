package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Clase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClaseService {
    public List<Clase> findAll();
    public Page<Clase> finalAll(Pageable pageable);
    public Clase findById(String id);
    public Clase save (Clase clase);
    public void delete (Clase clase);
    public void deleteById(String id);
}
