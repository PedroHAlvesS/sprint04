package br.com.compass.sprint04.service;


import br.com.compass.sprint04.dto.request.PartidoAtualizaRequestDTO;
import br.com.compass.sprint04.entity.PartidoEntity;
import br.com.compass.sprint04.repository.PartidoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PartidoServiceTests {

    @InjectMocks
    private PartidoService partidoService;

    @Mock
    private PartidoRepository partidoRepository;

    @Mock
    private ModelMapper modelMapper;
    private PartidoEntity partidoEntity;

    @BeforeEach
    private void inicializar() {
        this.partidoEntity = new PartidoEntity();
    }

    @Test
    @DisplayName("Deve ser possivel atualizar apenas um campo")
    void deveriaAtualizarApenasUmCampoDoPartido() {
        Mockito.when(partidoRepository.save(this.partidoEntity)).thenReturn(this.partidoEntity);
        Mockito.when(partidoRepository.findById(1l)).thenReturn(Optional.of(this.partidoEntity));


        partidoEntity.setNome("Ricos");
        partidoEntity.setId(1l);

        PartidoAtualizaRequestDTO requestDTO = new PartidoAtualizaRequestDTO();
        requestDTO.setNome("Teste");

        partidoService.atualiza(1l, requestDTO);

        Assertions.assertEquals(partidoEntity.getNome(), requestDTO.getNome());
    }

    @Test
    @DisplayName("Nao deveria atualizar campos em nulo")
    void naoDeveriaAtualizarCamposDoEmNull() {
        Mockito.when(partidoRepository.save(this.partidoEntity)).thenReturn(this.partidoEntity);
        Mockito.when(partidoRepository.findById(1l)).thenReturn(Optional.of(this.partidoEntity));

        partidoEntity.setIdeologia("teste");
        partidoEntity.setSigla("teste");
        partidoEntity.setNome("teste");
        partidoEntity.setId(1l);
        LocalDate dataFundacao = LocalDate.of(2000, 8, 27);
        partidoEntity.setDataFundacao(dataFundacao);

        PartidoAtualizaRequestDTO requestDTO = new PartidoAtualizaRequestDTO();

        partidoService.atualiza(1l, requestDTO);

        Assertions.assertNotNull(partidoEntity.getNome());
        Assertions.assertNotNull(partidoEntity.getIdeologia());
        Assertions.assertNotNull(partidoEntity.getDataFundacao());
        Assertions.assertNotNull(partidoEntity.getDataFundacao());
    }

    @Test
    @DisplayName("Deve trazer todos os partidos caso ideologia seja null")
    void deveriaTrazerTodosOsPartidosCasoIdeologiaSejaNull() {
        String abc = null;

        partidoService.lista(abc);
        Mockito.verify(partidoRepository).findAll();
    }

    @Test
    @DisplayName("Deve trazer todos os partidos da ideologia Informada")
    void deveriaTrazerTodosOsPartidosDaIdeologiaInformada() {
        String abc = "abc";

        partidoService.lista(abc);
        Mockito.verify(partidoRepository).findByIdeologia(abc);
    }

}
