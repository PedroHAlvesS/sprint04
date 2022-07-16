package br.com.compass.sprint04.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
public class AssociadoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String cargoPolitico;
    private LocalDate dataNascimento;
    private String sexo;
    @ManyToOne(optional = true, cascade = CascadeType.REFRESH)
    private PartidoEntity partidoId;
}
