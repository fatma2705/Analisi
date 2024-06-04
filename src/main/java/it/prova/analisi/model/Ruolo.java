package it.prova.analisi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ruolo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ruolo {

	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	public static final String ROLE_CLASSIC_USER = "ROLE_CLASSIC_USER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "codice")
	private String codice;

	@Column(name = "descrizione")
	private String descrizione;

	public Ruolo(Long id) {
		this.id = id;
	}

	public Ruolo(String descrizione, String codice) {
		this.descrizione = descrizione;
		this.codice = codice;
	}

}
