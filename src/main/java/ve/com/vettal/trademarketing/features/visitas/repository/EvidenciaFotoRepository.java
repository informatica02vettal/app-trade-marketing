package ve.com.vettal.trademarketing.features.visitas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.EvidenciaFotoModel;

public interface EvidenciaFotoRepository extends JpaRepository<EvidenciaFotoModel, Long> {

	List<EvidenciaFotoModel> findByVisitaId(Long visitaId);

	long countByVisitaId(Long visitaId);
}
