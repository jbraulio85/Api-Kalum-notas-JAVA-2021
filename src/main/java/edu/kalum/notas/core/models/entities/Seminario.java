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
@Table(name="seminario")
public class Seminario implements Serializable {
    @Id
    @Column(name="seminario_id")
    private String seminarioId;

    @Column(name="nombre_seminario")
    private String nombreSeminario;

    @Column(name ="fecha_inicio")
    private Date fechaInicio;

    @Column(name="fecha_fin")
    private Date fechaFin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="modulo_id",referencedColumnName = "modulo_id")
    @NotNull(message = "El campo del módulo no es válido")
    private Modulo modulo;

    @OneToMany(mappedBy = "seminario", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<DetalleActividad> detalleActividad;
}
