package ve.com.vettal.trademarketing.features.clientes.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.clientes.model.SucursalClienteErpModel;

public interface SucursalClienteErpRepository extends JpaRepository<SucursalClienteErpModel, Long> {

	Optional<SucursalClienteErpModel> findByErpId(Integer erpId);

	List<SucursalClienteErpModel> findByCodigoCliente(String codigoCliente);
}
