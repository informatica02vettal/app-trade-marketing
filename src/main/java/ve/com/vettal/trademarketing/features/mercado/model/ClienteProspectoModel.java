package ve.com.vettal.trademarketing.features.mercado.model;

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
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Entity
@Table(name = "clientes_prospecto")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteProspectoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hallazgo_id")
	private HallazgoMercadoModel hallazgo;

	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;

	@Column(name = "rif", nullable = false, length = 20)
	private String rif;

	@Column(name = "whatsapp", nullable = false, length = 30)
	private String whatsapp;

	@Column(name = "telefono", length = 30)
	private String telefono;

	@Column(name = "gps_lat", nullable = false, precision = 10, scale = 7)
	private BigDecimal gpsLat;

	@Column(name = "gps_lng", nullable = false, precision = 10, scale = 7)
	private BigDecimal gpsLng;

	@Column(name = "foto_fachada_url", nullable = false, length = 500)
	private String fotoFachadaUrl;

	@Column(name = "foto_interior_url", nullable = false, length = 500)
	private String fotoInteriorUrl;

	@Column(name = "marcas_competencia", length = 255)
	private String marcasCompetencia;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioModel usuario;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
