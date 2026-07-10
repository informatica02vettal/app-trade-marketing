package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaCompetenciaModel;

public interface MarcaCompetenciaRepository extends JpaRepository<MarcaCompetenciaModel, Long> {

	List<MarcaCompetenciaModel> findByMarcaIdAndActivoTrue(Long marcaId);
}
