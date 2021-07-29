package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "alumno")
@Entity
public class Alumno implements Serializable {
    @Id
    @Column(name="carne")
    @NotEmpty(message = "Es necesario asignar un número de carné")
    private String carne;
    @Column(name="no_expediente")
    @NotEmpty(message = "Es necesario asignar un número de expediente")
    private String noExpediente;
    @Column(name="nombres")
    private String nombres;
    @Column(name="apellidos")
    private String apellidos;
    @Column(name="email")
    @Email(message = "debe ingresar una dirección válida")
    private String email;
    @OneToMany
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer,handler"})
    private List<AsignacionAlumno> asignaciones;

    @OneToMany(mappedBy = "alumno",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer,handler"})
    private List<DetalleNota> notas;
}
