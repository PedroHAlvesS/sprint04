package br.com.compass.sprint04.service;

import br.com.compass.sprint04.dto.request.PartidoAtualizaRequestDTO;
import br.com.compass.sprint04.dto.request.PartidoRequestDTO;
import br.com.compass.sprint04.dto.response.AssociadoResponseDTO;
import br.com.compass.sprint04.dto.response.PartidoResponseDTO;
import br.com.compass.sprint04.entity.AssociadoEntity;
import br.com.compass.sprint04.entity.PartidoEntity;
import br.com.compass.sprint04.exceptions.PartidoNaoEncontrado;
import br.com.compass.sprint04.repository.AssociadoRepository;
import br.com.compass.sprint04.repository.PartidoRepository;
import br.com.compass.sprint04.util.ConverteDatas;
import br.com.compass.sprint04.util.PartidoValidacao;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PartidoService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PartidoRepository partidoRepository;
    @Autowired
    private AssociadoRepository associadoRepository;

    @Autowired
    private ConverteDatas converteDatas;

    @Autowired
    private PartidoValidacao partidoValidacao;



    public PartidoResponseDTO salva(PartidoRequestDTO request) {
        request.setIdeologia(partidoValidacao.validaIdeologia(request.getIdeologia()));

        PartidoEntity partidoEntity = modelMapper.map(request, PartidoEntity.class);
        partidoRepository.save(partidoEntity);

        return modelMapper.map(partidoEntity, PartidoResponseDTO.class);
    }

    public List<PartidoResponseDTO> lista(String tipoDeIdeologia) {
        List<PartidoEntity> partidoEntities;
        if (tipoDeIdeologia == null) {
            partidoEntities = partidoRepository.findAll();
        } else {
            partidoEntities = partidoRepository.findByIdeologia(tipoDeIdeologia);
        }

        return partidoEntities.stream().map(
                        partidoEntity -> modelMapper.map(partidoEntity, PartidoResponseDTO.class))
                .collect(Collectors.toList());

    }

    public PartidoResponseDTO listaPartido(Long id) {
        PartidoEntity partido = partidoRepository.findById(id).orElseThrow(PartidoNaoEncontrado::new);
        return modelMapper.map(partido, PartidoResponseDTO.class);
    }

    public void deleta(Long id) {
        partidoRepository.deleteById(id);
    }

    public PartidoResponseDTO atualiza(Long id, PartidoAtualizaRequestDTO partidoAtualiza) {
        PartidoEntity partido = partidoRepository.findById(id).orElseThrow(PartidoNaoEncontrado::new);
        if (partidoAtualiza.getIdeologia() != null) {
            partido.setIdeologia(partidoValidacao.validaIdeologia(partidoAtualiza.getIdeologia()));
        }
        if (partidoAtualiza.getNome() != null) {
            partido.setNome(partidoAtualiza.getNome());
        }
        if (partidoAtualiza.getSigla() != null) {
            partido.setSigla(partidoAtualiza.getSigla());
        }
        if (partidoAtualiza.getDataFundacao() != null) {
            LocalDate data = converteDatas.formataDataISO8601(partidoAtualiza.getDataFundacao());
            partido.setDataFundacao(data);
        }
        partidoRepository.save(partido);
        return modelMapper.map(partido, PartidoResponseDTO.class);
    }

    public List<AssociadoResponseDTO> listaAssociados(Long id) {
        List<AssociadoEntity> associadoEntities = associadoRepository.findByPartidoId_id(id);
        return associadoEntities.stream()
                .map(associadoEntity -> modelMapper.map(associadoEntity, AssociadoResponseDTO.class))
                .collect(Collectors.toList());
    }
}
