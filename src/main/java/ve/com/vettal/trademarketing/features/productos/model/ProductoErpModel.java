package ve.com.vettal.trademarketing.features.productos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copia local sincronizada de un producto del catálogo del ERP
 * (API externa, tabla {@code lista_prod_pw}). Se actualiza vía
 * POST /api/v1/productos/sincronizar para poder manipular esta información
 * dentro de app-trade-marketing sin depender de una llamada en vivo.
 */
@Entity
@Table(name = "productos_erp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoErpModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "codigo", nullable = false, length = 30, unique = true)
	private String codigo;

	@Column(name = "producto", length = 500)
	private String producto;

	@Column(name = "linea", length = 200)
	private String linea;

	@Column(name = "subcategoria", length = 200)
	private String subcategoria;

	@Column(name = "marca", length = 100)
	private String marca;

	@Column(name = "contenido", length = 50)
	private String contenido;

	@Column(name = "peso", precision = 12, scale = 4)
	private BigDecimal peso;

	@Column(name = "precio", precision = 14, scale = 4)
	private BigDecimal precio;

	@Column(name = "foto_url", length = 500)
	private String fotoUrl;

	@Column(name = "nombre_comercial", length = 500)
	private String nombreComercial;

	@Lob
	@Column(name = "detalles", columnDefinition = "TEXT")
	private String detalles;

	@Column(name = "sincronizado_en", nullable = false)
	private LocalDateTime sincronizadoEn;
}
