package br.com.compass.sprint04.dto.request;

import lombok.Data;

@Data
public class PartidoAtualizaRequestDTO {
    private String nome;
    private String sigla;
    private String ideologia;
    private String dataFundacao;
}
