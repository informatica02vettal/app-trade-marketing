package ve.com.vettal.trademarketing.features.bancoimagenes.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.bancoimagenes.model.TipoArchivo;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArteMarcaRequestDto {

	@NotBlank(message = "La marca es obligatoria")
	private String marca;

	@NotBlank(message = "El nombre es obligatorio")
	private String nombre;

	@NotBlank(message = "La URL del archivo es obligatoria")
	private String archivoUrl;

	@NotNull(message = "El tipo de archivo es obligatorio")
	private TipoArchivo tipoArchivo;
}
