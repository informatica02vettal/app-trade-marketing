package ve.com.vettal.trademarketing.features.competidores.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompetidorRequestDto {

	@NotNull(message = "La visita es obligatoria")
	private Long visitaId;

	@NotBlank(message = "El nombre de la empresa es obligatorio")
	private String nombreEmpresa;

	private String marcasRepresentadas;
	private String tipoProductosExhibidos;
	private String tamanoStand;
	private Integer cantidadPromotores;
	private Integer cantidadPersonalTecnico;
	private Boolean poseeInflables;
	private Boolean poseeToldos;
	private Boolean poseePantallaLed;
	private Boolean poseeExperienciasInteractivas;
	private Boolean realizaDemostraciones;
	private Boolean entregaMaterialPop;
	private Boolean entregaMuestras;
	private Boolean realizaRifasConcursos;
	private Boolean realizaPromocionesEspeciales;
	private Boolean cuentaActivaciones;
	private Boolean poseeExhibidoresDiferenciadores;
	private Boolean utilizaMascotasPublicitarias;
	private String observaciones;
	private List<String> fotosStand;
	private List<String> fotosMaterialPublicitario;
}
