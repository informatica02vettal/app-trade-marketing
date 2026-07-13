package ve.com.vettal.trademarketing.features.planvisitas.repository;

import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ve.com.vettal.trademarketing.features.planvisitas.model.EstadoPlanVisita;
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;

public interface PlanVisitaRepository extends JpaRepository<PlanVisitaModel, Long> {

	@Query("""
		SELECT p FROM PlanVisitaModel p
		WHERE (:usuarioId IS NULL OR p.usuario.id = :usuarioId)
		AND (:fechaDesde IS NULL OR p.fechaProgramada >= :fechaDesde)
		AND (:fechaHasta IS NULL OR p.fechaProgramada <= :fechaHasta)
		AND (:estado IS NULL OR p.estado = :estado)
		ORDER BY p.fechaProgramada ASC, p.horaProgramada ASC
	""")
	List<PlanVisitaModel> buscar(
			@Param("usuarioId") Long usuarioId,
			@Param("fechaDesde") LocalDate fechaDesde,
			@Param("fechaHasta") LocalDate fechaHasta,
			@Param("estado") EstadoPlanVisita estado
	);
}
