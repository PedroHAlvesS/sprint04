package br.com.compass.sprint04.util;

import br.com.compass.sprint04.exceptions.IdeologiaNaoEncontrada;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PartidoValidacaoTests {
    private PartidoValidacao partidoValidacao;
    @BeforeEach
    void inicializar() {
        this.partidoValidacao = new PartidoValidacao();
    }

    @Test
    @DisplayName("Ideologia nao deve ser case sensitive")
    void ideologiaNaoDeveSerCaseSensitive() {
        String ideologiavalidada = partidoValidacao.validaIdeologia("cEnTrO");

        Assertions.assertEquals("Centro", ideologiavalidada);
    }

    @Test
    @DisplayName("Ideologia nao deve aceitar valores diferentes do enum")
    void ideologiaNaoDeveAceitarValoresDiferenteDoEnum() {
        Assertions.assertThrows(IdeologiaNaoEncontrada.class,
                () -> this.partidoValidacao.validaIdeologia("Flip"));
    }



}
