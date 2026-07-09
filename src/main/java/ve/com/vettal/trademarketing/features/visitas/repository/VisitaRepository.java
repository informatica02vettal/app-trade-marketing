package ve.com.vettal.trademarketing.features.visitas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

public interface VisitaRepository extends JpaRepository<VisitaModel, Long> {

	List<VisitaModel> findByUsuarioId(Long usuarioId);
}
