package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Connessione;
import model.Progetto;

public class ProgettoRepository {
	
	public int inserimentoProgetto(Progetto p) {
		int num=0;
		Connection conn = Connessione.getConnection();
		String sql = "INSERT INTO progetti(nomeProgetto, descrizione) VALUES(?,?)";
				
		PreparedStatement ps = null;
		
		try {
			ps = conn.prepareStatement(sql);
			
			ps.setString(1, p.getNomeProgetto());
			ps.setString(2, p.getDescrizione());
			
			
			
			num=ps.executeUpdate(); // DML
			
			ps.close();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return num;
	}
	
	//voglio tutti i dipendenti
	public ResultSet getProgetto() {

		Connection conn = Connessione.getConnection();
		String sql = "SELECT * FROM progetti";
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
	 public ResultSet getProgettoById(int id) {
	        Connection conn = Connessione.getConnection();
	        String sql = "SELECT * FROM progetti WHERE id = ?";
	        PreparedStatement ps = null;
	        ResultSet rs = null;

	        try {
	            ps = conn.prepareStatement(sql);
	            ps.setInt(1,id);
	            rs = ps.executeQuery(); // DQL
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return rs;
	    }
	
	public boolean eliminaProgetto(int id) {
	    int result = 0;
	    Connection conn = null;
	    PreparedStatement ps = null;

	    try {
	        conn = Connessione.getConnection();
	        String sql = "DELETE FROM progetti WHERE id = ?";
	        ps = conn.prepareStatement(sql);
	        ps.setInt(1, id);
	        result = ps.executeUpdate();
	        return true;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        try {
	            if (ps != null) ps.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
		return false;

	}
	
	  public int aggiornaProgetto(Progetto p) {
	        int num = 0;
	        Connection conn = Connessione.getConnection();
	        String sql = "UPDATE progetti SET nomeProgetto = ?, descrizione = ? WHERE id=?";

	        PreparedStatement ps = null;

	        try {
	            ps = conn.prepareStatement(sql);
	            ps.setString(1, p.getNomeProgetto());
	            ps.setString(2, p.getDescrizione());
	            ps.setInt(3, p.getId());
	         
	            num = ps.executeUpdate(); // DML
	            ps.close();
	            
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }

	        return num;
	    }
	  
	  public List<Progetto> getAllProgetti() {
	        Connection conn = Connessione.getConnection();
	        List<Progetto> progetti = new ArrayList<>();
	        String sql = "SELECT * FROM progetti";

	        try (PreparedStatement ps = conn.prepareStatement(sql)) {
	            ResultSet rs = ps.executeQuery();

	            while (rs.next()) {
	                Progetto proj = new Progetto();
	                proj.setId(rs.getInt("id"));
	                proj.setNomeProgetto(rs.getString("nomeProgetto"));
	                proj.setDescrizione(rs.getString("descrizione"));
	                progetti.add(proj);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            Connessione.closeConnection(conn);
	        }

	        return progetti;
	    }


}
