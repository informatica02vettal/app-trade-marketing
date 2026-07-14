package ve.com.vettal.trademarketing.features.auditorias.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.auditorias.model.AuditoriaMarcaModel;

public interface AuditoriaMarcaRepository extends JpaRepository<AuditoriaMarcaModel, Long> {

	List<AuditoriaMarcaModel> findByVisitaId(Long visitaId);

	Optional<AuditoriaMarcaModel> findByVisitaIdAndMarcaId(Long visitaId, Long marcaId);
}
