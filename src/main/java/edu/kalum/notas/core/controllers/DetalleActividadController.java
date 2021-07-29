package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.dao.services.IDetalleActividadService;
import edu.kalum.notas.core.models.dao.services.ISeminarioService;
import edu.kalum.notas.core.models.entities.DetalleActividad;
import edu.kalum.notas.core.models.entities.Seminario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping(value = "/kalum-notas/v1")
public class DetalleActividadController {
    private Logger logger = LoggerFactory.getLogger(DetalleActividadController.class);
    @Autowired
    private IDetalleActividadService detalleActividadService;
    @Autowired
    private ISeminarioService seminarioService;

    @GetMapping("/actividades")
    public ResponseEntity<?> listarActividades(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de actividades");
        try {
            logger.debug("Iniciando la consulta a la base de datos");
            List<DetalleActividad> actividadList = detalleActividadService.findAll();
            if(actividadList == null || actividadList.size() == 0){
                logger.warn("No exiten registros en la tabla");
                response.put("Mensaje","No exiten registros en la tabla Detalle de actividades");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else {
                logger.info("Obteniendo la información");
                return new ResponseEntity<List<DetalleActividad>>(actividadList,HttpStatus.OK);
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

    @GetMapping("/actividades/{id}")
    public ResponseEntity<?> showActividad(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de actividades por id");
        try {
            logger.debug("Iniciando la consulta de la actividad con el id ".concat(id));
            DetalleActividad detalleActividad = this.detalleActividadService.findById(id);
            if (detalleActividad == null){
                logger.warn("No existe la actividad con el id ".concat(id));
                response.put("Mensaje","No existe la actividad con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Obteniendo la información de la actividad con el id ".concat(id));
                return new ResponseEntity<DetalleActividad>(detalleActividad,HttpStatus.OK);
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

    @PostMapping("/actividades")
    public ResponseEntity<?> create(@Valid @RequestBody DetalleActividad registro, BindingResult result){
        DetalleActividad detalleActividad = null;
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
            registro.setDetalleActividadId(UUID.randomUUID().toString());
            detalleActividad = this.detalleActividadService.save(registro);
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
        response.put("Mensaje","La actividad fue creada con éxito");
        response.put("Actividad",detalleActividad);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/actividades/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody DetalleActividad update, BindingResult result, @PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        DetalleActividad actividad = this.detalleActividadService.findById(id);
        if(actividad == null){
            logger.debug("No existe la actividad con el id ".concat(id));
            response.put("Mensaje","No existe la actividad con el id ".concat(id));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            Seminario seminario = seminarioService.findById(update.getSeminario().getSeminarioId());
            if(seminario == null){
                logger.warn("No existe el seminario con el id ".concat(update.getSeminario().getSeminarioId()));
                response.put("Mensaje","No existe el seminario con el id ".concat(update.getSeminario().getSeminarioId()));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }
            actividad.setNotaActividad(update.getNotaActividad());
            actividad.setNombreActividad(update.getNombreActividad());
            actividad.setEstado(update.getEstado());
            actividad.setFechaCreacion(update.getFechaCreacion());
            actividad.setFechaEntrega(update.getFechaEntrega());
            actividad.setFechaPostergacion(update.getFechaPostergacion());
            actividad.setSeminario(seminario);
            this.detalleActividadService.save(actividad);
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
        response.put("Mensaje","La actividad fue modificada exitosamente");
        response.put("Actividad",actividad);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    @DeleteMapping("/actividades/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        DetalleActividad actividad = null;
        try {
            actividad = this.detalleActividadService.findById(id);
            if(actividad == null){
                response.put("Mensaje","No existe ninguna actividad con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else {
                this.detalleActividadService.delete(actividad);
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
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
