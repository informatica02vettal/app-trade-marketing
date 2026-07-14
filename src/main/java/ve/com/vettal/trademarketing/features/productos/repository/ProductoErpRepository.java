package ve.com.vettal.trademarketing.features.productos.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.productos.model.ProductoErpModel;

public interface ProductoErpRepository extends JpaRepository<ProductoErpModel, Long> {

	Optional<ProductoErpModel> findByCodigo(String codigo);
}
