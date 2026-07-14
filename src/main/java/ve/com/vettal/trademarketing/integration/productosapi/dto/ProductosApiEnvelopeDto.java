package ve.com.vettal.trademarketing.integration.productosapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el sobre de la API de productos del ERP, que solo trae
 * {@code data} (sin {@code status}/{@code message} como la API de clientes).
 */
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductosApiEnvelopeDto<T> {

	private T data;
}
