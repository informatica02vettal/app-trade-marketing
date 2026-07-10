package ve.com.vettal.trademarketing.features.archivos.service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import ve.com.vettal.trademarketing.common.exception.BusinessException;
import ve.com.vettal.trademarketing.features.archivos.constants.ArchivoConstants;

@Slf4j
@Service
public class ArchivoStorageService {

	private final Path basePath;

	public ArchivoStorageService(@Value("${app.storage.base-path}") String basePathConfig) {
		this.basePath = Path.of(basePathConfig).toAbsolutePath().normalize();
		try {
			Files.createDirectories(this.basePath);
		} catch (IOException e) {
			throw new UncheckedIOException("No se pudo crear el directorio de almacenamiento: " + this.basePath, e);
		}
	}

	public String guardar(MultipartFile file) {
		if (file == null || file.isEmpty()) {
			throw new BusinessException("El archivo es obligatorio");
		}
		if (!ArchivoConstants.CONTENT_TYPES_PERMITIDOS.contains(file.getContentType())) {
			throw new BusinessException("Tipo de archivo no permitido: " + file.getContentType());
		}
		if (file.getSize() > ArchivoConstants.TAMANIO_MAXIMO_BYTES) {
			throw new BusinessException("El archivo excede el tamaño máximo permitido (10MB)");
		}

		String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
		String nombreArchivo = UUID.randomUUID() + (extension != null ? "." + extension.toLowerCase() : "");
		Path destino = basePath.resolve(nombreArchivo).normalize();

		try {
			Files.createDirectories(basePath);
			file.transferTo(destino);
		} catch (IOException e) {
			log.error("Error guardando archivo {}", nombreArchivo, e);
			throw new UncheckedIOException("No se pudo guardar el archivo", e);
		}

		return "/files/" + nombreArchivo;
	}
}
