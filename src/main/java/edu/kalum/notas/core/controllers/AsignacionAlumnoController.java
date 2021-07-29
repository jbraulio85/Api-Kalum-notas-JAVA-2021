package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.dao.services.IAlumnoService;
import edu.kalum.notas.core.models.dao.services.IAsignacionAlumnoService;
import edu.kalum.notas.core.models.dao.services.IClaseService;
import edu.kalum.notas.core.models.entities.Alumno;
import edu.kalum.notas.core.models.entities.AsignacionAlumno;
import edu.kalum.notas.core.models.entities.Clase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/kalum-notas/v1")
public class AsignacionAlumnoController {
    private Logger logger = LoggerFactory.getLogger(AsignacionAlumnoController.class);
    @Autowired
    private IAsignacionAlumnoService asignacionAlumnoService;
    @Autowired
    private IAlumnoService alumnoService;
    @Autowired
    private IClaseService claseService;


    @GetMapping("/asignaciones")
    public ResponseEntity<?> listarAsignaciones(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de asignaciones a la base de datos");
        try{
            logger.debug("Iniciando consulta a la base de datos");
            List<AsignacionAlumno> listaAsignaciones = asignacionAlumnoService.findAll();
            if(listaAsignaciones == null || listaAsignaciones.size() == 0){
                logger.warn("No existen registros en la tabla de asignaciones");
                response.put("Mensaje","No Existen registros en la tabla de asignaciones");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else{
                logger.info("Obteniendo la información de las asignaciones");
                return new ResponseEntity<List<AsignacionAlumno>>(listaAsignaciones,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @GetMapping("/asignaciones/{id}")
    public ResponseEntity<?> showAsignacion(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de asignaciones por id");
        try{
            logger.debug("Iniciando la consulta de la asignación por # de id".concat(id));
            AsignacionAlumno asignacion = asignacionAlumnoService.findById(id);
            if(asignacion == null){
                logger.debug("No existe la asignación con el id".concat(id));
                response.put("Mensaje","No existe la asignación con el id".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else {
                logger.info("Obteniendo la información de la asignación con el id ".concat(id));
                return new ResponseEntity<AsignacionAlumno>(asignacion,HttpStatus.OK);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    @PostMapping("/asignaciones")
    public ResponseEntity<?> create(@Valid @RequestBody AsignacionAlumno registro, BindingResult result){
        AsignacionAlumno asignacionAlumno = null;
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores",errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            registro.setAsignacionId(UUID.randomUUID().toString());
            asignacionAlumno = this.asignacionAlumnoService.save(registro);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La asignación fue creada con exito");
        response.put("Asignación",asignacionAlumno);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/asignaciones/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody AsignacionAlumno update, BindingResult result, @PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        AsignacionAlumno asignacion = this.asignacionAlumnoService.findById(id);
        if(asignacion == null){
            response.put("Mensaje","No existe la asignación con el id".concat(id));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            Alumno alumno = alumnoService.findByCarne(update.getAlumno().getCarne());
            if(alumno == null){
                response.put("Mensaje","No Existe el alumno con el carné".concat(update.getAlumno().getCarne()));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            Clase clase = claseService.findById(update.getClase().getClaseId());
            if(clase == null){
                response.put("Mensaje","No existe la clase con el id ".concat(update.getClase().getClaseId()));
                return  new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            asignacion.setFechaAsignacion(update.getFechaAsignacion());
            asignacion.setAlumno(alumno);
            asignacion.setClase(clase);
            this.asignacionAlumnoService.save(asignacion);
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de consultar la información a la base de datos");
            response.put("Mensaje","Error al momento de consultar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","La asignación fue modificada con éxito");
        response.put("Asignación",asignacion);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/asignaciones/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        AsignacionAlumno asignacion = null;
        try {
            asignacion = asignacionAlumnoService.findById(id);
            if(asignacion == null){
                response.put("Mensaje","No existe ninguna asignación con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else {
                asignacionAlumnoService.delete(asignacion);
            }
        }catch (CannotCreateTransactionException e){
            logger.error("Error al momento de conectarse a la base de datos");
            response.put("Mensaje","Error al momento de conectarse a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }catch (DataAccessException e){
            logger.error("Error al momento de eliminar la información a la base de datos");
            response.put("Mensaje","Error al momento de eliminar la información a la base de datos");
            response.put("Error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.SERVICE_UNAVAILABLE);
        }
        response.put("Mensaje","Se eliminado la asignación con el id ".concat(id));
        response.put("Mensaje",asignacion);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
