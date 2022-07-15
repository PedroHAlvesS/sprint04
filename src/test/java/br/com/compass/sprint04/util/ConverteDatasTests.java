package br.com.compass.sprint04.util;

import br.com.compass.sprint04.exceptions.DataInvalida;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class ConverteDatasTests {

    private ConverteDatas converteDatas;

    @BeforeEach
    void inicializar() {
        this.converteDatas = new ConverteDatas();
    }


    @Test
    @DisplayName("Deve retornar data no formato ISO 8601 (ano-mes-dia)")
    void dataDeveRetornarNoFormatoIso8601() {

        String dataFormatoBrasileiro = "27/10/2000";
        LocalDate dataEsperada = LocalDate.of(2000, 10, 27);

        LocalDate dataFormatadaPelaService = this.converteDatas.formataDataISO8601(dataFormatoBrasileiro);

        Assertions.assertEquals(dataFormatadaPelaService, dataEsperada);
    }

    @Test
    @DisplayName("Nao deveria poder inserir uma data invalida")
    void naoDeveriaInserirDataInvalida() {

        String dataFormatoBrasileiro = "30/02/2000";

        Assertions.assertThrows(DataInvalida.class, () -> this.converteDatas.formataDataISO8601(dataFormatoBrasileiro));

    }


    @Test
    @DisplayName("Deve retornar data no formato Brasileiro (dia/mes/ano)")
    void dataDeveRetornarNoFormatoBrasileiro() {

        LocalDate dataEsperada = LocalDate.of(2000, 10, 27);
        String dataFormatoBrasileiro = "27/10/2000";

        String formataDataBrasileira = this.converteDatas.formataDataBrasileira(dataEsperada);

        Assertions.assertEquals(dataFormatoBrasileiro, formataDataBrasileira);
    }
}
