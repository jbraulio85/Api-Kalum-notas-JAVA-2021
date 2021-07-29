package edu.kalum.notas.core.models.dao;

import edu.kalum.notas.core.models.entities.Seminario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ISeminarioDao extends JpaRepository<Seminario,String> {
}
