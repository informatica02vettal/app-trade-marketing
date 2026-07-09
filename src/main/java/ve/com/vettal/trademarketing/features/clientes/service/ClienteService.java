package ve.com.vettal.trademarketing.features.clientes.service;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.common.exception.ResourceNotFoundException;
import ve.com.vettal.trademarketing.features.asignaciones.model.AsignacionClienteModel;
import ve.com.vettal.trademarketing.features.asignaciones.repository.AsignacionClienteRepository;
import ve.com.vettal.trademarketing.features.clientes.dto.ClienteResponseDto;
import ve.com.vettal.trademarketing.integration.clientesapi.client.ClientesApiClient;
import ve.com.vettal.trademarketing.integration.clientesapi.dto.ClienteApiDto;

@Service
@RequiredArgsConstructor
public class ClienteService {

	private final ClientesApiClient clientesApiClient;
	private final AsignacionClienteRepository asignacionClienteRepository;

	@Transactional(readOnly = true)
	public List<ClienteResponseDto> buscar(String id, String nombre) {
		return clientesApiClient.buscar(id, nombre).stream()
				.map(this::toResponseDto)
				.toList();
	}

	@Transactional(readOnly = true)
	public ClienteResponseDto obtener(String id) {
		return clientesApiClient.buscar(id, null).stream()
				.filter(c -> id.equals(c.getId()))
				.findFirst()
				.map(this::toResponseDto)
				.orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id " + id));
	}

	private ClienteResponseDto toResponseDto(ClienteApiDto cliente) {
		Optional<AsignacionClienteModel> asignacion = asignacionClienteRepository
				.findByErpClienteIdAndActivoTrue(cliente.getId())
				.stream()
				.findFirst();

		return ClienteResponseDto.builder()
				.id(cliente.getId())
				.nombre(cliente.getNombre())
				.mercaderistaAsignadoId(asignacion.map(a -> a.getUsuario().getId()).orElse(null))
				.mercaderistaAsignadoNombre(asignacion.map(a -> a.getUsuario().getNombre()).orElse(null))
				.build();
	}
}
