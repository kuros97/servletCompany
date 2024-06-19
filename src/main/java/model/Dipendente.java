package model;

import java.sql.Date;
import java.util.Objects;

public class Dipendente {
	
	private int id;
	private String nome;
	private String cognome;
	private Date dataDiNascita;
	private String cf;
	private double stipendio;
	private boolean sesso;
	private Date dataDiAssunzione;
	private int idAccount;
	private String luogoNascita;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public Date getDataDiNascita() {
		return dataDiNascita;
	}
	public void setDataDiNascita(Date dataDiNascita) {
		this.dataDiNascita = dataDiNascita;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public double getStipendio() {
		return stipendio;
	}
	public void setStipendio(double stipendio) {
		this.stipendio = stipendio;
	}
	public boolean isSesso() {
		return sesso;
	}
	public void setSesso(boolean sesso) {
		this.sesso = sesso;
	}
	public Date getDataDiAssunzione() {
		return dataDiAssunzione;
	}
	public void setDataDiAssunzione(Date dataDiAssunzione) {
		this.dataDiAssunzione = dataDiAssunzione;
	}
	public int getIdAccount() {
		return idAccount;
	}
	public void setIdAccount(int idAccount) {
		this.idAccount = idAccount;
	}

	@Override
	public String toString() {
		return "Dipendente [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", dataDiNascita=" + dataDiNascita
				+ ", cf=" + cf + ", stipendio=" + stipendio + ", sesso=" + sesso + ", dataDiAssunzione="
				+ dataDiAssunzione + ", idAccount=" + idAccount + ", luogoNascita=" + luogoNascita + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(cf, cognome, dataDiAssunzione, dataDiNascita, id, idAccount, nome, sesso, stipendio,luogoNascita);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dipendente other = (Dipendente) obj;
		return Objects.equals(cf, other.cf) && Objects.equals(cognome, other.cognome)
				&& Objects.equals(dataDiAssunzione, other.dataDiAssunzione)
				&& Objects.equals(dataDiNascita, other.dataDiNascita) && id == other.id && idAccount == other.idAccount
				&& Objects.equals(nome, other.nome) && sesso == other.sesso
				&& Double.doubleToLongBits(stipendio) == Double.doubleToLongBits(other.stipendio);
	}

	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}

	
	
	
	
	
	

}
