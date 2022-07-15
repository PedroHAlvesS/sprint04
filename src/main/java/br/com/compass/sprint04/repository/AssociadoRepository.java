package br.com.compass.sprint04.repository;

import br.com.compass.sprint04.entity.AssociadoEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssociadoRepository extends JpaRepository<AssociadoEntity, Long> {
    // não me processa Davi
    @Query("SELECT associado FROM AssociadoEntity associado WHERE (:cargoPolitico IS NULL OR :cargoPolitico = associado.cargoPolitico)")
    List<AssociadoEntity> findWithFilters(@Param("cargoPolitico") String cargoPolitico, Sort sort);

    List<AssociadoEntity> findByPartidoId_id(Long id);
}
