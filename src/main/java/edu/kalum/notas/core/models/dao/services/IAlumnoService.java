package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.Alumno;

import java.util.List;

public interface IAlumnoService {
    public List<Alumno> findAll();
    public Alumno findByCarne (String carne);
    public Alumno save (Alumno alumno);
    public void delete(Alumno alumno);
    public void deleteByCarne(String carne);
}
