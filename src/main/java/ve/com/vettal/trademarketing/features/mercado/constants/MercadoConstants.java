package ve.com.vettal.trademarketing.features.mercado.constants;

public final class MercadoConstants {

	private MercadoConstants() {
	}

	public static final String API_BASE_PATH_MERCADO = "/api/v1/mercado";

	public static final String PATH_CLIENTES_PROSPECTO = "/clientes-prospecto";

	public static final String ROLES_ADMIN_SUPERVISOR = "hasAnyRole('ADMIN','SUPERVISOR')";

	public static final String MSG_TIPO_NO_PERMITIDO_EN_ENDPOINT_GENERICO =
			"Use POST /api/v1/mercado/clientes-prospecto para el flujo de cliente no registrado";

	public static final String MSG_OPORTUNIDAD_TEXTO_OBLIGATORIO =
			"El texto de oportunidad es obligatorio para el tipo OPORTUNIDAD_MERCADO";
}
