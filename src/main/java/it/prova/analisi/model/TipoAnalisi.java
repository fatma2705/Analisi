package it.prova.analisi.model;

public enum TipoAnalisi {
	EMATOLOGICA("Ematologica"), URINARIA("Urinaria"), RADIOLOGICA("Radiologica"), BIOCHIMICA("Biochimica"),
	CARDIOLOGICA("Cardiologica"), ENDOSCOPIA("Endoscopia"), GENETICA("Genetica"), MICROBIOLOGICA("Microbiologica"),
	ONCOLOGICA("Oncologica"), NEUROLOGICA("Neurologica");

	private final String descrizione;

	TipoAnalisi(String descrizione) {
		this.descrizione = descrizione;
	}

	public String getDescrizione() {
		return descrizione;
	}
}
