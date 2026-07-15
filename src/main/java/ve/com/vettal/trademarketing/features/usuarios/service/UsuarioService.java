package ve.com.vettal.trademarketing.features.usuarios.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioRequestDto;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.mapper.UsuarioMapper;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;
	private final PasswordEncoder passwordEncoder;

	@Transactional(readOnly = true)
	public List<UsuarioResponseDto> listar() {
		return usuarioMapper.toDtoList(usuarioRepository.findAll());
	}

	@Transactional(readOnly = true)
	public UsuarioResponseDto obtener(Long id) {
		return usuarioMapper.toDto(buscarPorId(id));
	}

	@Transactional
	public UsuarioResponseDto crear(UsuarioRequestDto request) {
		if (usuarioRepository.existsByEmail(request.getEmail())) {
			throw new BusinessException("Ya existe un usuario registrado con ese email");
		}
		if (request.getPassword() == null || request.getPassword().isBlank()) {
			throw new BusinessException("La contraseña es obligatoria para crear un usuario");
		}

		UsuarioModel usuario = UsuarioModel.builder()
				.nombre(request.getNombre())
				.email(request.getEmail())
				.passwordHash(passwordEncoder.encode(request.getPassword()))
				.region(request.getRegion())
				.rol(request.getRol())
				.activo(request.getActivo() == null || request.getActivo())
				.build();

		return usuarioMapper.toDto(usuarioRepository.save(usuario));
	}

	@Transactional
	public UsuarioResponseDto actualizar(Long id, UsuarioRequestDto request) {
		UsuarioModel usuario = buscarPorId(id);

		if (!usuario.getEmail().equalsIgnoreCase(request.getEmail())
				&& usuarioRepository.existsByEmail(request.getEmail())) {
			throw new BusinessException("Ya existe un usuario registrado con ese email");
		}

		usuario.setNombre(request.getNombre());
		usuario.setEmail(request.getEmail());
		usuario.setRegion(request.getRegion());
		usuario.setRol(request.getRol());
		if (request.getActivo() != null) {
			usuario.setActivo(request.getActivo());
		}
		if (request.getPassword() != null && !request.getPassword().isBlank()) {
			usuario.setPasswordHash(passwordEncoder.encode(request.getPassword()));
		}

		return usuarioMapper.toDto(usuarioRepository.save(usuario));
	}

	@Transactional
	public void eliminar(Long id) {
		UsuarioModel usuario = buscarPorId(id);
		usuario.setActivo(false);
		usuarioRepository.save(usuario);
	}

	// Solo cambia activo/inactivo, sin tocar ni revalidar el resto del perfil
	// (nombre, email, etc.) — a diferencia de actualizar(), que exige un DTO
	// completo y válido. Así, activar/desactivar nunca queda bloqueado por
	// datos antiguos del registro que no tienen que ver con este cambio.
	@Transactional
	public UsuarioResponseDto cambiarEstado(Long id, boolean activo) {
		UsuarioModel usuario = buscarPorId(id);
		usuario.setActivo(activo);
		return usuarioMapper.toDto(usuarioRepository.save(usuario));
	}

	private UsuarioModel buscarPorId(Long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con id " + id));
	}
}
