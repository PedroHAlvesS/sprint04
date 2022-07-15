package br.com.compass.sprint04.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
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
    void deveriaalgo() throws Exception {
        URI uri = new URI("/associados");
        String json = "{\"nome\":\"abc\",\"cargoPolitico\":\"nenhum\",\"dataNascimento\":\"27/10/1984\",\"sexo\":\"Masculino\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }

    @Test
    void deveriaalgo3() throws Exception {
        URI uri = new URI("/associados");
        String json = "{\"nome\":\"abc\",\"cargoPolitico\":\"abc\",\"dataNascimento\":\"27/10/1984\",\"sexo\":\"Masculino\"}";

        mockMvc.perform(MockMvcRequestBuilders
                        .post(uri)
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(500));
    }

}
