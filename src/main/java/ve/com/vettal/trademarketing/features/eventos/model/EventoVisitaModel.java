package ve.com.vettal.trademarketing.features.eventos.model;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;

@Entity
@Table(name = "eventos_visita")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoVisitaModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "visita_id", nullable = false, unique = true)
	private VisitaModel visita;

	@Enumerated(EnumType.STRING)
	@Column(name = "motivo", nullable = false, length = 30)
	private MotivoEvento motivo;

	@Column(name = "motivo_otro_detalle", length = 255)
	private String motivoOtroDetalle;

	@Column(name = "nombre_evento", length = 200)
	private String nombreEvento;

	@Column(name = "ciudad", length = 100)
	private String ciudad;

	@Column(name = "estado", length = 100)
	private String estado;

	@Column(name = "lugar_realizacion", length = 255)
	private String lugarRealizacion;

	@Column(name = "fecha_evento")
	private LocalDate fechaEvento;

	@Column(name = "hora_inicio", length = 10)
	private String horaInicio;

	@Column(name = "hora_fin", length = 10)
	private String horaFin;

	@Column(name = "organizador", length = 200)
	private String organizador;

	@Column(name = "objetivo_participacion", length = 500)
	private String objetivoParticipacion;

	@Enumerated(EnumType.STRING)
	@Column(name = "participacion_vettal", length = 20)
	private ParticipacionVettal participacionVettal;

	@Column(name = "cantidad_asistentes_estimada")
	private Integer cantidadAsistentesEstimada;

	@Builder.Default
	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventoLeadModel> leads = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EventoEntrevistaModel> entrevistas = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public void addLead(EventoLeadModel lead) {
		lead.setEvento(this);
		this.leads.add(lead);
	}

	public void addEntrevista(EventoEntrevistaModel entrevista) {
		entrevista.setEvento(this);
		this.entrevistas.add(entrevista);
	}
}
