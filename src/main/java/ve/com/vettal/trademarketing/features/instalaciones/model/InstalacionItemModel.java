package ve.com.vettal.trademarketing.features.instalaciones.model;

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
import ve.com.vettal.trademarketing.features.catalogos.model.MaterialModel;

@Entity
@Table(name = "instalacion_items")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionItemModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "instalacion_id", nullable = false)
	private InstalacionEjecucionModel instalacion;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "material_id", nullable = false)
	private MaterialModel material;

	@Builder.Default
	@OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<InstalacionItemFotoModel> fotos = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	public void addFoto(InstalacionItemFotoModel foto) {
		foto.setItem(this);
		this.fotos.add(foto);
	}

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
