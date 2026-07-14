package ve.com.vettal.trademarketing.features.productos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Versión ligera de un producto del ERP, usada para exponer la lista de
 * productos propios a auditar dentro de un plan de visita o de un hallazgo
 * de mercado, sin repetir todos los campos de {@code ProductoLocalResponseDto}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResumenDto {

	private Long id;
	private String codigo;
	private String producto;
	private String nombreComercial;
	private String marca;
}
