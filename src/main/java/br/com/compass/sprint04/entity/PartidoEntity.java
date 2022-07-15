package br.com.compass.sprint04.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@Table(name = "Partidos", uniqueConstraints = {@UniqueConstraint(columnNames = {"nome", "sigla"})})
public class PartidoEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nome", unique = true)
    private String nome;
    @Column(name = "sigla", unique = true)
    private String sigla;
    private String ideologia;
    private LocalDate dataFundacao;
}
