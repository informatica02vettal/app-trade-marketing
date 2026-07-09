package ve.com.vettal.trademarketing.features.mercado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hallazgo_productos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoProductoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hallazgo_id", nullable = false)
	private HallazgoMercadoModel hallazgo;

	@Column(name = "marca", length = 100)
	private String marca;

	@Column(name = "modelo", length = 100)
	private String modelo;

	@Column(name = "precio", precision = 12, scale = 2)
	private BigDecimal precio;

	@Column(name = "foto_url", length = 500)
	private String fotoUrl;
}
