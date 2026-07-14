package ve.com.vettal.trademarketing.features.eventos.dto;

import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.eventos.model.MotivoEvento;
import ve.com.vettal.trademarketing.features.eventos.model.ParticipacionVettal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoVisitaResponseDto {

	private Long id;
	private Long visitaId;
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
	private List<EventoLeadResponseDto> leads;
	private List<String> videosEntrevistaUrls;
}
