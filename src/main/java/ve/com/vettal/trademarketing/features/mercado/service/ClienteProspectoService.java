package ve.com.vettal.trademarketing.features.mercado.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.security.AuthenticatedUserProvider;
import ve.com.vettal.trademarketing.features.mercado.dto.ClienteProspectoRequestDto;
import ve.com.vettal.trademarketing.features.mercado.dto.ClienteProspectoResponseDto;
import ve.com.vettal.trademarketing.features.mercado.mapper.ClienteProspectoMapper;
import ve.com.vettal.trademarketing.features.mercado.model.ClienteProspectoModel;
import ve.com.vettal.trademarketing.features.mercado.model.HallazgoMercadoModel;
import ve.com.vettal.trademarketing.features.mercado.model.TipoHallazgo;
import ve.com.vettal.trademarketing.features.mercado.repository.ClienteProspectoRepository;
import ve.com.vettal.trademarketing.features.mercado.repository.HallazgoMercadoRepository;
import ve.com.vettal.trademarketing.features.usuarios.model.UsuarioModel;

@Service
@RequiredArgsConstructor
public class ClienteProspectoService {

	private final ClienteProspectoRepository clienteProspectoRepository;
	private final HallazgoMercadoRepository hallazgoMercadoRepository;
	private final ClienteProspectoMapper clienteProspectoMapper;
	private final AuthenticatedUserProvider authenticatedUserProvider;

	@Transactional(readOnly = true)
	public List<ClienteProspectoResponseDto> listar() {
		return clienteProspectoMapper.toDtoList(clienteProspectoRepository.findAll());
	}

	@Transactional
	public ClienteProspectoResponseDto crear(ClienteProspectoRequestDto request) {
		UsuarioModel usuarioActual = authenticatedUserProvider.getUsuarioActual();

		HallazgoMercadoModel hallazgo = HallazgoMercadoModel.builder()
				.tipo(TipoHallazgo.CLIENTE_NO_REGISTRADO)
				.clienteNombre(request.getNombre())
				.usuario(usuarioActual)
				.build();
		hallazgo = hallazgoMercadoRepository.save(hallazgo);

		ClienteProspectoModel prospecto = ClienteProspectoModel.builder()
				.hallazgo(hallazgo)
				.nombre(request.getNombre())
				.rif(request.getRif())
				.whatsapp(request.getWhatsapp())
				.telefono(request.getTelefono())
				.gpsLat(request.getGpsLat())
				.gpsLng(request.getGpsLng())
				.fotoFachadaUrl(request.getFotoFachadaUrl())
				.fotoInteriorUrl(request.getFotoInteriorUrl())
				.marcasCompetencia(request.getMarcasCompetencia())
				.usuario(usuarioActual)
				.build();

		return clienteProspectoMapper.toDto(clienteProspectoRepository.save(prospecto));
	}
}
