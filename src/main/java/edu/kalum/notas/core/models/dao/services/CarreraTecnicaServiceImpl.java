package edu.kalum.notas.core.models.dao.services;

import edu.kalum.notas.core.models.dao.ICarreraTecnicaDao;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CarreraTecnicaServiceImpl implements ICarreraTecnicaService{
    @Autowired
    private ICarreraTecnicaDao carreraTecnicaDao;

    @Override
    public List<CarreraTecnica> findAll() {
        return carreraTecnicaDao.findAll();
    }

    @Override
    public CarreraTecnica findById(String id) {
        return carreraTecnicaDao.findById(id).orElse(null);
    }

    @Override
    public CarreraTecnica save(CarreraTecnica carreraTecnica) {
        return this.carreraTecnicaDao.save(carreraTecnica);
    }

    @Override
    public void delete(CarreraTecnica carreraTecnica) {
        this.carreraTecnicaDao.delete(carreraTecnica);
    }

    @Override
    public void deleteById(String id) {
        this.carreraTecnicaDao.deleteById(id);
    }
}
