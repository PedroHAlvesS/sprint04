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
public class AssociadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deveria criar um associado")
    void deveriaCriarUmAssociado() throws Exception {
        URI uri = new URI("/associados");
        String json = stringJson("Zaphod Beeblebrox", "Presidente", "25/10/1982", "masculino");


        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    @DisplayName("Nao deveria criar um associado com cargo politico invalido")
    void naoDeveriaCriarUmAssociadoComCargoPoliticoInvalido() throws Exception {
        URI uri = new URI("/associados");
        String json = stringJson("Zaphod Beeblebrox", "teste", "25/10/1982", "masculino");

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    @DisplayName("Nao deveria criar um associado com data invalida")
    void naoDeveriaCriarUmAssociadoComDataInvalida() throws Exception {
        URI uri = new URI("/associados");
        String json = stringJson("Zaphod Beeblebrox", "Presidente", "42/10/1982", "masculino");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

        String msgEsperada = "{\"message\":\"Data invalida\",\"type\":\"Data\"}";

        Assertions.assertEquals(msgEsperada, mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Nao deveria criar um associado com sexo invalido")
    void naoDeveriaCriarUmAssociadoComSexoInvalido() throws Exception {
        URI uri = new URI("/associados");
        String json = stringJson("Zaphod Beeblebrox", "Presidente", "24/10/1982", "teste");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(400)).andReturn();

        String msgEsperada = "{\"message\":\"Sexo apenas Masculino ou Feminino\",\"type\":\"Sexo\"}";

        Assertions.assertEquals(msgEsperada, mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("Deveria retornar not found no get associado que nao existe")
    void deveriaRetornarNotFoundNoGet() throws Exception {
        URI uri = new URI("/associados/9999");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .get(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();

        ExceptionResponseDTO responseDTOTest = new ExceptionResponseDTO("Associado nao encontrado", "Associado");
        String stringEsperada = responseDTOTest.toString();

        String stringRetornada = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(stringEsperada, stringRetornada);

    }
    @Test
    @DisplayName("Deveria retornar not found no delete associado que nao existe\"")
    void deveriaRetornarNotFoundNoDelete() throws Exception {
        URI uri = new URI("/associados/9999");

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                        .delete(uri)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(404))
                .andReturn();

        ExceptionResponseDTO responseDTOTest = new ExceptionResponseDTO("Associado nao encontrado", "Associado");
        String stringEsperada = responseDTOTest.toString();

        String stringRetornada = mvcResult.getResponse().getContentAsString();

        Assertions.assertEquals(stringEsperada, stringRetornada);

    }



    private String stringJson(String nome, String cargoPolitico, String dataNascimento, String sexo) {
        String json = "{" + "\"nome\":" + "\"" + nome + "\"" + "," +"\"cargoPolitico\":" + "\"" + cargoPolitico + "\"" + ","
                + "\"dataNascimento\":" + "\"" + dataNascimento + "\"" + ","+ "\"sexo\":" + "\"" + sexo + "\"" + "}";
        return json;
    }

}
