package edu.kalum.notas.core.controllers;

import edu.kalum.notas.core.models.dao.services.ICarreraTecnicaService;
import edu.kalum.notas.core.models.dao.services.IModuloService;
import edu.kalum.notas.core.models.entities.CarreraTecnica;
import edu.kalum.notas.core.models.entities.Modulo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.CannotCreateTransactionException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value="/kalum-notas/v1")
public class ModuloController {
    private Logger logger = LoggerFactory.getLogger(ModuloController.class);
    @Autowired
    private IModuloService moduloService;
    @Autowired
    private ICarreraTecnicaService carreraTecnicaService;

    @GetMapping("/modulos")
    public ResponseEntity<?> listarModulos(){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de modulos de asignaciones a la base de datos");
        try {
            logger.debug("Iniciando la consulta a la base de datos");
            List<Modulo> listaModulos = moduloService.findAll();
            if(listaModulos == null || listaModulos.size() == 0){
                logger.warn("No existen registros en la tabla modulos");
                response.put("Mensaje","No Existen registros en la tabla modulos");
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NO_CONTENT);
            }else {
                logger.info("Obteniendo la información de la base de datos");
                return new ResponseEntity<List<Modulo>>(listaModulos,HttpStatus.OK);
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

    @GetMapping("/modulos/{id}")
    public ResponseEntity<?> showModulo(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        logger.debug("Iniciando el proceso de asignaciones por id");
        try {
            logger.debug("Iniciando la consulta a la base de datos");
            Modulo modulo = moduloService.findById(id);
            if (modulo == null){
                logger.debug("No existe el módulo con el id".concat(id));
                response.put("Mensaje","No existe el módulo con el id".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else{
                logger.info("Obteniendo la información del módulo con el id ".concat(id));
                return new ResponseEntity<Modulo>(modulo,HttpStatus.OK);
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

    @PostMapping("/modulos")
    public ResponseEntity<?> create(@Valid @RequestBody Modulo registro, BindingResult result){
        Modulo modulo = null;
        Map<String,Object> response = new HashMap<>();
        if (result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errores",errores);
            return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
        }
        try {
            registro.setModuloId(UUID.randomUUID().toString());
            modulo = this.moduloService.save(registro);
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
        response.put("Mensaje","El módulo fue creado con éxito");
        response.put("Modulo",modulo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }

    @PutMapping("/modulos/{id}")
    public ResponseEntity<?> update(@Valid @RequestBody Modulo update, BindingResult result, @PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        if(result.hasErrors()){
            List<String> errores = result.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("Errores",errores);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }
        Modulo modulo = this.moduloService.findById(id);
        if(modulo == null){
            response.put("Mensaje","No existe el módulo con el id".concat(id));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
        }
        try{
            CarreraTecnica carreraTecnica = carreraTecnicaService.findById(update.getCarreraTecnica().getCodigoCarrera());
            if (carreraTecnica == null){
                response.put("Mensaje","No existe la carrera con el id ".concat(update.getCarreraTecnica().getCodigoCarrera()));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
            }
            modulo.setNombreModulo(update.getNombreModulo());
            modulo.setNumeroSerminarios(update.getNumeroSerminarios());
            modulo.setCarreraTecnica(carreraTecnica);
            this.moduloService.save(modulo);
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
        response.put("Mensaje","El módulo fue modificado con éxito");
        response.put("Módulo",modulo);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }

    @DeleteMapping("/modulos/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        Map<String,Object> response = new HashMap<>();
        Modulo modulo = null;
        try {
            modulo = moduloService.findById(id);
            if(modulo == null){
                response.put("Mensaje","No existe ningún módulo con el id ".concat(id));
                return new ResponseEntity<Map<String,Object>>(response,HttpStatus.NOT_FOUND);
            }else {
                moduloService.delete(modulo);
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
        response.put("Mensaje","Se ha eliminado el módulo con el id ".concat(id));
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
    }
}
