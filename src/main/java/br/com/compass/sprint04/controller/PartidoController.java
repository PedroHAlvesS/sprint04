package br.com.compass.sprint04.controller;

import br.com.compass.sprint04.dto.request.PartidoAtualizaRequestDTO;
import br.com.compass.sprint04.dto.request.PartidoRequestDTO;
import br.com.compass.sprint04.dto.response.AssociadoResponseDTO;
import br.com.compass.sprint04.dto.response.PartidoResponseDTO;
import br.com.compass.sprint04.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/partidos")
public class PartidoController {

    @Autowired
    private PartidoService partidoService;

    @PostMapping
    @Transactional
    public ResponseEntity<PartidoResponseDTO> criaPartido(@RequestBody @Valid PartidoRequestDTO requestDTO, UriComponentsBuilder uriBuilder) {
        PartidoResponseDTO responseDTO = partidoService.salva(requestDTO);
        URI uri = uriBuilder.path("/partidos/{id}").buildAndExpand(responseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> listaPartidos(@RequestParam(required = false) String ideologia) {
        List<PartidoResponseDTO> responseDTOList = partidoService.lista(ideologia);
        return ResponseEntity.ok(responseDTOList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> listaPartido(@PathVariable Long id) {
        PartidoResponseDTO responseDTO = partidoService.listaPartido(id);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("/{id}/associados")
    public ResponseEntity<List<AssociadoResponseDTO>> listaAssociados(@PathVariable Long id) {
        List<AssociadoResponseDTO> responseDTOList = partidoService.listaAssociados(id);
        return ResponseEntity.ok(responseDTOList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaPartido(@PathVariable Long id) {
        partidoService.deleta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ResponseEntity<PartidoResponseDTO> atualizaPartido(@PathVariable Long id, @RequestBody PartidoAtualizaRequestDTO requestDTO) {
        PartidoResponseDTO responseDTO = partidoService.atualiza(id, requestDTO);
        return ResponseEntity.ok(responseDTO);
    }


}
