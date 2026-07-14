package ve.com.vettal.trademarketing.integration.productosapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Espeja el contrato JSON real de la API de productos del ERP
 * (GET .../api/v1.php?table=lista_prod_pw&action=list), cuyas claves vienen
 * en mayúsculas. Peso/precio/contenido se dejan como String tal como llegan
 * para no fallar la sincronización si el ERP envía un valor no numérico.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductoApiDto {

	@JsonProperty("CODIGO")
	private String codigo;

	@JsonProperty("PRODUCTO")
	private String producto;

	@JsonProperty("LINEA")
	private String linea;

	@JsonProperty("SUBCATEGORIA")
	private String subcategoria;

	@JsonProperty("MARCA")
	private String marca;

	@JsonProperty("CONTENIDO")
	private String contenido;

	@JsonProperty("PESO")
	private String peso;

	@JsonProperty("PRECIO")
	private String precio;

	@JsonProperty("FOTO")
	private String foto;

	@JsonProperty("NOM_COMERCIAL")
	private String nombreComercial;

	@JsonProperty("DETALLES")
	private String detalles;
}
