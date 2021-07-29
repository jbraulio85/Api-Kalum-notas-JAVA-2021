package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clase")
@Entity
public class Clase implements Serializable {
    @Id
    @Column(name="clase_id")
    private String claseId;
    @Column(name="ciclo")
    private int ciclo;
    @Column(name="cupo_maximo")
    private int cupoMaximo;
    @Column(name="cupo_minimo")
    private int cupoMinimo;
    @Column(name="descripcion")
    private String descripcion;
    @OneToMany(mappedBy = "clase", fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<AsignacionAlumno> asignaciones;
}
