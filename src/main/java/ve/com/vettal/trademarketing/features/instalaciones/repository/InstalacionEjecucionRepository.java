package ve.com.vettal.trademarketing.features.instalaciones.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionEjecucionModel;

public interface InstalacionEjecucionRepository extends JpaRepository<InstalacionEjecucionModel, Long> {

	List<InstalacionEjecucionModel> findByUsuarioId(Long usuarioId);

	List<InstalacionEjecucionModel> findByVisitaId(Long visitaId);

	List<InstalacionEjecucionModel> findByUsuarioIdAndVisitaId(Long usuarioId, Long visitaId);
}
