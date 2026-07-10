package ve.com.vettal.trademarketing.features.mercado.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.catalogos.model.CategoriaProductoMercadoModel;
import ve.com.vettal.trademarketing.features.catalogos.repository.CategoriaProductoMercadoRepository;
import ve.com.vettal.trademarketing.features.mercado.constants.MercadoConstants;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.mapper.HallazgoMercadoMapper;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMaterialModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoProductoModel;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.repository.HallazgoMercadoRepository;
import ve.com.vettal.trademarketing.features.visitas.model.EstadoVisita;
import ve.com.vettal.trademarketing.features.visitas.model.VisitaModel;
import ve.com.vettal.trademarketing.features.visitas.repository.VisitaRepository;

@Service
@RequiredArgsConstructor
public class HallazgoMercadoService {

	private final HallazgoMercadoRepository hallazgoMercadoRepository;
	private final VisitaRepository visitaRepository;
	private final CategoriaProductoMercadoRepository categoriaProductoMercadoRepository;
	private final HallazgoMercadoMapper hallazgoMercadoMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<HallazgoMercadoResponseDto> listar(TipoHallazgo tipo, Long usuarioId) {
		List<HallazgoMercadoModel> hallazgos;

		if (tipo != null && usuarioId != null) {
			hallazgos = hallazgoMercadoRepository.findByTipoAndUsuarioId(tipo, usuarioId);
		} else if (tipo != null) {
			hallazgos = hallazgoMercadoRepository.findByTipo(tipo);
		} else if (usuarioId != null) {
			hallazgos = hallazgoMercadoRepository.findByUsuarioId(usuarioId);
		} else {
			hallazgos = hallazgoMercadoRepository.findAll();
		}

		return hallazgoMercadoMapper.toDtoList(hallazgos);
	}

	@Transactional
	public HallazgoMercadoResponseDto crear(HallazgoMercadoRequestDto request) {
		VisitaModel visita = visitaRepository.findById(request.getVisitaId())
				.orElseThrow(() -> new ResourceNotFoundException("Visita no encontrada con id " + request.getVisitaId()));
		if (visita.getEstado() != EstadoVisita.EN_CURSO) {
			throw new BusinessException("Solo se pueden registrar hallazgos de mercado en visitas en curso");
		}

		if (request.getTipo() == TipoHallazgo.OBSERVACION_MERCADO
				&& (request.getObservacionTexto() == null || request.getObservacionTexto().isBlank())) {
			throw new BusinessException(MercadoConstants.MSG_OBSERVACION_TEXTO_OBLIGATORIO);
		}

		CategoriaProductoMercadoModel categoriaProducto = null;
		if (request.getCategoriaProductoId() != null) {
			categoriaProducto = categoriaProductoMercadoRepository.findById(request.getCategoriaProductoId())
					.orElseThrow(() -> new ResourceNotFoundException(
							"Categoría de producto no encontrada con id " + request.getCategoriaProductoId()));
		}

		HallazgoMercadoModel hallazgo = HallazgoMercadoModel.builder()
				.visita(visita)
				.tipo(request.getTipo())
				.categoriaProducto(categoriaProducto)
				.marcaCompetencia(request.getMarcaCompetencia())
				.observacionTexto(request.getObservacionTexto())
				.detalle(request.getDetalle())
				.usuario(authenticatedUserProvider.getUsuarioActual())
				.build();

		if (request.getProductos() != null) {
			request.getProductos().forEach(producto -> hallazgo.addProducto(
					HallazgoProductoModel.builder()
							.marca(producto.getMarca())
							.modelo(producto.getModelo())
							.precio(producto.getPrecio())
							.fotoUrl(producto.getFotoUrl())
							.build()));
		}

		if (request.getMateriales() != null) {
			request.getMateriales().forEach(material -> hallazgo.addMaterial(
					HallazgoMaterialModel.builder()
							.material(material.getMaterial())
							.marca(material.getMarca())
							.fotoUrl(material.getFotoUrl())
							.build()));
		}

		return hallazgoMercadoMapper.toDto(hallazgoMercadoRepository.save(hallazgo));
	}
}
