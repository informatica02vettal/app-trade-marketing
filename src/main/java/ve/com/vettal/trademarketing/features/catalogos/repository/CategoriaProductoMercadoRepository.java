package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaProductoMercadoModel;

public interface CategoriaProductoMercadoRepository extends JpaRepository<CategoriaProductoMercadoModel, Long> {

	List<CategoriaProductoMercadoModel> findByActivoTrue();
}
