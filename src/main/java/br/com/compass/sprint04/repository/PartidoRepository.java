package br.com.compass.sprint04.repository;

import br.com.compass.sprint04.entity.PartidoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartidoRepository extends JpaRepository<PartidoEntity, Long> {
    List<PartidoEntity> findByIdeologia(String tipoDeIdelogia);
}
