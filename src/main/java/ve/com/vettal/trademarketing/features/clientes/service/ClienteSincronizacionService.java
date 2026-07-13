package ve.com.vettal.trademarketing.features.clientes.service;

import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ve.com.vettal.trademarketing.features.clientes.dto.ClienteLocalResponseDto;
import ve.com.vettal.trademarketing.features.clientes.dto.SincronizacionResultadoDto;
import ve.com.vettal.trademarketing.features.clientes.dto.SucursalLocalResponseDto;
import ve.com.vettal.trademarketing.features.clientes.mapper.ClienteErpMapper;
import ve.com.vettal.trademarketing.features.clientes.mapper.SucursalClienteErpMapper;
import ve.com.vettal.trademarketing.features.clientes.model.ClienteErpModel;
import ve.com.vettal.trademarketing.features.clientes.model.SucursalClienteErpModel;
import ve.com.vettal.trademarketing.features.clientes.repository.ClienteErpRepository;
import ve.com.vettal.trademarketing.features.clientes.repository.SucursalClienteErpRepository;
import ve.com.vettal.trademarketing.integration.clientesapi.client.ClientesApiClient;
import ve.com.vettal.trademarketing.integration.clientesapi.dto.ClienteListadoApiDto;
import ve.com.vettal.trademarketing.integration.clientesapi.dto.SucursalClienteApiDto;

/**
 * Trae clientes y sucursales del ERP (vía vettal-backend) y los guarda en
 * tablas propias ({@code clientes_erp}, {@code sucursales_cliente_erp}) para
 * poder manipularlos dentro de app-trade-marketing sin depender de una
 * llamada en vivo a la API externa en cada consulta. Upsert por clave
 * natural (codigo_cliente / erp_id): nunca borra filas existentes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteSincronizacionService {

	private final ClientesApiClient clientesApiClient;
	private final ClienteErpRepository clienteErpRepository;
	private final SucursalClienteErpRepository sucursalClienteErpRepository;
	private final ClienteErpMapper clienteErpMapper;
	private final SucursalClienteErpMapper sucursalClienteErpMapper;

	@Transactional
	public SincronizacionResultadoDto sincronizar() {
		LocalDateTime ahora = LocalDateTime.now();

		int clientesSincronizados = sincronizarClientes(ahora);
		int sucursalesSincronizadas = sincronizarSucursales(ahora);

		log.info("Sincronización con vettal-backend completada: {} clientes, {} sucursales",
				clientesSincronizados, sucursalesSincronizadas);

		return SincronizacionResultadoDto.builder()
				.clientesSincronizados(clientesSincronizados)
				.sucursalesSincronizadas(sucursalesSincronizadas)
				.sincronizadoEn(ahora)
				.build();
	}

	@Transactional(readOnly = true)
	public List<ClienteLocalResponseDto> listarLocales() {
		return clienteErpMapper.toDtoList(clienteErpRepository.findAll());
	}

	@Transactional(readOnly = true)
	public List<SucursalLocalResponseDto> listarSucursalesLocales(String codigoCliente) {
		return sucursalClienteErpMapper.toDtoList(sucursalClienteErpRepository.findByCodigoCliente(codigoCliente));
	}

	private int sincronizarClientes(LocalDateTime ahora) {
		List<ClienteListadoApiDto> clientes = clientesApiClient.listarTodos();
		for (ClienteListadoApiDto cliente : clientes) {
			ClienteErpModel model = clienteErpRepository.findByCodigoCliente(cliente.getCodigoCliente())
					.orElseGet(ClienteErpModel::new);
			model.setCodigoCliente(cliente.getCodigoCliente());
			model.setRif(cliente.getRif());
			model.setNombreFiscal(cliente.getNombreFiscal());
			model.setNombreComercial(cliente.getNombreComercial());
			model.setDireccionFiscal(cliente.getDireccionFiscal());
			model.setTelefonoPrincipal(cliente.getTelefonoPrincipal());
			model.setCelular(cliente.getCelular());
			model.setEmail(cliente.getEmail());
			model.setEstado(cliente.getEstado());
			model.setCiudad(cliente.getCiudad());
			model.setMunicipio(cliente.getMunicipio());
			model.setFechaCreacionErp(cliente.getCreacion());
			model.setSincronizadoEn(ahora);
			clienteErpRepository.save(model);
		}
		return clientes.size();
	}

	private int sincronizarSucursales(LocalDateTime ahora) {
		List<SucursalClienteApiDto> sucursales = clientesApiClient.sucursales(null);
		for (SucursalClienteApiDto sucursal : sucursales) {
			SucursalClienteErpModel model = sucursalClienteErpRepository.findByErpId(sucursal.getId())
					.orElseGet(SucursalClienteErpModel::new);
			model.setErpId(sucursal.getId());
			model.setCodigoCliente(sucursal.getIdCliente());
			model.setIdVendedor(sucursal.getIdVendedor());
			model.setNombreSucursal(sucursal.getNombreSucursal());
			model.setDireccionSucursal(sucursal.getDireccionSucursal());
			model.setEstado(sucursal.getEstado());
			model.setCiudad(sucursal.getCiudad());
			model.setSincronizadoEn(ahora);
			sucursalClienteErpRepository.save(model);
		}
		return sucursales.size();
	}
}
