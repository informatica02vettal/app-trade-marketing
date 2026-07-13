package ve.com.vettal.trademarketing.features.clientes.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Copia local sincronizada de un cliente del ERP (vettal-backend,
 * GET /api/v1/clientes/listado). Se actualiza vía
 * POST /api/v1/clientes/sincronizar para poder manipular esta información
 * dentro de app-trade-marketing sin depender de una llamada en vivo.
 */
@Entity
@Table(name = "clientes_erp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteErpModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "codigo_cliente", nullable = false, length = 10, unique = true)
	private String codigoCliente;

	@Column(name = "rif", length = 20)
	private String rif;

	@Column(name = "nombre_fiscal", length = 200)
	private String nombreFiscal;

	@Column(name = "nombre_comercial", length = 200)
	private String nombreComercial;

	@Column(name = "direccion_fiscal", length = 500)
	private String direccionFiscal;

	@Column(name = "telefono_principal", length = 50)
	private String telefonoPrincipal;

	@Column(name = "celular", length = 50)
	private String celular;

	@Column(name = "email", length = 150)
	private String email;

	@Column(name = "estado", length = 100)
	private String estado;

	@Column(name = "ciudad", length = 100)
	private String ciudad;

	@Column(name = "municipio", length = 100)
	private String municipio;

	@Column(name = "fecha_creacion_erp")
	private LocalDateTime fechaCreacionErp;

	@Column(name = "sincronizado_en", nullable = false)
	private LocalDateTime sincronizadoEn;
}
