package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAsignacionAlumnoDao extends JpaRepository<AsignacionAlumno,String> {
}
