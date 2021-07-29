package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IAsignacionAlumnoService {
    public List<AsignacionAlumno> findAll();
    public Page<AsignacionAlumno> finalAll(Pageable pageable);
    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno);
    public AsignacionAlumno findById (String id);
    public void delete(AsignacionAlumno asignacionAlumno);
    public void deleteById(String id);
}
