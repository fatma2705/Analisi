package it.prova.analisi.web.api;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import it.prova.analisi.dto.AnalisiDTO;
import it.prova.analisi.model.Analisi;
import it.prova.analisi.service.AnalisiService;
import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/analisi")
public class AnalisiController {
	

	@Autowired
	private AnalisiService analisiService;

	@GetMapping("/listAll")
	public List<AnalisiDTO> listAll() {
		return analisiService.listAll().stream().map(analisi -> {
			return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
		}).collect(Collectors.toList());

	}

	@GetMapping
	public List<AnalisiDTO> listMine(Principal principal) {
		return analisiService.listMine(principal.getName()).stream().map(analisi -> {
			return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
		}).collect(Collectors.toList());
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public AnalisiDTO inserisciNuovo(@Valid @RequestBody AnalisiDTO input, Principal principal) {
		Analisi analisi = input.builsAnalisiModel();
		analisi = analisiService.inserisciNuova(analisi, principal.getName());
		return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable(name = "id", required = true) Long id, Principal principal) {
		analisiService.delete(id, principal.getName());
	}

	@GetMapping("/{id}")
	public AnalisiDTO getById(@PathVariable(name = "id", required = true) Long id, Principal principal) {
		return AnalisiDTO.buildAnalisiDTOFromModel(analisiService.findById(id, principal.getName()));
	}

	@PutMapping
	public AnalisiDTO aggiorna(@Valid @RequestBody AnalisiDTO input, Principal principal) {
		Analisi aggiornata = input.builsAnalisiModel();
		aggiornata = analisiService.aggiorna(aggiornata, principal.getName());
		return AnalisiDTO.buildAnalisiDTOFromModel(aggiornata);
	}

	@PostMapping("/search")
	public List<AnalisiDTO> searchAnalisi(@RequestBody AnalisiDTO example, Principal principal) {
		System.out.println("api search" + principal.getName());
		Analisi analisiModel = example.builsAnalisiModel();
		return analisiService.findByExample(analisiModel, principal.getName()).stream().map(analisi -> {
			return AnalisiDTO.buildAnalisiDTOFromModel(analisi);
		}).collect(Collectors.toList());
	}

}
