package br.com.compass.sprint04.dto.response;

import lombok.Data;

@Data
public class PartidoResponseDTO {
    private Long id;
    private String nome;
    private String sigla;
    private String ideologia;
    private String dataFundacao;
}
