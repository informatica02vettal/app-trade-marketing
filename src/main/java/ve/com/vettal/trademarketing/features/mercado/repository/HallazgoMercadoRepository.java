package ve.com.vettal.trademarketing.features.mercado.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;

public interface HallazgoMercadoRepository extends JpaRepository<HallazgoMercadoModel, Long> {

	List<HallazgoMercadoModel> findByTipo(TipoHallazgo tipo);

	List<HallazgoMercadoModel> findByUsuarioId(Long usuarioId);

	List<HallazgoMercadoModel> findByTipoAndUsuarioId(TipoHallazgo tipo, Long usuarioId);
}
