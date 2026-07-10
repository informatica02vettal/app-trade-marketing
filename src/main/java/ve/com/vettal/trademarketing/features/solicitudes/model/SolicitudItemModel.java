package ve.com.vettal.trademarketing.features.solicitudes.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_id", nullable = false)
	private MaterialModel material;

	@Column(name = "medidas", length = 50)
	private String medidas;

	@Column(name = "ubicacion", length = 100)
	private String ubicacion;

	@Builder.Default
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<SolicitudItemFotoModel> fotos = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public void addFoto(SolicitudItemFotoModel foto) {
		foto.setItem(this);
		this.fotos.add(foto);
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
