package ve.com.vettal.trademarketing.features.competidores.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.competidores.model.CompetidorModel;

public interface CompetidorRepository extends JpaRepository<CompetidorModel, Long> {

	List<CompetidorModel> findByVisitaId(Long visitaId);
}
