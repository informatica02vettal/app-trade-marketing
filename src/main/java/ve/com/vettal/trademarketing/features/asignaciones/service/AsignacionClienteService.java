package ve.com.vettal.trademarketing.features.asignaciones.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.asignaciones.dto.AsignacionClienteRequestDto;
import ve.com.vettal.trademarketing.features.asignaciones.dto.AsignacionClienteResponseDto;
import ve.com.vettal.trademarketing.features.asignaciones.mapper.AsignacionClienteMapper;
import ve.com.vettal.trademarketing.features.asignaciones.model.AsignacionClienteModel;
import ve.com.vettal.trademarketing.features.asignaciones.repository.AsignacionClienteRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AsignacionClienteService {

	private final AsignacionClienteRepository asignacionClienteRepository;
	private final UsuarioRepository usuarioRepository;
	private final AsignacionClienteMapper asignacionClienteMapper;

	@Transactional(readOnly = true)
	public List<AsignacionClienteResponseDto> listarPorUsuario(Long usuarioId) {
		return asignacionClienteMapper.toDtoList(asignacionClienteRepository.findByUsuarioIdAndActivoTrue(usuarioId));
	}

	@Transactional(readOnly = true)
	public List<AsignacionClienteResponseDto> listarPorCliente(String erpClienteId) {
		return asignacionClienteMapper.toDtoList(asignacionClienteRepository.findByErpClienteIdAndActivoTrue(erpClienteId));
	}

	@Transactional
	public AsignacionClienteResponseDto asignar(AsignacionClienteRequestDto request) {
		UsuarioModel usuario = usuarioRepository.findById(request.getUsuarioId())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + request.getUsuarioId()));

		asignacionClienteRepository.findByErpClienteIdAndUsuarioId(request.getErpClienteId(), request.getUsuarioId())
				.ifPresent(a -> {
					throw new BusinessException("El cliente ya está asignado a ese usuario");
				});

		AsignacionClienteModel asignacion = AsignacionClienteModel.builder()
				.erpClienteId(request.getErpClienteId())
				.clienteNombre(request.getClienteNombre())
				.usuario(usuario)
				.region(request.getRegion())
				.build();

		return asignacionClienteMapper.toDto(asignacionClienteRepository.save(asignacion));
	}

	@Transactional
	public void desactivar(Long id) {
		AsignacionClienteModel asignacion = asignacionClienteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Asignación no encontrada con id " + id));
		asignacion.setActivo(false);
		asignacionClienteRepository.save(asignacion);
	}
}
