package ve.com.vettal.trademarketing.features.mercado.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Entity
@Table(name = "hallazgos_mercado")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HallazgoMercadoModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "tipo", nullable = false, length = 40)
	private TipoHallazgo tipo;

	@Column(name = "erp_cliente_id", length = 10)
	private String erpClienteId;

	@Column(name = "cliente_nombre", length = 200)
	private String clienteNombre;

	@Column(name = "categoria_producto", length = 50)
	private String categoriaProducto;

	@Column(name = "marca", length = 100)
	private String marca;

	@Column(name = "marca_competencia", length = 100)
	private String marcaCompetencia;

	@Column(name = "oportunidad_texto", columnDefinition = "TEXT")
	private String oportunidadTexto;

	@Column(name = "detalle", columnDefinition = "TEXT")
	private String detalle;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", nullable = false)
	private UsuarioModel usuario;

	@Builder.Default
	@OneToMany(mappedBy = "hallazgo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HallazgoProductoModel> productos = new ArrayList<>();

	@Builder.Default
	@OneToMany(mappedBy = "hallazgo", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<HallazgoMaterialModel> materiales = new ArrayList<>();

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}

	public void addProducto(HallazgoProductoModel producto) {
		producto.setHallazgo(this);
		this.productos.add(producto);
	}

	public void addMaterial(HallazgoMaterialModel material) {
		material.setHallazgo(this);
		this.materiales.add(material);
	}
}
