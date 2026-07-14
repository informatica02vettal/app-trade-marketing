package ve.com.vettal.trademarketing.features.archivos.constants;

import java.util.Set;

public final class ArchivoConstants {

	private ArchivoConstants() {
	}

	public static final String API_BASE_PATH_ARCHIVOS = "/api/v1/archivos";

	public static final String RESOURCE_HANDLER_PATTERN = "/files/**";

	public static final Set<String> CONTENT_TYPES_PERMITIDOS =
			Set.of("image/jpeg", "image/png", "image/webp", "video/mp4", "video/quicktime", "video/webm");

	public static final long TAMANIO_MAXIMO_BYTES = 50L * 1024 * 1024;
}
