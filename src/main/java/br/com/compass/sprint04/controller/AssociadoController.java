package br.com.compass.sprint04.controller;

import br.com.compass.sprint04.dto.request.AssociadoRequestDTO;
import br.com.compass.sprint04.dto.request.AssociadoVinculacaoRequestDTO;
import br.com.compass.sprint04.dto.response.AssociadoResponseDTO;
import br.com.compass.sprint04.service.AssociadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/associados")
public class AssociadoController {
    @Autowired
    private AssociadoService associadoService;

    @PostMapping
    public ResponseEntity<AssociadoResponseDTO> criaAssociado(@RequestBody @Valid AssociadoRequestDTO associadoRequest, UriComponentsBuilder uriBuilder) {
        AssociadoResponseDTO responseDTO = associadoService.salva(associadoRequest);

        URI uri = uriBuilder.path("/associados/{id}").buildAndExpand(responseDTO.getId()).toUri();

        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PostMapping("/partidos")
    public ResponseEntity<Void> vinculaAoPartido(@RequestBody @Valid AssociadoVinculacaoRequestDTO requestDTO) {
        associadoService.vinculaAssociadoAoPartido(requestDTO);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<AssociadoResponseDTO>> listaAssociados(@RequestParam(required = false) String cargoPolitico,
                                                                      @RequestParam(required = false, defaultValue = "id") String ordernarPor) {
        List<AssociadoResponseDTO> responseDTOList = associadoService.listaAssociados(cargoPolitico, ordernarPor);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> mostraAssociado(@PathVariable Long id) {
        AssociadoResponseDTO responseDTO = associadoService.mostraAssociado(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssociadoResponseDTO> atualizaAssociado(@PathVariable Long id, @RequestBody AssociadoRequestDTO requestDTO) {
        AssociadoResponseDTO responseDTO = associadoService.atualizaAssociado(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaAssociado(@PathVariable Long id) {
        associadoService.deletaAssociado(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/partidos/{idPartidos}")
    public ResponseEntity<AssociadoResponseDTO> desvinculacao(@PathVariable Long id, @PathVariable Long idPartidos) {
        AssociadoResponseDTO responseDTO = associadoService.desvinculacao(id, idPartidos);
        return ResponseEntity.ok(responseDTO);
    }
}
