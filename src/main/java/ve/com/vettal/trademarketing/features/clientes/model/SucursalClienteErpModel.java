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
 * Copia local sincronizada de una sucursal de cliente del ERP
 * (vettal-backend, GET /api/v1/clientes/sucursales). {@code erpId} es el
 * ID autoincrement original de {@code vettal_local_sucursales}, usado como
 * clave natural para el upsert.
 */
@Entity
@Table(name = "sucursales_cliente_erp")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SucursalClienteErpModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "erp_id", nullable = false, unique = true)
	private Integer erpId;

	@Column(name = "codigo_cliente", nullable = false, length = 10)
	private String codigoCliente;

	@Column(name = "id_vendedor", length = 38)
	private String idVendedor;

	@Column(name = "nombre_sucursal")
	private String nombreSucursal;

	@Column(name = "direccion_sucursal")
	private String direccionSucursal;

	@Column(name = "estado", length = 38)
	private String estado;

	@Column(name = "ciudad", length = 38)
	private String ciudad;

	@Column(name = "sincronizado_en", nullable = false)
	private LocalDateTime sincronizadoEn;
}
