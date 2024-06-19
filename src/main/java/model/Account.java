package model;

import java.util.Objects;

public class Account {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private int idPermesso;
	private int idDipendente;
	
	public Account(String username, String password, String email, int idPermesso) {
		;
	}

	public Account() {
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "Account [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email + ", idPermesso=" + idPermesso + ", idDipendente ="+ idDipendente+"]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password, email, idPermesso);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		return id == other.id && Objects.equals(username, other.username)
				&& Objects.equals(password, other.password)
				&& Objects.equals(email, other.email) && idPermesso == other.idPermesso;
	}

	public int getIdDipendente() {
		return idDipendente;
	}

	public void setIdDipendente(int idDipendente) {
		this.idDipendente = idDipendente;
	}
	
    public Account(int id, String username, String email, String password, int idPermesso, int idDipendente) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.idPermesso = idPermesso;
        this.idDipendente = idDipendente;
    }
	
	
	

}
