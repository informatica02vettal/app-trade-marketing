package ve.com.vettal.trademarketing.features.clientes.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.clientes.model.ClienteErpModel;

public interface ClienteErpRepository extends JpaRepository<ClienteErpModel, Long> {

	Optional<ClienteErpModel> findByCodigoCliente(String codigoCliente);
}
