package ve.com.vettal.trademarketing.features.solicitudes.dto;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.solicitudes.model.CategoriaSolicitud;
import ve.com.vettal.trademarketing.features.solicitudes.model.EstadoSolicitud;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SolicitudResponseDto {

	private Long id;
	private String erpClienteId;
	private String clienteNombre;
	private CategoriaSolicitud categoria;
	private String marca;
	private String observaciones;
	private Long solicitanteId;
	private String solicitanteNombre;
	private EstadoSolicitud estado;
	private Long aprobadoPorId;
	private String aprobadoPorNombre;
	private List<SolicitudItemResponseDto> items;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
