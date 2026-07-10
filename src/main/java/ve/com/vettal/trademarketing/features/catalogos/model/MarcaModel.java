package ve.com.vettal.trademarketing.features.catalogos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "marcas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarcaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "codigo", nullable = false, length = 20)
	private String codigo;

	@Column(name = "nombre", nullable = false, length = 60)
	private String nombre;

	@Builder.Default
	@Column(name = "activo", nullable = false)
	private boolean activo = true;
}
