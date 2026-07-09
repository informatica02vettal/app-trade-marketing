package ve.com.vettal.trademarketing.features.visitas.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitaResponseDto {

	private Long id;
	private Long planId;
	private String erpClienteId;
	private String clienteNombre;
	private String region;
	private Long usuarioId;
	private String usuarioNombre;
	private String ejecutivoVentas;
	private LocalDateTime checkinAt;
	private LocalDateTime checkoutAt;
	private Integer permanenciaMin;
	private String observaciones;
	private EstadoVisita estado;
	private long cantidadFotos;
}
