package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Connessione;
import model.Dipendente;

public class DipendenteRepository {
	
	public int inserimentoDipendente(Dipendente d) {
		int num=0;
		Connection conn = Connessione.getConnection();
		String sql = "INSERT INTO dipendenti(nome,cognome,data_di_nascita,codice_fiscale,stipendio,sesso,data_di_assunzione) VALUES(?,?,?,?,?,?,?)";
				
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, d.getNome());
			ps.setString(2, d.getCognome());
			ps.setDate(3, d.getDataDiNascita());
			ps.setString(4, d.getCf());
			ps.setDouble(5, d.getStipendio());
			ps.setBoolean(6, d.isSesso());
			ps.setDate(7, d.getDataDiAssunzione());
			
			
			num=ps.executeUpdate(); // DML
			
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	//voglio tutti i dipendenti
	public ResultSet getDipendente() {

		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM dipendenti";
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery(); //DQL


		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rs;
	}
	//voglio selezionare un unico dipendente (mi serve per la modifica per ora)
	 public ResultSet getDipendenteByCf(String cf) {
	        Connection conn = Connessione.getConnection();
	        String sql = "SELECT * FROM dipendenti WHERE codice_fiscale = ?";
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, cf);
	            rs = ps.executeQuery(); // DQL
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return rs;
	    }
	 
	 public ResultSet getDipendenteById(int id) {
	        Connection conn = Connessione.getConnection();
	        String sql = "SELECT * FROM dipendenti WHERE id = ?";
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1, id);
	            rs = ps.executeQuery(); // DQL
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return rs;
	    }
	
	public int eliminaDipendente(String cf) {
	    int result = 0;
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = Connessione.getConnection();
	        String sql = "DELETE FROM dipendenti WHERE codice_fiscale = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setString(1, cf);
	        result = ps.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return result;
	}
	
	  public int aggiornaDipendente(Dipendente d) {
	        int num = 0;
	        Connection conn = Connessione.getConnection();
	        String sql = "UPDATE dipendenti SET nome=?, cognome=?, data_di_nascita=?, stipendio=?, sesso=?, data_di_assunzione=? WHERE codice_fiscale=?";

	        PreparedStatement ps = null;

	        try {
	            ps = conn.prepareStatement(sql);

	            ps.setString(1, d.getNome());
	            ps.setString(2, d.getCognome());
	            ps.setDate(3, d.getDataDiNascita());
	            ps.setDouble(4, d.getStipendio());
	            ps.setBoolean(5, d.isSesso());
	            ps.setDate(6, d.getDataDiAssunzione());
	            ps.setString(7, d.getCf());

	            num = ps.executeUpdate(); // DML

	            ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return num;
	    }
	  
	  public List<Dipendente> getAllDipendenti() {
	        Connection conn = Connessione.getConnection();
	        List<Dipendente> dipendenti = new ArrayList<>();
	        String sql = "SELECT * FROM dipendenti";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Dipendente dip = new Dipendente();
	                dip.setId(rs.getInt("id"));
	                dip.setNome(rs.getString("nome"));
	                dip.setCognome(rs.getString("cognome"));
	                dip.setDataDiNascita(rs.getDate("data_di_nascita"));
	                dip.setCf(rs.getString("codice_fiscale"));
	                dip.setStipendio(rs.getDouble("stipendio"));
	                dip.setSesso(rs.getBoolean("sesso"));
	                dip.setDataDiAssunzione(rs.getDate("data_di_assunzione"));
	                dipendenti.add(dip);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            Connessione.closeConnection(conn);
	        }

	        return dipendenti;
	    }


	
}
	
  