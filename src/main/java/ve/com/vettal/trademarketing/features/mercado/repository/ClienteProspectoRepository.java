package ve.com.vettal.trademarketing.features.mercado.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ve.com.vettal.trademarketing.features.mercado.model.ClienteProspectoModel;

public interface ClienteProspectoRepository extends JpaRepository<ClienteProspectoModel, Long> {
}
