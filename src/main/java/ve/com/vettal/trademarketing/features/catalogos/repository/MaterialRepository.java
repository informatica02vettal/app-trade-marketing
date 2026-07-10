package ve.com.vettal.trademarketing.features.catalogos.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;

public interface MaterialRepository extends JpaRepository<MaterialModel, Long> {

	/**
	 * Materiales aplicables a una marca: los genéricos (marca_id NULL) más los
	 * específicos de esa marca. Si categoriaId es null, no filtra por categoría.
	 */
	@Query("""
			select m from MaterialModel m
			where m.activo = true
			and (m.marca.id = :marcaId or m.marca is null)
			and (:categoriaId is null or m.categoria.id = :categoriaId)
			order by m.categoria.id, m.nombre
			""")
	List<MaterialModel> buscarAplicables(@Param("marcaId") Long marcaId, @Param("categoriaId") Long categoriaId);
}
