package ve.com.vettal.trademarketing.features.auditorias.model;

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
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.catalogos.model.MarcaModel;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Entity
@Table(name = "auditorias_marca")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditoriaMarcaModel {

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

	@Column(name = "presencia_pct", nullable = false)
	private Integer presenciaPct;

	@Column(name = "anaquel_pct", nullable = false)
	private Integer anaquelPct;

	@Column(name = "frentes_vettal", nullable = false)
	private Integer frentesVettal;

	@Column(name = "frentes_totales", nullable = false)
	private Integer frentesTotales;

	@Column(name = "exhibidor_marca", nullable = false)
	private boolean exhibidorMarca;

	@Column(name = "producto_exhibidor", nullable = false)
	private boolean productoExhibidor;

	@Column(name = "producto_anaquel", nullable = false)
	private boolean productoAnaquel;

	@Column(name = "aviso_fachada", nullable = false)
	private boolean avisoFachada;

	@Column(name = "aviso_pared", nullable = false)
	private boolean avisoPared;

	@Column(name = "banderines", nullable = false)
	private boolean banderines;

	@Column(name = "rotulado", nullable = false)
	private boolean rotulado;

	@Column(name = "empleados_uniforme", nullable = false)
	private boolean empleadosUniforme;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_exhibidores", nullable = false, length = 20)
	private EstadoExhibidor estadoExhibidores;

	@Enumerated(EnumType.STRING)
	@Column(name = "estado_pop", nullable = false, length = 20)
	private EstadoPop estadoPop;

	@Column(name = "competencia_detectada", length = 255)
	private String competenciaDetectada;

	@Column(name = "oportunidad", columnDefinition = "TEXT")
	private String oportunidad;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
