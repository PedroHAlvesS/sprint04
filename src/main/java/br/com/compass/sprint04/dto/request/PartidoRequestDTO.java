package br.com.compass.sprint04.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class PartidoRequestDTO {
    @NotBlank
    private String nome;
    @NotBlank
    private String sigla;
    @NotBlank
    private String ideologia;
    @NotBlank
    private String dataFundacao;
}
