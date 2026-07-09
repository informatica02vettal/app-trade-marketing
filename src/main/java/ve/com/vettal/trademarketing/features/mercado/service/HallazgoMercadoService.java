package ve.com.vettal.trademarketing.features.mercado.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.mercado.constants.MercadoConstants;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.HallazgoMercadoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.mapper.HallazgoMercadoMapper;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMaterialModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoProductoModel;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.repository.HallazgoMercadoRepository;

@Service
@RequiredArgsConstructor
public class HallazgoMercadoService {

	private final HallazgoMercadoRepository hallazgoMercadoRepository;
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
		if (request.getTipo() == TipoHallazgo.CLIENTE_NO_REGISTRADO) {
			throw new BusinessException(MercadoConstants.MSG_TIPO_NO_PERMITIDO_EN_ENDPOINT_GENERICO);
		}

		if (request.getTipo() == TipoHallazgo.OPORTUNIDAD_MERCADO
				&& (request.getOportunidadTexto() == null || request.getOportunidadTexto().isBlank())) {
			throw new BusinessException(MercadoConstants.MSG_OPORTUNIDAD_TEXTO_OBLIGATORIO);
		}

		HallazgoMercadoModel hallazgo = HallazgoMercadoModel.builder()
				.tipo(request.getTipo())
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.categoriaProducto(request.getCategoriaProducto())
				.marca(request.getMarca())
				.marcaCompetencia(request.getMarcaCompetencia())
				.oportunidadTexto(request.getOportunidadTexto())
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
