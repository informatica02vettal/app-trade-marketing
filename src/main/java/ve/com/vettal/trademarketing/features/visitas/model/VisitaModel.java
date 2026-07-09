package ve.com.vettal.trademarketing.features.visitas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
import ve.com.vettal.trademarketing.features.planvisitas.model.PlanVisitaModel;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Entity
@Table(name = "visitas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "plan_id", nullable = true)
	private PlanVisitaModel plan;

	@Column(name = "erp_cliente_id", length = 10)
	private String erpClienteId;

	@Column(name = "cliente_nombre", nullable = false, length = 200)
	private String clienteNombre;

	@Column(name = "region", length = 100)
	private String region;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioModel usuario;

	@Column(name = "ejecutivo_ventas", length = 150)
	private String ejecutivoVentas;

	@Column(name = "checkin_at", nullable = false)
	private LocalDateTime checkinAt;

	@Column(name = "checkin_gps_lat", precision = 10, scale = 7)
	private BigDecimal checkinGpsLat;

	@Column(name = "checkin_gps_lng", precision = 10, scale = 7)
	private BigDecimal checkinGpsLng;

	@Column(name = "checkout_at")
	private LocalDateTime checkoutAt;

	@Column(name = "permanencia_min")
	private Integer permanenciaMin;

	@Column(name = "observaciones", columnDefinition = "TEXT")
	private String observaciones;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(name = "estado", nullable = false, length = 20)
	private EstadoVisita estado = EstadoVisita.EN_CURSO;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
