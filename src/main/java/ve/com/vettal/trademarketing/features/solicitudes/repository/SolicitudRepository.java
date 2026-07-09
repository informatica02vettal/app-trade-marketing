package ve.com.vettal.trademarketing.features.solicitudes.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.model.SolicitudModel;

public interface SolicitudRepository extends JpaRepository<SolicitudModel, Long> {

	List<SolicitudModel> findByEstado(EstadoSolicitud estado);

	List<SolicitudModel> findBySolicitanteId(Long solicitanteId);

	List<SolicitudModel> findByEstadoAndSolicitanteId(EstadoSolicitud estado, Long solicitanteId);
}
