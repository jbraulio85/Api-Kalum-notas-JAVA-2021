package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IModuloDao extends JpaRepository<Modulo,String> {
}
