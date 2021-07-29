package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="detalle_actividad")
public class DetalleActividad implements Serializable {
    @Id
    @Column(name="detalle_actividad_id")
    private String detalleActividadId;
    @Column(name="estado")
    private String estado;
    @Column(name="fecha_creacion")
    private Date fechaCreacion;
    @Column(name="fecha_entrega")
    private Date fechaEntrega;
    @Column(name="fecha_postergacion")
    private Date fechaPostergacion;
    @Column(name="nombre_actividad")
    private String nombreActividad;
    @Column(name="nota_actividad")
    private int notaActividad;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seminario_id",referencedColumnName = "seminario_id")
    @NotNull(message = "El campo seminario no puede ser nulo")
    private Seminario seminario;

    @OneToMany(mappedBy = "detalleActividad",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer,handler"})
    private List<DetalleNota> notas;
}
