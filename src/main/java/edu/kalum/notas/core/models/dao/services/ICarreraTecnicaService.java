package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.entities.CarreraTecnica;

import java.util.List;

public interface ICarreraTecnicaService {
    public List<CarreraTecnica> findAll();
    public CarreraTecnica findById(String id);
    public CarreraTecnica save(CarreraTecnica carreraTecnica);
    public void delete (CarreraTecnica carreraTecnica);
    public void deleteById (String id);
}
