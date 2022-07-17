package br.com.compass.sprint04.repository;

import br.com.compass.sprint04.entity.AssociadoEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
public class AssociadoRepositoryTest {

    @Autowired
    private AssociadoRepository associadoRepository;

    @Test
    void abc() {
        AssociadoEntity associado = new AssociadoEntity();
        associado.setCargoPolitico("Presidente");
        associadoRepository.save(associado);
        List<AssociadoEntity> assocaidos = associadoRepository.findWithFilters("Presidente", Sort.by(Sort.Direction.DESC, "id"));
        AssociadoEntity associadoDb = assocaidos.get(0);

        Assertions.assertEquals(associado.getCargoPolitico(), associadoDb.getCargoPolitico());
        Assertions.assertNotNull(associadoDb);

    }

}
