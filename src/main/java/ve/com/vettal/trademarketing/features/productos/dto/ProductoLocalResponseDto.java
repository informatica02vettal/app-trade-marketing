package ve.com.vettal.trademarketing.features.productos.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoLocalResponseDto {

	private Long id;
	private String codigo;
	private String producto;
	private String linea;
	private String subcategoria;
	private String marca;
	private String contenido;
	private BigDecimal peso;
	private BigDecimal precio;
	private String fotoUrl;
	private String nombreComercial;
	private String detalles;
	private LocalDateTime sincronizadoEn;
}
