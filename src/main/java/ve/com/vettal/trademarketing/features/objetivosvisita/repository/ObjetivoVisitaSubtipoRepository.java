package ve.com.vettal.trademarketing.features.objetivosvisita.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaSubtipoModel;

public interface ObjetivoVisitaSubtipoRepository extends JpaRepository<ObjetivoVisitaSubtipoModel, Long> {

	List<ObjetivoVisitaSubtipoModel> findByTipoId(Long tipoId);
}
