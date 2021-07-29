package edu.kalum.notas.core.models.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="detalle_nota")
public class DetalleNota implements Serializable {
    @Id
    @Column(name="detalle_nota_id")
    private String detalleNotaId;
    @Column(name="valor_nota")
    private int valorNota;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="detalle_actividad_id",referencedColumnName = "detalle_actividad_id")
    @NotNull(message = "El campo de detalle de actividad no puede ser nulo")
    private DetalleActividad detalleActividad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="carne",referencedColumnName = "carne")
    @NotNull(message = "El campo de alumno no puede ser nulo")
    private Alumno alumno;
}
