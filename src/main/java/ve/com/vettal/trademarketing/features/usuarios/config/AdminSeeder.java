package ve.com.vettal.trademarketing.features.usuarios.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ve.com.vettal.trademarketing.features.usuarios.model.RolUsuario;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;
import ve.com.vettal.trademarketing.features.usuarios.repository.UsuarioRepository;

/**
 * Crea el usuario ADMIN inicial al arrancar la app, si todavía no existe.
 * Equivalente al script `seed:admin` del backend Node de referencia, pero
 * corriendo automáticamente dentro de Spring Boot — no requiere un comando
 * aparte ni acceso directo a la base de datos.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;

	@Value("${app.admin-seed.email}")
	private String adminEmail;

	@Value("${app.admin-seed.password}")
	private String adminPassword;

	@Value("${app.admin-seed.nombre}")
	private String adminNombre;

	@Override
	public void run(ApplicationArguments args) {
		if (usuarioRepository.existsByEmail(adminEmail)) {
			return;
		}

		UsuarioModel admin = UsuarioModel.builder()
				.nombre(adminNombre)
				.email(adminEmail)
				.passwordHash(passwordEncoder.encode(adminPassword))
				.rol(RolUsuario.ADMIN)
				.activo(true)
				.build();

		usuarioRepository.save(admin);
		log.warn("Usuario ADMIN inicial creado con email '{}'. Si ADMIN_PASSWORD no fue configurado en el .env, "
				+ "está usando la clave temporal por defecto — inicia sesión y cámbiala de inmediato.", adminEmail);
	}
}
