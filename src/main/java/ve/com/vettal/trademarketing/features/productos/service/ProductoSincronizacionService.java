package ve.com.vettal.trademarketing.features.productos.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.features.productos.dto.ProductoLocalResponseDto;
import ve.com.vettal.trademarketing.features.productos.dto.SincronizacionProductosResultadoDto;
import ve.com.vettal.trademarketing.features.productos.mapper.ProductoErpMapper;
import ve.com.vettal.trademarketing.features.productos.model.ProductoErpModel;
import ve.com.vettal.trademarketing.features.productos.repository.ProductoErpRepository;
import ve.com.vettal.trademarketing.integration.productosapi.client.ProductosApiClient;
import ve.com.vettal.trademarketing.integration.productosapi.dto.ProductoApiDto;

/**
 * Trae el catálogo de productos del ERP y lo guarda en una tabla propia
 * ({@code productos_erp}) para poder manipularlo dentro de
 * app-trade-marketing sin depender de una llamada en vivo a la API externa
 * en cada consulta. Upsert por clave natural (codigo): nunca borra filas
 * existentes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductoSincronizacionService {

	private final ProductosApiClient productosApiClient;
	private final ProductoErpRepository productoErpRepository;
	private final ProductoErpMapper productoErpMapper;

	@Transactional
	public SincronizacionProductosResultadoDto sincronizar() {
		LocalDateTime ahora = LocalDateTime.now();
		List<ProductoApiDto> productos = productosApiClient.listarTodos();

		int sincronizados = 0;
		for (ProductoApiDto producto : productos) {
			if (producto.getCodigo() == null || producto.getCodigo().isBlank()) {
				continue;
			}

			ProductoErpModel model = productoErpRepository.findByCodigo(producto.getCodigo())
					.orElseGet(ProductoErpModel::new);
			model.setCodigo(producto.getCodigo());
			model.setProducto(producto.getProducto());
			model.setLinea(producto.getLinea());
			model.setSubcategoria(producto.getSubcategoria());
			model.setMarca(producto.getMarca());
			model.setContenido(producto.getContenido());
			model.setPeso(parseDecimal(producto.getPeso()));
			model.setPrecio(parseDecimal(producto.getPrecio()));
			model.setFotoUrl(producto.getFoto());
			model.setNombreComercial(producto.getNombreComercial());
			model.setDetalles(producto.getDetalles());
			model.setSincronizadoEn(ahora);
			productoErpRepository.save(model);
			sincronizados++;
		}

		log.info("Sincronización de productos con el ERP completada: {} productos", sincronizados);

		return SincronizacionProductosResultadoDto.builder()
				.productosSincronizados(sincronizados)
				.sincronizadoEn(ahora)
				.build();
	}

	@Transactional(readOnly = true)
	public List<ProductoLocalResponseDto> listarLocales() {
		return productoErpMapper.toDtoList(productoErpRepository.findAll());
	}

	private BigDecimal parseDecimal(String valor) {
		if (valor == null || valor.isBlank()) {
			return null;
		}
		try {
			return new BigDecimal(valor.trim());
		} catch (NumberFormatException ex) {
			return null;
		}
	}
}
