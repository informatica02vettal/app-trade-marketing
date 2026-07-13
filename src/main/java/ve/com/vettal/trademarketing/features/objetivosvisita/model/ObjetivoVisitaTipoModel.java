package ve.com.vettal.trademarketing.features.objetivosvisita.model;

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
@Table(name = "objetivo_visita_tipos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjetivoVisitaTipoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

	@Builder.Default
	@Column(name = "activo", nullable = false)
	private boolean activo = true;
}
