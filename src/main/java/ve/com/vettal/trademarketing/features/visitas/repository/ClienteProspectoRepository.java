package ve.com.vettal.trademarketing.features.visitas.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.visitas.model.ClienteProspectoModel;

public interface ClienteProspectoRepository extends JpaRepository<ClienteProspectoModel, Long> {

	List<ClienteProspectoModel> findByVisitaId(Long visitaId);
}
