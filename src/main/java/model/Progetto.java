package model;

import java.util.Objects;

public class Progetto {
	
	private int id;
	private String descrizione;
	private String nomeProgetto;
	private double costo;
	
	

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescrizione() {
		return descrizione;
	}
	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}
	public String getNomeProgetto() {
		return nomeProgetto;
	}
	public void setNomeProgetto(String nomeProgetto) {
		this.nomeProgetto = nomeProgetto;
	}
	
	@Override
	public String toString() {
		return "Progetto [id=" + id + ", descrizione=" + descrizione + ", nomeProgetto=" + nomeProgetto + ", costo" + costo;
	}
	@Override
	public int hashCode() {
		return Objects.hash(descrizione, id, nomeProgetto,costo);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Progetto other = (Progetto) obj;
		return Objects.equals(descrizione, other.descrizione) && id == other.id
				&& Objects.equals(nomeProgetto, other.nomeProgetto);
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	
	

}
