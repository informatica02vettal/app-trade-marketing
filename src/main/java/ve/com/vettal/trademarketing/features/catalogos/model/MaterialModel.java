package ve.com.vettal.trademarketing.features.catalogos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "materiales")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", nullable = true)
	private MarcaModel marca;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoria_id", nullable = false)
	private CategoriaMaterialModel categoria;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

	@Column(name = "requiere_medidas", nullable = false)
	private boolean requiereMedidas;

	@Column(name = "requiere_ubicacion", nullable = false)
	private boolean requiereUbicacion;

	@Column(name = "minimo_fotos", nullable = false)
	private int minimoFotos;

	@Builder.Default
	@Column(name = "activo", nullable = false)
	private boolean activo = true;
}
