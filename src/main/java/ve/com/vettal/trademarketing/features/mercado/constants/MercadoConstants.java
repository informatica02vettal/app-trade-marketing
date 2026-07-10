package ve.com.vettal.trademarketing.features.mercado.constants;

public final class MercadoConstants {

	private MercadoConstants() {
	}

	public static final String API_BASE_PATH_MERCADO = "/api/v1/mercado";

	public static final String ROLES_ADMIN_SUPERVISOR = "hasAnyRole('ADMIN','SUPERVISOR')";

	public static final String MSG_OBSERVACION_TEXTO_OBLIGATORIO =
			"El texto de observación es obligatorio para el tipo OBSERVACION_MERCADO";
}
