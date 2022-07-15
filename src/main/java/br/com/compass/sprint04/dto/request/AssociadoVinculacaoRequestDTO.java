package br.com.compass.sprint04.dto.request;

import lombok.Data;

import javax.validation.constraints.Positive;

@Data
public class AssociadoVinculacaoRequestDTO {
    @Positive
    private Long idAssociado;
    @Positive
    private Long idPartido;
}
