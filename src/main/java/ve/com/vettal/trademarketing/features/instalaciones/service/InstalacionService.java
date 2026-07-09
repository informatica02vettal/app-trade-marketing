package ve.com.vettal.trademarketing.features.instalaciones.service;

import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionItemRequestDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionRequestDto;
import ve.com.vettal.trademarketing.features.instalaciones.dto.InstalacionResponseDto;
import ve.com.vettal.trademarketing.features.instalaciones.mapper.InstalacionMapper;
import ve.com.vettal.trademarketing.features.instalaciones.model.EstadoInstalacion;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionEjecucionModel;
import ve.com.vettal.trademarketing.features.instalaciones.model.InstalacionItemModel;
import ve.com.vettal.trademarketing.features.instalaciones.repository.InstalacionEjecucionRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Service
@RequiredArgsConstructor
@Transactional
public class InstalacionService {

	private final InstalacionEjecucionRepository instalacionEjecucionRepository;
	private final InstalacionMapper instalacionMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<InstalacionResponseDto> listar(Long usuarioId, String erpClienteId) {
		Long usuarioIdEfectivo = usuarioId;
		if (!authenticatedUserProvider.esAdminOSupervisor() && usuarioIdEfectivo == null) {
			usuarioIdEfectivo = authenticatedUserProvider.getUsuarioActual().getId();
		}

		List<InstalacionEjecucionModel> instalaciones;
		if (usuarioIdEfectivo != null && erpClienteId != null) {
			instalaciones = instalacionEjecucionRepository.findByUsuarioIdAndErpClienteId(usuarioIdEfectivo, erpClienteId);
		} else if (usuarioIdEfectivo != null) {
			instalaciones = instalacionEjecucionRepository.findByUsuarioId(usuarioIdEfectivo);
		} else if (erpClienteId != null) {
			instalaciones = instalacionEjecucionRepository.findByErpClienteId(erpClienteId);
		} else {
			instalaciones = instalacionEjecucionRepository.findAll();
		}

		return instalacionMapper.toDtoList(instalaciones);
	}

	@Transactional(readOnly = true)
	public InstalacionResponseDto obtener(Long id) {
		return instalacionMapper.toDto(buscarPorId(id));
	}

	public InstalacionResponseDto crear(InstalacionRequestDto request) {
		if (request.getItems() == null || request.getItems().isEmpty()) {
			throw new BusinessException("Debe registrar al menos un ítem de material instalado");
		}

		UsuarioModel usuarioActual = authenticatedUserProvider.getUsuarioActual();

		InstalacionEjecucionModel instalacion = InstalacionEjecucionModel.builder()
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.marca(request.getMarca())
				.categoria(request.getCategoria())
				.usuario(usuarioActual)
				.observaciones(request.getObservaciones())
				.fechaInstalacion(request.getFechaInstalacion() != null ? request.getFechaInstalacion() : LocalDate.now())
				.estado(EstadoInstalacion.INSTALADO)
				.build();

		for (InstalacionItemRequestDto itemRequest : request.getItems()) {
			InstalacionItemModel item = InstalacionItemModel.builder()
					.material(itemRequest.getMaterial())
					.fotoUrl(itemRequest.getFotoUrl())
					.build();
			instalacion.addItem(item);
		}

		return instalacionMapper.toDto(instalacionEjecucionRepository.save(instalacion));
	}

	private InstalacionEjecucionModel buscarPorId(Long id) {
		return instalacionEjecucionRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Instalación no encontrada con id " + id));
	}
}
