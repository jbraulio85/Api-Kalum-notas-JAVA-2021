package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.IAsignacionAlumnoDao;
import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AsignacionAlumnoServiceImpl implements IAsignacionAlumnoService{
    @Autowired
    private IAsignacionAlumnoDao asignacionAlumnoDao;
    @Override
    public List<AsignacionAlumno> findAll() {
        return asignacionAlumnoDao.findAll();
    }

    @Override
    public Page<AsignacionAlumno> finalAll(Pageable pageable) {
        return asignacionAlumnoDao.findAll(pageable);
    }

    @Override
    public AsignacionAlumno save(AsignacionAlumno asignacionAlumno) {
        return this.asignacionAlumnoDao.save(asignacionAlumno);
    }

    @Override
    public AsignacionAlumno findById(String id) {
        return this.asignacionAlumnoDao.findById(id).orElse(null);
    }

    @Override
    public void delete(AsignacionAlumno asignacionAlumno) {
        this.asignacionAlumnoDao.delete(asignacionAlumno);
    }

    @Override
    public void deleteById(String id) {
        this.asignacionAlumnoDao.deleteById(id);
    }
}
