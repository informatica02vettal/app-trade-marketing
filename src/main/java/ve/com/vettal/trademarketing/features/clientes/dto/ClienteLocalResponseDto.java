package ve.com.vettal.trademarketing.features.clientes.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteLocalResponseDto {

	private String codigoCliente;
	private String rif;
	private String nombreFiscal;
	private String nombreComercial;
	private String direccionFiscal;
	private String telefonoPrincipal;
	private String celular;
	private String email;
	private String estado;
	private String ciudad;
	private String municipio;
	private LocalDateTime fechaCreacionErp;
	private LocalDateTime sincronizadoEn;
}
