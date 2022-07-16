package br.com.compass.sprint04.service;

import br.com.compass.sprint04.dto.request.AssociadoAtualizaRequestDTO;
import br.com.compass.sprint04.dto.request.AssociadoRequestDTO;
import br.com.compass.sprint04.dto.request.AssociadoVinculacaoRequestDTO;
import br.com.compass.sprint04.dto.response.AssociadoResponseDTO;
import br.com.compass.sprint04.entity.AssociadoEntity;
import br.com.compass.sprint04.entity.PartidoEntity;
import br.com.compass.sprint04.exceptions.AssociadoNaoEncontrado;
import br.com.compass.sprint04.exceptions.PartidoNaoEncontrado;
import br.com.compass.sprint04.repository.AssociadoRepository;
import br.com.compass.sprint04.repository.PartidoRepository;
import br.com.compass.sprint04.util.AssociadoValidacao;
import br.com.compass.sprint04.util.ConverteDatas;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AssociadoService {
    @Autowired
    private AssociadoValidacao validacao;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private PartidoRepository partidoRepository;

    @Autowired
    private ConverteDatas converteDatas;

    public AssociadoResponseDTO salva(AssociadoRequestDTO requestDTO) {
        requestDTO.setCargoPolitico(validacao.validaCargoPolitico(requestDTO.getCargoPolitico()));

        requestDTO.setSexo(validacao.validaSexo(requestDTO.getSexo()));

        AssociadoEntity associadoEntity = modelMapper.map(requestDTO, AssociadoEntity.class);
        associadoRepository.save(associadoEntity);

        return modelMapper.map(associadoEntity, AssociadoResponseDTO.class);

    }

    public void vinculaAssociadoAoPartido(AssociadoVinculacaoRequestDTO requestDTO) {
        Long idPartido = requestDTO.getIdPartido();
        Long idAssociado = requestDTO.getIdAssociado();
        PartidoEntity partidoEntity = partidoRepository.findById(idPartido).orElseThrow(PartidoNaoEncontrado::new);
        AssociadoEntity associadoEntity = associadoRepository.findById(idAssociado).orElseThrow(AssociadoNaoEncontrado::new);
        associadoEntity.setPartidoId(partidoEntity);
        associadoRepository.save(associadoEntity);
    }

    public List<AssociadoResponseDTO> listaAssociados(String cargoPolitico, String ordernarPor) {
        List<AssociadoEntity> associadoEntityList = associadoRepository.findWithFilters(cargoPolitico, Sort.by(Sort.Direction.DESC, ordernarPor));
        return associadoEntityList
                .stream().map(
                        associadoEntity -> modelMapper.map(associadoEntity, AssociadoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AssociadoResponseDTO mostraAssociado(Long id) {
        AssociadoEntity associadoEntity = associadoRepository.findById(id).orElseThrow(AssociadoNaoEncontrado::new);
        return modelMapper.map(associadoEntity, AssociadoResponseDTO.class);
    }

    public AssociadoResponseDTO atualizaAssociado(Long id, AssociadoAtualizaRequestDTO requestDTO) {
        AssociadoEntity associadoEntity = associadoRepository.findById(id).orElseThrow(AssociadoNaoEncontrado::new);

        if (requestDTO.getNome() != null) {
            associadoEntity.setNome(requestDTO.getNome());
        }
        if (requestDTO.getCargoPolitico() != null) {
            String cargoValidado = validacao.validaCargoPolitico(requestDTO.getCargoPolitico());
            associadoEntity.setCargoPolitico(cargoValidado);
        }
        if (requestDTO.getDataNascimento() != null) {
            LocalDate data = converteDatas.formataDataISO8601(requestDTO.getDataNascimento());
            associadoEntity.setDataNascimento(data);
        }
        if (requestDTO.getSexo() != null) {
            String sexoValido = validacao.validaSexo(requestDTO.getSexo());
            associadoEntity.setSexo(sexoValido);
        }

        associadoRepository.save(associadoEntity);
        return modelMapper.map(associadoEntity, AssociadoResponseDTO.class);
    }

    public void deletaAssociado(Long id) {
        AssociadoEntity associadoEntity = associadoRepository.findById(id).orElseThrow(AssociadoNaoEncontrado::new);
        associadoRepository.delete(associadoEntity);
    }

    public AssociadoResponseDTO desvinculacao(Long idAssociado, Long idPartido) {
        partidoRepository.findById(idPartido).orElseThrow(PartidoNaoEncontrado::new);
        AssociadoEntity associadoEntity = associadoRepository.findById(idAssociado).orElseThrow(AssociadoNaoEncontrado::new);
        associadoEntity.setPartidoId(null);
        associadoRepository.save(associadoEntity);
        return modelMapper.map(associadoEntity, AssociadoResponseDTO.class);
    }
}


