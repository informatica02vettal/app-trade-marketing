package ve.com.vettal.trademarketing.features.bancoimagenes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "artes_marca")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArteMarcaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "marca", nullable = false, length = 100)
	private String marca;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

	@Column(name = "archivo_url", nullable = false, length = 500)
	private String archivoUrl;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(name = "tipo_archivo", nullable = false, length = 20)
	private TipoArchivo tipoArchivo = TipoArchivo.IMAGEN;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
