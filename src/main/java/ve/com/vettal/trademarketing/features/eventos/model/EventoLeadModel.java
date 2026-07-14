package ve.com.vettal.trademarketing.features.eventos.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "evento_leads")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EventoLeadModel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "evento_id", nullable = false)
	private EventoVisitaModel evento;

	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;

	@Column(name = "empresa", length = 200)
	private String empresa;

	@Column(name = "cargo", length = 150)
	private String cargo;

	@Column(name = "telefono", length = 50)
	private String telefono;

	@Column(name = "correo", length = 150)
	private String correo;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;

	@PrePersist
	protected void onCreate() {
		this.createdAt = LocalDateTime.now();
	}
}
