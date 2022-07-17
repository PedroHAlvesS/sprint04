package br.com.compass.sprint04.util;

import br.com.compass.sprint04.exceptions.CargoPoliticoInvalido;
import br.com.compass.sprint04.exceptions.SexoInvalido;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class AssociadoValidacaoTests {

    private AssociadoValidacao associadoValidacao;
    @BeforeEach
    void inicializar() {
        this.associadoValidacao = new AssociadoValidacao();
    }

    @Test
    @DisplayName("Cargo Politico nao deve ser case sensitve")
    void cargoPoliticoNaoDeveSerCaseSensitive() {
        String ideologiavalidada = associadoValidacao.validaCargoPolitico("PrEsIdEnTe");

        Assertions.assertEquals("Presidente", ideologiavalidada);
    }

    @Test
    @DisplayName("Cargo Politico nao deve aceitar valores diferentes do enum")
    void cargoPoliticoNaoDeveAceitarValoresDiferenteDoEnum() {
        Assertions.assertThrows(CargoPoliticoInvalido.class,
                () -> this.associadoValidacao.validaCargoPolitico("Flip"));
    }

    @Test
    @DisplayName("Sexo nao deve ser case sensitve")
    void SexoNaoDeveSerCaseSensitive() {
        String ideologiavalidada = associadoValidacao.validaSexo("feMinInO");

        Assertions.assertEquals("Feminino", ideologiavalidada);
    }

    @Test
    @DisplayName("Sexo nao deve aceitar valores diferentes do enum")
    void SexoNaoDeveAceitarValoresDiferenteDoEnum() {
        Assertions.assertThrows(SexoInvalido.class,
                () -> this.associadoValidacao.validaSexo("Flip"));
    }

}
