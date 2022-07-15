package br.com.compass.sprint04.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class AssociadoRequestDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String cargoPolitico;
    @NotBlank
    private String dataNascimento;
    @NotBlank
    private String sexo;
}
