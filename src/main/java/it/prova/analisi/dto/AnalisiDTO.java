package it.prova.analisi.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

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
@JsonInclude(value = Include.NON_NULL)
public class AnalisiDTO {

	private Long id;
	@NotNull(message = "esitoPositivo.notnull")
	private Boolean esitoPositivo;
	@NotNull(message = "tipo.notnull")
	private TipoAnalisi tipo;
	@NotNull(message = "data.notnull")
	private LocalDate data;
	private UtenteDTO paziente;

	public Analisi builsAnalisiModel() {
		return Analisi.builder().id(this.id).esitoPositivo(esitoPositivo).tipo(tipo).data(data)
				.paziente(paziente == null ? null : paziente.buildUtenteModel(false)).build();
	}

	public static AnalisiDTO buildAnalisiDTOFromModel(Analisi model) {
		return new AnalisiDTO(model.getId(), model.getEsitoPositivo(), model.getTipo(), model.getData(),
				UtenteDTO.builder().id(model.getPaziente().getId()).build());
	}

}
