package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.dao.services.IModuloService;
import edu.kalum.notas.core.models.dao.services.ISeminarioService;
import edu.kalum.notas.core.models.entities.Modulo;
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
@RequestMapping(value="/kalum-notas/v1")
public class SeminarioController {

    private Logger logger = LoggerFactory.getLogger(SeminarioController.class);
    @Autowired
    private ISeminarioService seminarioService;
    @Autowired
    private IModuloService moduloService;

    @GetMapping("/seminarios")
    public ResponseEntity<?> listarSeminarios(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de seminarios a la base de datos");
        try {
            logger.debug("Iniciando consulta a la base de datos");
            List<Seminario> listaSeminarios = seminarioService.findAll();
            if(listaSeminarios == null || listaSeminarios.size() == 0){
                logger.warn("No existen registros en la tabla de seminarios");
                response.put("Mensaje","No Existen registros en la tabla de seminarios");
                return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NO_CONTENT);
            }else {
                logger.info("Obteniendo la información de las asignaciones");
                return new ResponseEntity<List<Seminario>>(listaSeminarios,HttpStatus.OK);
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

    @GetMapping("/seminarios/{id}")
    public ResponseEntity<?> showSeminario(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de consulta de seminario por Id");
        try{
            logger.debug("Iniciando el proceso de la consulta del seminario con el id ".concat(id));
            Seminario seminario = seminarioService.findById(id);
            if(seminario == null){
                logger.warn("No existe el seminario con el id ".concat(id));
                response.put("Mensaje", "No exites el seminario con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Obteniendo la información del seminario con el id ".concat(id));
                return new ResponseEntity<Seminario>(seminario,HttpStatus.OK);
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

    @PostMapping("/seminarios")
    public ResponseEntity<?> create(@Valid @RequestBody Seminario registro, BindingResult result){
        Seminario seminario = null;
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
        }
        try{
            registro.setSeminarioId(UUID.randomUUID().toString());
            seminario = this.seminarioService.save(registro);
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
        response.put("Mensaje","El seminario fue creado con éxito");
        response.put("Seminario",seminario);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/seminarios/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Seminario update, BindingResult result, @PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        Seminario seminario = this.seminarioService.findById(id);
        if(seminario == null){
            logger.warn("No existe el seminario con id ".concat(id));
            response.put("Mensaje","No existe el seminario con el id ".concat(id));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try {
            Modulo modulo = this.moduloService.findById(update.getModulo().getModuloId());
            if(modulo == null){
                logger.warn("No existe el módulo con el id ".concat(update.getModulo().getModuloId()));
                response.put("Mensaje","No existe el módulo con el id ".concat(update.getModulo().getModuloId()));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            seminario.setNombreSeminario(update.getNombreSeminario());
            seminario.setFechaInicio(update.getFechaInicio());
            seminario.setFechaFin(update.getFechaFin());
            seminario.setModulo(modulo);
            this.seminarioService.save(seminario);
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
        response.put("Mensaje","El seminario fue modificado con exito");
        response.put("Seminario",seminario);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @DeleteMapping("/seminarios/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        Seminario seminario = null;
        logger.debug("Iniciando el proceso de eliminación de seminarios");
        try {
            seminario = this.seminarioService.findById(id);
            if (seminario == null){
                logger.debug("No existe el seminario con el id ".concat(id));
                response.put("Mensaje","No existe el seminario con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }else {
                this.seminarioService.delete(seminario);
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
        logger.info("Se eliminó el seminario de manera correcta");
        response.put("Mensaje","Se eliminó el seminario con el id ".concat(id));
        response.put("Seminario",seminario);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.I_AM_A_TEAPOT);
    }
}
