package ve.com.vettal.trademarketing.features.usuarios.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.constants.UsuarioConstants;
import ve.com.vettal.trademarketing.features.usuarios.dto.LoginRequestDto;
import ve.com.vettal.trademarketing.features.usuarios.dto.LoginResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.service.AuthService;

@RestController
@RequestMapping(UsuarioConstants.API_BASE_PATH_AUTH)
@RequiredArgsConstructor
@Tag(name = "Autenticación")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/login")
	public ResponseEntity<ApiResponseDto<LoginResponseDto>> login(@Valid @RequestBody LoginRequestDto request) {
		LoginResponseDto response = authService.login(request);
		return ResponseEntity.ok(ApiResponseDto.ok(response, "Inicio de sesión exitoso"));
	}
}
