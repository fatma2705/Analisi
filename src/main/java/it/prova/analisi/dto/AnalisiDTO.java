package it.prova.analisi.dto;

import java.time.LocalDate;

import it.prova.analisi.model.Analisi;
import it.prova.analisi.model.TipoAnalisi;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnalisiDTO {

	private Long id;
	@NotNull(message = "esitoPositivo.notnull")
	private Boolean esitoPositivo;
	@NotNull(message = "tipo.notnull")
	private TipoAnalisi tipo;
	@NotNull(message = "data.notnull")
	private LocalDate data;
	@NotNull(message = "paziente.notnull")
	private UtenteDTO paziente;

	public Analisi builsAnalisiModel() {
		return new Analisi(this.id, this.esitoPositivo, this.tipo, this.data, null);
	}

	public static AnalisiDTO buildAnalisiDTOFromModel(Analisi model) {
		return new AnalisiDTO(model.getId(), model.getEsitoPositivo(), model.getTipo(), model.getData(),
				UtenteDTO.buildUtenteDTOFromModel(model.getPaziente()));
	}

}
