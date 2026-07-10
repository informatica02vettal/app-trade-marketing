package ve.com.vettal.trademarketing.features.instalaciones.model;

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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Entity
@Table(name = "instalaciones_ejecucion")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionEjecucionModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visita_id", nullable = false)
	private VisitaModel visita;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marca_id", nullable = false)
	private MarcaModel marca;

	@Enumerated(EnumType.STRING)
	@Column(name = "categoria", nullable = false, length = 30)
	private CategoriaInstalacion categoria;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioModel usuario;

	@Column(name = "observaciones", columnDefinition = "TEXT")
	private String observaciones;

	@Column(name = "fecha_instalacion", nullable = false)
	private LocalDate fechaInstalacion;

	@Enumerated(EnumType.STRING)
	@Builder.Default
	@Column(name = "estado", nullable = false, length = 20)
	private EstadoInstalacion estado = EstadoInstalacion.INSTALADO;

	@Builder.Default
	@OneToMany(mappedBy = "instalacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InstalacionItemModel> items = new ArrayList<>();

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

	public void addItem(InstalacionItemModel item) {
		items.add(item);
		item.setInstalacion(this);
	}
}
