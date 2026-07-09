package ve.com.vettal.trademarketing.features.usuarios.constants;

public final class UsuarioConstants {

	private UsuarioConstants() {
	}

	public static final String API_BASE_PATH_AUTH = "/api/v1/auth";
	public static final String API_BASE_PATH_USUARIOS = "/api/v1/usuarios";

	public static final String ROLES_ADMIN = "hasRole('ADMIN')";
	public static final String ROLES_ADMIN_SUPERVISOR = "hasAnyRole('ADMIN','SUPERVISOR')";
}
