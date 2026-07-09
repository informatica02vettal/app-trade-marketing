package ve.com.vettal.trademarketing.features.instalaciones.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ve.com.vettal.trademarketing.features.instalaciones.model.CategoriaInstalacion;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InstalacionRequestDto {

	private String erpClienteId;

	@NotBlank(message = "El nombre del cliente es obligatorio")
	private String clienteNombre;

	@NotBlank(message = "La marca es obligatoria")
	private String marca;

	@NotNull(message = "La categoría es obligatoria")
	private CategoriaInstalacion categoria;

	private String observaciones;

	private LocalDate fechaInstalacion;

	@NotEmpty(message = "Debe registrar al menos un ítem de material instalado")
	@Valid
	private List<InstalacionItemRequestDto> items;
}
