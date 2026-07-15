package ve.com.vettal.trademarketing.features.usuarios.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.constants.UsuarioConstants;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioEstadoRequestDto;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioRequestDto;
import ve.com.vettal.trademarketing.features.usuarios.dto.UsuarioResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.service.UsuarioService;

@RestController
@RequestMapping(UsuarioConstants.API_BASE_PATH_USUARIOS)
@RequiredArgsConstructor
@Tag(name = "Usuarios")
@SecurityRequirement(name = "bearerAuth")
public class UsuarioController {

	private final UsuarioService usuarioService;

	@GetMapping
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<List<UsuarioResponseDto>>> listar() {
		return ResponseEntity.ok(ApiResponseDto.ok(usuarioService.listar(), "Usuarios obtenidos"));
	}

	@GetMapping("/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN_SUPERVISOR)
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> obtener(@PathVariable Long id) {
		return ResponseEntity.ok(ApiResponseDto.ok(usuarioService.obtener(id), "Usuario obtenido"));
	}

	@PostMapping
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN)
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> crear(@Valid @RequestBody UsuarioRequestDto request) {
		UsuarioResponseDto usuario = usuarioService.crear(request);
		return ResponseEntity.status(201).body(ApiResponseDto.created(usuario, "Usuario creado"));
	}

	@PutMapping("/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN)
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> actualizar(
			@PathVariable Long id, @Valid @RequestBody UsuarioRequestDto request) {
		return ResponseEntity.ok(ApiResponseDto.ok(usuarioService.actualizar(id, request), "Usuario actualizado"));
	}

	@DeleteMapping("/{id}")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN)
	public ResponseEntity<ApiResponseDto<Void>> eliminar(@PathVariable Long id) {
		usuarioService.eliminar(id);
		return ResponseEntity.ok(ApiResponseDto.ok(null, "Usuario desactivado"));
	}

	@PatchMapping("/{id}/estado")
	@PreAuthorize(UsuarioConstants.ROLES_ADMIN)
	public ResponseEntity<ApiResponseDto<UsuarioResponseDto>> cambiarEstado(
			@PathVariable Long id, @Valid @RequestBody UsuarioEstadoRequestDto request) {
		UsuarioResponseDto usuario = usuarioService.cambiarEstado(id, request.getActivo());
		String mensaje = request.getActivo() ? "Usuario activado" : "Usuario desactivado";
		return ResponseEntity.ok(ApiResponseDto.ok(usuario, mensaje));
	}
}
