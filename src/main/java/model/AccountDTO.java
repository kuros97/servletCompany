package model;

import java.sql.Date;

public class AccountDTO {

    private int id;
    private int idAccount;
    private String username;
    private String password;
    private String email;
    private int idPermesso;
    private int idDipendente;
    
    private String nome;
    private String cognome;
    private Date dataDiNascita;
    private String cf;
    private Double stipendio;
    private boolean sesso;
    private Date dataDiAssunzione;
    private String luogoNascita;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdPermesso() {
        return idPermesso;
    }

    public void setIdPermesso(int idPermesso) {
        this.idPermesso = idPermesso;
    }

    public int getIdDipendente() {
        return idDipendente;
    }

    public void setIdDipendente(int idDipendente) {
        this.idDipendente = idDipendente;
    }
    
    
    //dipendente
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

	public Double getStipendio() {
		return stipendio;
	}

	public void setStipendio(Double stipendio) {
		this.stipendio = stipendio;
	}
	public String getLuogoNascita() {
		return luogoNascita;
	}

	public void setLuogoNascita(String luogoNascita) {
		this.luogoNascita = luogoNascita;
	}
	
	
    public AccountDTO(int id, String nome, String cognome, Date dataDiNascita, boolean sesso, Date dataDiAssunzione, String luogoNascita, String username, String email, String password) {
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.dataDiNascita = dataDiNascita;
        this.sesso = sesso;
        this.dataDiAssunzione = dataDiAssunzione;
        this.luogoNascita = luogoNascita;
        this.username = username;
        this.email = email;
        this.password = password;
        
    }

	public AccountDTO() {
		// TODO Auto-generated constructor stub
	}


}
