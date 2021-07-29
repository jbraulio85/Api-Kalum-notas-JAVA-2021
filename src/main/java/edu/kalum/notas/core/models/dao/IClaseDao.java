package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IClaseDao extends JpaRepository<Clase,String> {
}
