package model;

public class Working {
    private int idDipendente;
    private String nomeDipendente;
    private String cognomeDipendente;
    private int idProgetto;
    private String nomeProgetto;

    // Getters e Setters
    public int getIdDipendente() {
        return idDipendente;
    }

    public void setIdDipendente(int idDipendente) {
        this.idDipendente = idDipendente;
    }

    public String getNomeDipendente() {
        return nomeDipendente;
    }

    public void setNomeDipendente(String nomeDipendente) {
        this.nomeDipendente = nomeDipendente;
    }

    public int getIdProgetto() {
        return idProgetto;
    }

    public void setIdProgetto(int idProgetto) {
        this.idProgetto = idProgetto;
    }

    public String getNomeProgetto() {
        return nomeProgetto;
    }

    public void setNomeProgetto(String nomeProgetto) {
        this.nomeProgetto = nomeProgetto;
    }
    
    public String getCognomeDipendente() {
    	return cognomeDipendente;
    }

	public void setCognomeDipendente(String cognomeDipendente) {
		this.cognomeDipendente = cognomeDipendente;
	}
}
