package ve.com.vettal.trademarketing.features.eventos.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.eventos.model.MotivoEvento;
import ve.com.vettal.trademarketing.features.eventos.model.ParticipacionVettal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventoVisitaRequestDto {

	@NotNull(message = "La visita es obligatoria")
	private Long visitaId;

	@NotNull(message = "El motivo del evento es obligatorio")
	private MotivoEvento motivo;

	private String motivoOtroDetalle;

	private String nombreEvento;

	private String ciudad;

	private String estado;

	private String lugarRealizacion;

	private LocalDate fechaEvento;

	private String horaInicio;

	private String horaFin;

	private String organizador;

	private String objetivoParticipacion;

	private ParticipacionVettal participacionVettal;

	private Integer cantidadAsistentesEstimada;

	private Double gpsLat;

	private Double gpsLng;

	private List<EventoLeadRequestDto> leads;

	private List<String> videosEntrevistaUrls;
}
