package ve.com.vettal.trademarketing.features.asignaciones.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.asignaciones.model.AsignacionClienteModel;

public interface AsignacionClienteRepository extends JpaRepository<AsignacionClienteModel, Long> {

	List<AsignacionClienteModel> findByUsuarioIdAndActivoTrue(Long usuarioId);

	List<AsignacionClienteModel> findByErpClienteIdAndActivoTrue(String erpClienteId);

	Optional<AsignacionClienteModel> findByErpClienteIdAndUsuarioId(String erpClienteId, Long usuarioId);
}
