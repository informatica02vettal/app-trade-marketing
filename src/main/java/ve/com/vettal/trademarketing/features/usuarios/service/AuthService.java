package ve.com.vettal.trademarketing.features.usuarios.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.common.security.jwt.JwtService;
import ve.com.vettal.trademarketing.features.usuarios.dto.LoginRequestDto;
import ve.com.vettal.trademarketing.features.usuarios.dto.LoginResponseDto;
import ve.com.vettal.trademarketing.features.usuarios.mapper.UsuarioMapper;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final UsuarioRepository usuarioRepository;
	private final UsuarioMapper usuarioMapper;

	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto request) {
		UserDetails userDetails = (UserDetails) authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())).getPrincipal();

		String token = jwtService.generateToken(userDetails);

		UsuarioModel usuario = usuarioRepository.findByEmail(request.getEmail())
				.orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

		return LoginResponseDto.builder()
				.token(token)
				.usuario(usuarioMapper.toDto(usuario))
				.build();
	}
}
