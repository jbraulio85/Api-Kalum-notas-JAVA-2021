package edu.kalum.notas.core.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="modulo")
public class Modulo implements Serializable{
    @Id
    @Column(name="modulo_id")
    private String moduloId;

    @Column(name = "nombre_modulo")
    private String nombreModulo;

    @Column(name="numero_seminarios")
    private Integer numeroSerminarios;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="codigo_carrera",referencedColumnName = "codigo_carrera")
    @NotNull(message = "el campo de la carrera no es válido")
    private CarreraTecnica carreraTecnica;

    @OneToMany(mappedBy = "modulo",fetch = FetchType.EAGER)
    @JsonIgnore
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private List<Seminario> seminario;
}
