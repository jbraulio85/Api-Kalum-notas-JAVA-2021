package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IAlumnoDao extends JpaRepository<Alumno,String> {
    public Alumno findByCarne(String carne);
    public void deleteByCarne(String carne);
}
