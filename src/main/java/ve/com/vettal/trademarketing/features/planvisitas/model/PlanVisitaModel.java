package ve.com.vettal.trademarketing.features.planvisitas.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.clientes.model.SucursalClienteErpModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaSubtipoModel;
import ve.com.vettal.trademarketing.features.objetivosvisita.model.ObjetivoVisitaTipoModel;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Entity
@Table(name = "plan_visitas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlanVisitaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "erp_cliente_id", length = 10)
	private String erpClienteId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "sucursal_id")
	private SucursalClienteErpModel sucursal;

	@Column(name = "cliente_nombre", nullable = false, length = 200)
	private String clienteNombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioModel usuario;

	@Column(name = "region", length = 100)
	private String region;

	@Column(name = "fecha_programada", nullable = false)
	private LocalDate fechaProgramada;

	@Column(name = "hora_programada", length = 10)
	private String horaProgramada;

	@Column(name = "objetivo", length = 255)
	private String objetivo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objetivo_tipo_id")
	private ObjetivoVisitaTipoModel objetivoTipo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objetivo_subtipo_id")
	private ObjetivoVisitaSubtipoModel objetivoSubtipo;

	@Lob
	@Column(name = "comentario", columnDefinition = "TEXT")
	private String comentario;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(name = "tipo_visita", nullable = false, length = 20)
	private TipoVisita tipoVisita = TipoVisita.PLANIFICADA;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(name = "estado", nullable = false, length = 20)
	private EstadoPlanVisita estado = EstadoPlanVisita.PENDIENTE;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
