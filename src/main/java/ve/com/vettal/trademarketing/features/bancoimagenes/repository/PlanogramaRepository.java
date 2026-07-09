package ve.com.vettal.trademarketing.features.bancoimagenes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.PlanogramaModel;

public interface PlanogramaRepository extends JpaRepository<PlanogramaModel, Long> {

	List<PlanogramaModel> findByTipoExhibidor(String tipoExhibidor);
}
