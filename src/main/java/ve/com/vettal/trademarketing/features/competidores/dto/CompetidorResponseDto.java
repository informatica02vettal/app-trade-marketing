package ve.com.vettal.trademarketing.features.competidores.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetidorResponseDto {

	private Long id;
	private Long visitaId;
	private String nombreEmpresa;
	private String marcasRepresentadas;
	private String tipoProductosExhibidos;
	private String tamanoStand;
	private Integer cantidadPromotores;
	private Integer cantidadPersonalTecnico;
	private boolean poseeInflables;
	private boolean poseeToldos;
	private boolean poseePantallaLed;
	private boolean poseeExperienciasInteractivas;
	private boolean realizaDemostraciones;
	private boolean entregaMaterialPop;
	private boolean entregaMuestras;
	private boolean realizaRifasConcursos;
	private boolean realizaPromocionesEspeciales;
	private boolean cuentaActivaciones;
	private boolean poseeExhibidoresDiferenciadores;
	private boolean utilizaMascotasPublicitarias;
	private String observaciones;
	private List<String> fotosStand;
	private List<String> fotosMaterialPublicitario;
}
