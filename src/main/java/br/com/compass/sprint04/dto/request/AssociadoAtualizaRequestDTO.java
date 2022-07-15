package br.com.compass.sprint04.dto.request;

import lombok.Data;

@Data
public class AssociadoAtualizaRequestDTO {
    private String nome;
    private String cargoPolitico;
    private String dataNascimento;
    private String sexo;
}
