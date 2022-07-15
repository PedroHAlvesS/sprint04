package br.com.compass.sprint04.controller;

import br.com.compass.sprint04.dto.request.PartidoAtualizaRequestDTO;
import br.com.compass.sprint04.dto.request.PartidoRequestDTO;
import br.com.compass.sprint04.dto.response.AssociadoResponseDTO;
import br.com.compass.sprint04.dto.response.PartidoResponseDTO;
import br.com.compass.sprint04.service.PartidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
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
    public ResponseEntity<PartidoResponseDTO> criaPartido(@RequestBody @Valid PartidoRequestDTO partido, UriComponentsBuilder uriBuilder) {
        PartidoResponseDTO partidoResponseDTO = partidoService.salva(partido);
        URI uri = uriBuilder.path("/partidos/{id}").buildAndExpand(partidoResponseDTO.getId()).toUri();
        return ResponseEntity.created(uri).body(partidoResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PartidoResponseDTO>> listaPartidos(@RequestParam(required = false) String ideologia) {
        List<PartidoResponseDTO> partidosResponse = partidoService.lista(ideologia);
        return ResponseEntity.ok(partidosResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> listaPartido(@PathVariable Long id) {
        PartidoResponseDTO partidoDTO = partidoService.listaPartido(id);
        return ResponseEntity.ok(partidoDTO);
    }

    @GetMapping("/{id}/associados")
    public ResponseEntity<List<AssociadoResponseDTO>> listaAssociados(@PathVariable Long id) {
        List<AssociadoResponseDTO> dtoList = partidoService.listaAssociados(id);
        return ResponseEntity.ok(dtoList);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletaPartido(@PathVariable Long id) {
        partidoService.deleta(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidoResponseDTO> atualizaPartido(@PathVariable Long id, @RequestBody PartidoAtualizaRequestDTO partidoAtualiza) {
        PartidoResponseDTO partidoResponse = partidoService.atualiza(id, partidoAtualiza);
        return ResponseEntity.ok(partidoResponse);
    }


}
