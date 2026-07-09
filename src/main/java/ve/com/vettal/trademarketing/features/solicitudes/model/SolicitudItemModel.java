package ve.com.vettal.trademarketing.features.solicitudes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "solicitud_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudItemModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ToString.Exclude
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solicitud_id", nullable = false)
	private SolicitudModel solicitud;

	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;

	@Column(name = "medidas", length = 50)
	private String medidas;

	@Column(name = "ubicacion", length = 100)
	private String ubicacion;

	@Column(name = "foto_url", length = 500)
	private String fotoUrl;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
