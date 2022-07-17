package br.com.compass.sprint04.service;


import br.com.compass.sprint04.dto.request.PartidoAtualizaRequestDTO;
import br.com.compass.sprint04.entity.AssociadoEntity;
import br.com.compass.sprint04.entity.PartidoEntity;
import br.com.compass.sprint04.repository.AssociadoRepository;
import br.com.compass.sprint04.repository.PartidoRepository;
import br.com.compass.sprint04.util.ConverteDatas;
import br.com.compass.sprint04.util.PartidoValidacao;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PartidoServiceTests {

    @InjectMocks
    private PartidoService partidoService;
    @Mock
    private PartidoRepository partidoRepository;
    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private PartidoValidacao partidoValidacao;
    @Mock
    private ConverteDatas converteDatas;
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
        Mockito.when(partidoValidacao.validaIdeologia("Direita")).thenReturn("Direita");
        LocalDate dataRetornada = LocalDate.of(2000, 5, 27);
        Mockito.when(converteDatas.formataDataISO8601("27/5/2000")).thenReturn(dataRetornada);

        partidoEntity.setNome("Ricos");
        partidoEntity.setSigla("Rich");
        partidoEntity.setIdeologia("Centro");
        LocalDate dataFundacao = LocalDate.of(2000, 5, 8);
        partidoEntity.setDataFundacao(dataFundacao);
        partidoEntity.setId(1l);

        PartidoAtualizaRequestDTO requestDTO = new PartidoAtualizaRequestDTO();

        // testes individuais
        requestDTO.setNome("Teste");
        partidoService.atualiza(1l, requestDTO);
        Assertions.assertEquals(requestDTO.getNome(), partidoEntity.getNome());

        requestDTO.setSigla("Teste");
        partidoService.atualiza(1l, requestDTO);
        Assertions.assertEquals(requestDTO.getSigla(), partidoEntity.getSigla());

        requestDTO.setIdeologia("Direita");
        partidoService.atualiza(1l, requestDTO);
        Assertions.assertEquals(requestDTO.getIdeologia(), partidoEntity.getIdeologia());

        requestDTO.setDataFundacao("27/5/2000");
        partidoService.atualiza(1l, requestDTO);
        LocalDate dataEsperada = LocalDate.of(2000, 5, 27);
        Assertions.assertEquals(partidoEntity.getDataFundacao(), dataEsperada);



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
    @Test
    @DisplayName("Deveria deletar um partido")
    void deveriaDeletarUmPartido() {
        partidoEntity.setId(1l);

        Mockito.when(partidoRepository.findById(1l)).thenReturn(Optional.ofNullable(partidoEntity));

        partidoService.deleta(1l);

        Mockito.verify(partidoRepository).delete(partidoEntity);
    }

    @Test
    @DisplayName("Deveria deletar um partido")
    void deveriaListarOsAssociadosDoPartido() {
        partidoEntity.setId(1l);
        List<AssociadoEntity> associadoEntities = new ArrayList<>();

        Mockito.when(associadoRepository.findByPartidoId_id(1l)).thenReturn(associadoEntities);

        partidoService.listaAssociados(1l);

        Mockito.verify(associadoRepository).findByPartidoId_id(1l);
    }

}
