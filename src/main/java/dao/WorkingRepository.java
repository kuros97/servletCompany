package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.Connessione;

public class WorkingRepository {

	public boolean associaDipendenteProgetto(int idDipendente, int idProgetto) {
        Connection conn = Connessione.getConnection();
        String sql = "INSERT INTO workings (idDipendente, idProgetto) VALUES (?, ?)";
        boolean isSuccess = false;

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDipendente);
            ps.setInt(2, idProgetto);
            int rowsAffected = ps.executeUpdate();
            isSuccess = rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            Connessione.closeConnection(conn);
        }

        return isSuccess;
    }

	public boolean coppiaExists(int idDipendente, int idProgetto) {
		ResultSet set = null;
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * from workings where idDipendente = ? AND idProgetto = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, idDipendente);
			ps.setInt(2, idProgetto);
			set = ps.executeQuery();
			
			if (!set.next()){
				return false;
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return true;
	}

	public ResultSet getAllWorkings() {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT w.idDipendente, d.nome AS nomeDipendente, d.cognome AS cognomeDipendente, w.idProgetto, p.nomeProgetto AS nomeProgetto "
				+ "FROM workings w " + "JOIN dipendenti d ON w.idDipendente = d.id "
				+ "JOIN progetti p ON w.idProgetto = p.id";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();

			if (rs != null) {
				System.out.println("Aggiunta associazione");
			} else {
				System.out.println("result set nullo (?).");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public boolean dissociaDipendenteDaProgetto(int idDipendente, int idProgetto) {
        Connection conn = Connessione.getConnection();
        String sql = "DELETE FROM workings WHERE idDipendente = ? AND idProgetto = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idDipendente);
            ps.setInt(2, idProgetto);
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            Connessione.closeConnection(conn);
        }
    }

}
