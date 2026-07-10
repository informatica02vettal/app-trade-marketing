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
@Table(name = "marcas_competencia")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarcaCompetenciaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", nullable = false)
	private MarcaModel marca;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Builder.Default
	@Column(name = "activo", nullable = false)
	private boolean activo = true;
}
