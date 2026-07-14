package ve.com.vettal.trademarketing.features.mercado.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;

public interface HallazgoMercadoRepository extends JpaRepository<HallazgoMercadoModel, Long> {

	@Query("""
			select h from HallazgoMercadoModel h
			where (:tipo is null or h.tipo = :tipo)
			and (:usuarioId is null or h.usuario.id = :usuarioId)
			and (:marcaId is null or h.marca.id = :marcaId)
			and (:visitaId is null or h.visita.id = :visitaId)
			order by h.createdAt desc
			""")
	List<HallazgoMercadoModel> buscar(@Param("tipo") TipoHallazgo tipo, @Param("usuarioId") Long usuarioId,
			@Param("marcaId") Long marcaId, @Param("visitaId") Long visitaId);
}
