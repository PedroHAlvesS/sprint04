package br.com.compass.sprint04.controller;

import br.com.compass.sprint04.dto.response.ExceptionResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class PartidoControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria criar um partido")
    void deveriaCriarUmPartido() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("Pais do Futuro", "Pf", "CenTro", "27/10/2000");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    @DisplayName("Nao deveria criar um partido com ideologia invalida")
    void naoDeveriaCriarUmPartidoComIdeologiaInvalida() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "teste", "27/10/2000");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Nao deveria criar um partido com data invalida")
    void naoDeveriaCriarUmPartidoComDataInvalida() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "cEnTro", "30/02/2000");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Nao deveria criar um partido com parametro faltando")
    void naoDeveriaCriarUmPartidoComParametroFaltando() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "cEnTro", "");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Nao deveria criar um partido com nome ou sigla duplicados")
    void naoDeveriaCriarUmPartidoComNomeOuSiglaDuplicado() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "cEnTro", "25/02/2000");
        String jsonNomeDuplicado = stringJson("teste", "abc", "cEnTro", "25/02/2000");
        String jsonSiglaDuplicada = stringJson("abc", "teste", "cEnTro", "25/02/2000");
        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonNomeDuplicado)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409));

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(jsonSiglaDuplicada)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(409));

    }

    @Test
    @DisplayName("Teste da messagem de erro da Data Invalida")
    void messagemDeDataInvalidaDeveSerIgual() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "cEnTro", "30/02/2000");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        // mensagem enviada no handler
        ExceptionResponseDTO dataInvalidaPadrao = new ExceptionResponseDTO("Data invalida", "Data");
        String dataInvalidaString = dataInvalidaPadrao.toString();

        String errorMessage = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(dataInvalidaString, errorMessage);
    }

    @Test
    @DisplayName("Teste da messagem de erro da Ideologia Invalida")
    void messagemDeIdeologiaInvalidaDeveSerIgual() throws Exception {
        URI uri = new URI("/partidos");
        String json = stringJson("teste", "teste", "teste", "28/02/2000");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400))
                .andReturn();

        // mensagem enviada no handler
        ExceptionResponseDTO dataInvalidaPadrao = new ExceptionResponseDTO("Ideologia invalida", "ideologia");
        String dataInvalidaString = dataInvalidaPadrao.toString();

        String errorMessage = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(dataInvalidaString, errorMessage);
    }

    @Test
    @DisplayName("Deveria retornar not found no partido que nao existe")
    void deveriaRetornarNotFound() throws Exception {
        URI uri = new URI("/partidos/9999");
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();

        // mensagem enviada no handler
        ExceptionResponseDTO dataInvalidaPadrao = new ExceptionResponseDTO("Partido nao encontrado", "Partido");
        String dataInvalidaString = dataInvalidaPadrao.toString();

        String errorMessage = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(dataInvalidaString, errorMessage);
    }

    private String stringJson(String nome, String sigla, String ideologia, String dataFundacao) {
        String json = "{" + "\"nome\":" + "\"" + nome + "\"" + "," +"\"sigla\":" + "\"" + sigla + "\"" + ","
            + "\"ideologia\":" + "\"" + ideologia + "\"" + ","+ "\"dataFundacao\":" + "\"" + dataFundacao + "\"" + "}";
        return json;
    }










}
