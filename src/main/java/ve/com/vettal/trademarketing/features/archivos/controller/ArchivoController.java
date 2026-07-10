package ve.com.vettal.trademarketing.features.archivos.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ve.com.vettal.trademarketing.common.dto.ApiResponseDto;
import ve.com.vettal.trademarketing.features.archivos.constants.ArchivoConstants;
import ve.com.vettal.trademarketing.features.archivos.dto.ArchivoResponseDto;
import ve.com.vettal.trademarketing.features.archivos.service.ArchivoStorageService;

/**
 * Sube evidencia fotográfica (fachada, piso de ventas, marca, solicitudes,
 * hallazgos de mercado, etc.) y devuelve la URL que se usa tal cual en los
 * campos "url"/"fotoUrl"/"archivoUrl" ya existentes en el resto de la API.
 */
@RestController
@RequestMapping(ArchivoConstants.API_BASE_PATH_ARCHIVOS)
@RequiredArgsConstructor
@Tag(name = "Archivos")
@SecurityRequirement(name = "bearerAuth")
public class ArchivoController {

	private final ArchivoStorageService archivoStorageService;

	@PostMapping(consumes = "multipart/form-data")
	public ResponseEntity<ApiResponseDto<ArchivoResponseDto>> subir(@RequestPart("file") MultipartFile file) {
		String url = archivoStorageService.guardar(file);
		ArchivoResponseDto response = ArchivoResponseDto.builder().url(url).build();
		return ResponseEntity.status(201).body(ApiResponseDto.created(response, "Archivo subido"));
	}
}
