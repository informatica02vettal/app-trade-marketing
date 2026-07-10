package ve.com.vettal.trademarketing.features.solicitudes.model;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Entity
@Table(name = "solicitudes")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visita_id", nullable = false)
	private VisitaModel visita;

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria", nullable = false, length = 20)
	private CategoriaSolicitud categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", nullable = false)
	private MarcaModel marca;

	@Column(name = "observaciones", columnDefinition = "TEXT")
	private String observaciones;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "solicitante_id", nullable = false)
	private UsuarioModel solicitante;

	@Builder.Default
	@Enumerated(EnumType.STRING)
	@Column(name = "estado", nullable = false, length = 30)
	private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE_APROBACION;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "aprobado_por")
	private UsuarioModel aprobadoPor;

	@ToString.Exclude
	@Builder.Default
	@OneToMany(mappedBy = "solicitud", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SolicitudItemModel> items = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	public void addItem(SolicitudItemModel item) {
		item.setSolicitud(this);
		this.items.add(item);
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}
