package br.com.compass.sprint04.repository;


import br.com.compass.sprint04.entity.PartidoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
public class PartidoRepositoryTests {

    @Autowired
    private PartidoRepository partidoRepository;

    @Test
    @DisplayName("Nome do partido deveria ser unico")
    void nomeDoPartidoDeveriaSerUnico() {
        PartidoEntity partido = new PartidoEntity();
        partido.setNome("Et Bilu");
        partidoRepository.save(partido);

        PartidoEntity partidoDuplicado = new PartidoEntity();
        partidoDuplicado.setNome("Et Bilu");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> partidoRepository.save(partidoDuplicado));
    }

    @Test
    @DisplayName("Sigla do partido deveria ser unica")
    void siglaDoPartidoDeveriaSerUnica() {
        PartidoEntity partido = new PartidoEntity();
        partido.setSigla("MG");
        partidoRepository.save(partido);

        PartidoEntity partidoDuplicado = new PartidoEntity();
        partidoDuplicado.setSigla("MG");
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> partidoRepository.save(partidoDuplicado));
    }
}
