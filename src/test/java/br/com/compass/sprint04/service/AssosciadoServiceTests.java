package br.com.compass.sprint04.service;

import br.com.compass.sprint04.dto.request.AssociadoAtualizaRequestDTO;
import br.com.compass.sprint04.dto.request.AssociadoVinculacaoRequestDTO;
import br.com.compass.sprint04.entity.AssociadoEntity;
import br.com.compass.sprint04.entity.PartidoEntity;
import br.com.compass.sprint04.repository.AssociadoRepository;
import br.com.compass.sprint04.repository.PartidoRepository;
import br.com.compass.sprint04.util.AssociadoValidacao;
import br.com.compass.sprint04.util.ConverteDatas;
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
public class AssosciadoServiceTests {
    @InjectMocks
    private AssociadoService associadoService;

    @Mock
    private AssociadoRepository associadoRepository;
    @Mock
    private PartidoRepository partidoRepository;
    @Mock
    private ModelMapper modelMapper;
    @Mock
    private AssociadoValidacao validacao;
    @Mock
    private ConverteDatas converteDatas;

    private AssociadoEntity associadoEntity;
    private PartidoEntity partidoEntity;

    @BeforeEach
    private void inicializar() {
        this.associadoEntity = new AssociadoEntity();
        this.partidoEntity = new PartidoEntity();
        Mockito.when(associadoRepository.findById(1l)).thenReturn(Optional.of(this.associadoEntity));
    }

    @Test
    @DisplayName("Deveria vincular um associado em um partido existente")
    void deveriaVincularUmAssociadoEmUmPartidoExistente() {

        // Mockito Config
        Mockito.when(partidoRepository.findById(1l)).thenReturn(Optional.of(this.partidoEntity));
        Mockito.when(associadoRepository.save(associadoEntity)).thenReturn(this.associadoEntity);

        associadoEntity.setId(1l);
        partidoEntity.setId(1l);

        AssociadoVinculacaoRequestDTO requestDTO = new AssociadoVinculacaoRequestDTO();
        requestDTO.setIdAssociado(associadoEntity.getId());
        requestDTO.setIdPartido(partidoEntity.getId());

        associadoService.vinculaAssociadoAoPartido(requestDTO);

        Mockito.verify(associadoRepository).save(associadoEntity);
        Assertions.assertEquals(associadoEntity.getPartidoId(), partidoEntity);

    }

    @Test
    @DisplayName("Deveria Permitir Atualizar Partes do associado")
    void deveriaPermitirAtualizaApenasUmCampoDoAssociado() {
        // Mockito Config
        Mockito.when(associadoRepository.save(associadoEntity)).thenReturn(this.associadoEntity);
        LocalDate dataEsperada = LocalDate.of(2000, 5, 27);
        Mockito.when(converteDatas.formataDataISO8601("27/5/2000")).thenReturn(dataEsperada);
        Mockito.when(validacao.validaSexo("Feminino")).thenReturn("Feminino");
        Mockito.when(validacao.validaCargoPolitico("Prefeito")).thenReturn("Prefeito");

        associadoEntity.setId(1l);
        associadoEntity.setSexo("Masculino");
        associadoEntity.setNome("Zaphod");
        associadoEntity.setCargoPolitico("Presidente");
        LocalDate dataEntidade = LocalDate.of(2000, 10, 12);
        associadoEntity.setDataNascimento(dataEntidade);

        AssociadoAtualizaRequestDTO requestDTO = new AssociadoAtualizaRequestDTO();

        // testes individuais
        requestDTO.setNome("Jose");
        associadoService.atualizaAssociado(associadoEntity.getId(), requestDTO);
        Assertions.assertEquals(associadoEntity.getNome(), requestDTO.getNome());

        requestDTO.setCargoPolitico("Prefeito");
        associadoService.atualizaAssociado(1l, requestDTO);
        Assertions.assertEquals(requestDTO.getCargoPolitico(), associadoEntity.getCargoPolitico());

        requestDTO.setSexo("Feminino");
        associadoService.atualizaAssociado(1l, requestDTO);
        Assertions.assertEquals(requestDTO.getSexo(), associadoEntity.getSexo());

        requestDTO.setDataNascimento("27/5/2000");
        associadoService.atualizaAssociado(1l, requestDTO);
        Assertions.assertEquals(associadoEntity.getDataNascimento(), dataEsperada);
    }

    @Test
    @DisplayName("Deveria Permitir Atualizar Partes do associado")
    void naoPermitirAtualizarCasoParametroSejaNull() {
        // Mockito Config
        Mockito.when(associadoRepository.save(associadoEntity)).thenReturn(this.associadoEntity);

        associadoEntity.setId(1l);
        associadoEntity.setNome("teste");
        associadoEntity.setCargoPolitico("teste");
        associadoEntity.setSexo("teste");
        LocalDate dataNascimento = LocalDate.of(2000, 10, 15);
        associadoEntity.setDataNascimento(dataNascimento);

        AssociadoAtualizaRequestDTO requestDTO = new AssociadoAtualizaRequestDTO();

        associadoService.atualizaAssociado(associadoEntity.getId(), requestDTO);

        Assertions.assertNotNull(associadoEntity.getNome());
        Assertions.assertNotNull(associadoEntity.getSexo());
        Assertions.assertNotNull(associadoEntity.getCargoPolitico());
        Assertions.assertNotNull(associadoEntity.getDataNascimento());

    }

    @Test
    @DisplayName("deveria desvincular o associado do partido")
    void deveriaDesvincularOAssociadoDoPartido() {

        //Mockito Config
        Mockito.when(partidoRepository.findById(1l)).thenReturn(Optional.of(this.partidoEntity));
        Mockito.when(associadoRepository.save(associadoEntity)).thenReturn(this.associadoEntity);

        associadoEntity.setId(1l);
        partidoEntity.setId(1l);

        associadoEntity.setPartidoId(partidoEntity);
        Assertions.assertEquals(associadoEntity.getPartidoId(), partidoEntity);

        associadoService.desvinculacao(associadoEntity.getId(), partidoEntity.getId());

        Mockito.verify(associadoRepository).save(associadoEntity);
        Assertions.assertNull(associadoEntity.getPartidoId());

    }

    @Test
    @DisplayName("Deveria deletar um associado cadastrado")
    void deveriaDeletarUmAssociado() {
        associadoEntity.setId(1l);

        associadoService.deletaAssociado(1l);

        Mockito.verify(associadoRepository).delete(associadoEntity);


    }

}
