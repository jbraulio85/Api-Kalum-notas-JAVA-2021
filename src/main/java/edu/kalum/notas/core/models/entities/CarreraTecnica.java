package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="carrera_tecnica")
public class CarreraTecnica implements Serializable{
    @Id
    @Column(name="codigo_carrera")
    @NotEmpty(message = "Debe de asignar un código")
    private String codigoCarrera;

    @Column(name="nombre")
    @NotEmpty(message = "Debe de ingresar un nombre")
    private String nombre;

    @OneToMany(mappedBy = "carreraTecnica", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Modulo> modulos;
}
