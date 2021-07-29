package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.dao.services.IAlumnoService;
import edu.kalum.notas.core.models.entities.Alumno;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/kalum-notas/v1")
public class AlumnoController {
    private Logger logger = LoggerFactory.getLogger(AlumnoController.class);
    @Autowired
    private IAlumnoService alumnoService;

    @GetMapping("/alumnos")
    public ResponseEntity<?> listarAlumnos(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de alumnos en la base de datos");
        try{
            logger.debug("Iniciando la consulta a la base de datos");
            List<Alumno> listaAlumnos = alumnoService.findAll();
            if(listaAlumnos == null || listaAlumnos.size() == 0){
                logger.warn("No exiten registros en la tabla alumnos");
                response.put("Mensaje","No exiten registros en la tabla alumno");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo la información de alumnos");
                return new ResponseEntity<List<Alumno>>(listaAlumnos,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información en la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/alumnos/{carne}")
    public ResponseEntity<?> showAlumno(@PathVariable String carne){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de alumnos por carné en la base de datos");
        try{
            logger.debug("Iniciando la consulta de alumno por # de carné".concat(carne));
            Alumno alumno = alumnoService.findByCarne(carne);
            if(alumno == null){
                logger.debug("No existe el alumno con el el carné ".concat(carne));
                response.put("Mensaje","No existe el alumno con el el carné ".concat(carne));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Obteniendo la información del alumno con el carné".concat(carne));
                return new ResponseEntity<Alumno>(alumno,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información en la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
