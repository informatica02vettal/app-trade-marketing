package ve.com.vettal.trademarketing.features.planvisitas.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;

public interface PlanVisitaRepository extends JpaRepository<PlanVisitaModel, Long> {

	List<PlanVisitaModel> findByUsuarioIdAndFechaProgramada(Long usuarioId, LocalDate fecha);
}
