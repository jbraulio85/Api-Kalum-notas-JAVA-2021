package edu.kalum.notas.core.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="asignacion_alumno")
public class AsignacionAlumno implements Serializable {
    @Id
    @Column(name="asignacion_id")
    private String asignacionId;
    @Column(name="fecha_asignacion")
    @NotNull(message = "Debe de asignar una fecha")
    private Date fechaAsignacion;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "carne",referencedColumnName = "carne")
    @NotNull(message = "El campo carné no es valido")
    private Alumno alumno;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "clase_id",referencedColumnName = "clase_id")
    @NotNull(message = "El campo clase no es válido")
    private Clase clase;
}
