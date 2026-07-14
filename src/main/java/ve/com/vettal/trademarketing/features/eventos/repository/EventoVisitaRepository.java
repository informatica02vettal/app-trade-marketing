package ve.com.vettal.trademarketing.features.eventos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.eventos.model.EventoVisitaModel;

public interface EventoVisitaRepository extends JpaRepository<EventoVisitaModel, Long> {

	Optional<EventoVisitaModel> findByVisitaId(Long visitaId);
}
