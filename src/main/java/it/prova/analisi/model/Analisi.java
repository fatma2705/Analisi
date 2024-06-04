package it.prova.analisi.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "analisi")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Analisi {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "esito_positivo")
	private Boolean esitoPositivo;

	@Column(name = "tipo")
	private TipoAnalisi tipo;

	@Column(name = "data")
	private LocalDate data;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "paziente_id", nullable = true)
	private Utente paziente;

	public Analisi(Boolean esitoPositivo, TipoAnalisi tipo, LocalDate data) {
		this.esitoPositivo = esitoPositivo;
		this.tipo = tipo;
		this.data = data;
	}

}
