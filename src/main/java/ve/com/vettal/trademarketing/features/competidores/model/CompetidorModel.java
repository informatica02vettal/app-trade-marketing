package ve.com.vettal.trademarketing.features.competidores.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
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
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Entity
@Table(name = "competidores")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetidorModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visita_id", nullable = false)
	private VisitaModel visita;

	@Column(name = "nombre_empresa", nullable = false, length = 200)
	private String nombreEmpresa;

	@Column(name = "marcas_representadas", length = 500)
	private String marcasRepresentadas;

	@Column(name = "tipo_productos_exhibidos", length = 500)
	private String tipoProductosExhibidos;

	@Column(name = "tamano_stand", length = 100)
	private String tamanoStand;

	@Column(name = "cantidad_promotores")
	private Integer cantidadPromotores;

	@Column(name = "cantidad_personal_tecnico")
	private Integer cantidadPersonalTecnico;

	@Builder.Default
	@Column(name = "posee_inflables", nullable = false)
	private boolean poseeInflables = false;

	@Builder.Default
	@Column(name = "posee_toldos", nullable = false)
	private boolean poseeToldos = false;

	@Builder.Default
	@Column(name = "posee_pantalla_led", nullable = false)
	private boolean poseePantallaLed = false;

	@Builder.Default
	@Column(name = "posee_experiencias_interactivas", nullable = false)
	private boolean poseeExperienciasInteractivas = false;

	@Builder.Default
	@Column(name = "realiza_demostraciones", nullable = false)
	private boolean realizaDemostraciones = false;

	@Builder.Default
	@Column(name = "entrega_material_pop", nullable = false)
	private boolean entregaMaterialPop = false;

	@Builder.Default
	@Column(name = "entrega_muestras", nullable = false)
	private boolean entregaMuestras = false;

	@Builder.Default
	@Column(name = "realiza_rifas_concursos", nullable = false)
	private boolean realizaRifasConcursos = false;

	@Builder.Default
	@Column(name = "realiza_promociones_especiales", nullable = false)
	private boolean realizaPromocionesEspeciales = false;

	@Builder.Default
	@Column(name = "cuenta_activaciones", nullable = false)
	private boolean cuentaActivaciones = false;

	@Builder.Default
	@Column(name = "posee_exhibidores_diferenciadores", nullable = false)
	private boolean poseeExhibidoresDiferenciadores = false;

	@Builder.Default
	@Column(name = "utiliza_mascotas_publicitarias", nullable = false)
	private boolean utilizaMascotasPublicitarias = false;

	@Lob
	@Column(name = "observaciones", columnDefinition = "TEXT")
	private String observaciones;

	@Builder.Default
	@OneToMany(mappedBy = "competidor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<CompetidorFotoModel> fotos = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public void addFoto(CompetidorFotoModel foto) {
		foto.setCompetidor(this);
		this.fotos.add(foto);
	}
}
