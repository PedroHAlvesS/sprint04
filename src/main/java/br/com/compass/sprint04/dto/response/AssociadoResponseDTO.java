package br.com.compass.sprint04.dto.response;

import lombok.Data;

@Data
public class AssociadoResponseDTO {
    private Long id;
    private String nome;
    private String cargoPolitico;
    private String dataNascimento;
    private String sexo;
    private Long idPartido;
}
