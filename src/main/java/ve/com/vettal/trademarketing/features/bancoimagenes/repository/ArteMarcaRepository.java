package ve.com.vettal.trademarketing.features.bancoimagenes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.ArteMarcaModel;

public interface ArteMarcaRepository extends JpaRepository<ArteMarcaModel, Long> {

	List<ArteMarcaModel> findByMarca(String marca);
}
