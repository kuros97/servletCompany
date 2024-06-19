package dao;

import model.Account;
import model.AccountDTO;
import model.Connessione;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountRepository {

	public Account getAccountByUsernamePassword(String usernameOrEmail, String password) {
		Connection conn = Connessione.getConnection();
		Account account = null;
		String query = "SELECT * FROM accounts WHERE (username = ? OR email = ?) AND password = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, usernameOrEmail);
			stmt.setString(2, usernameOrEmail);
			stmt.setString(3, password);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				account = new Account();
				account.setId(rs.getInt("id"));
				account.setUsername(rs.getString("username"));
				account.setEmail(rs.getString("email"));
				account.setPassword(rs.getString("password"));
				account.setIdPermesso(rs.getInt("idPermesso"));
				account.setIdDipendente(rs.getInt("idDipendente"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			Connessione.closeConnection(conn);
		}

		return account;
	}

	public AccountDTO getDipendenteById(int id) {
		Connection conn = Connessione.getConnection();
		AccountDTO accountDTO = null;
		String query = "SELECT dipendenti.*, accounts.username, accounts.email, accounts.password " + "FROM dipendenti "
				+ "JOIN accounts ON dipendenti.id = accounts.idDipendente " + "WHERE dipendenti.id = ?";

		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				accountDTO = new AccountDTO();
				accountDTO.setId(rs.getInt("id"));
				accountDTO.setNome(rs.getString("nome"));
				accountDTO.setCognome(rs.getString("cognome"));
				accountDTO.setDataDiNascita(rs.getDate("data_di_nascita"));
				accountDTO.setCf(rs.getString("codice_fiscale"));
				accountDTO.setStipendio(rs.getDouble("stipendio"));
				accountDTO.setSesso(rs.getBoolean("sesso"));
				accountDTO.setDataDiAssunzione(rs.getDate("data_di_assunzione"));
				accountDTO.setLuogoNascita(rs.getString("luogo_nascita"));
				accountDTO.setUsername(rs.getString("username"));
				accountDTO.setEmail(rs.getString("email"));
				accountDTO.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return accountDTO;
	}

	public ResultSet getDipAcc() {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT d.*, a.username, a.email, a.password, a.idPermesso, a.idDipendente "
				+ "FROM dipendenti d JOIN accounts a ON d.id = a.idDipendente";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public int inserimentoDipAcc(AccountDTO acc) {
		int num = 0;
		Connection conn = Connessione.getConnection();
		String sql = "INSERT INTO dipendenti(nome, cognome, data_di_nascita, codice_fiscale, stipendio, sesso, data_di_assunzione, luogo_nascita) VALUES(?,?,?,?,?,?,?,?)";
		String sql1 = "INSERT INTO accounts(username, email, password, idPermesso, idDipendente) VALUES(?,?,?,?,?)";

		PreparedStatement ps = null;
		PreparedStatement ps1 = null;

		try {
			conn.setAutoCommit(false); // Start transaction

			// Insert into dipendenti
			ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, acc.getNome());
			ps.setString(2, acc.getCognome());
			ps.setDate(3, acc.getDataDiNascita());
			ps.setString(4, acc.getCf());
			ps.setDouble(5, acc.getStipendio());
			ps.setBoolean(6, acc.isSesso());
			ps.setDate(7, acc.getDataDiAssunzione());
			ps.setString(8, acc.getLuogoNascita());

			num = ps.executeUpdate();

			// mi serve l'id del dipendente e me lo recupero così:
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				int idDipendente = rs.getInt(1);

				// Insert into accounts
				ps1 = conn.prepareStatement(sql1);
				ps1.setString(1, acc.getUsername());
				ps1.setString(2, acc.getEmail());
				ps1.setString(3, acc.getPassword());
				ps1.setInt(4, acc.getIdPermesso());
				ps1.setInt(5, idDipendente); // Setta l'idDipendente

				ps1.executeUpdate();
			}

			conn.commit(); // Commit transaction

		} catch (SQLException e) {
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (ps != null)
					ps.close();
				if (ps1 != null)
					ps1.close();
				if (conn != null)
					conn.close();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		}

		return num;
	}

	public boolean updateDipAcc(AccountDTO accountDTO) {
		Connection conn = null;
		PreparedStatement stmtDipendenti = null;
		PreparedStatement stmtAccounts = null;
		boolean updated = false;

		try {
			conn = Connessione.getConnection();
			conn.setAutoCommit(false); // Inizia la transazione

			String queryDipendenti = "UPDATE dipendenti SET nome = ?, cognome = ?, data_di_nascita = ?, stipendio =?, sesso = ?, data_di_assunzione = ?, luogo_nascita = ? WHERE id = ?";
			String queryAccounts = "UPDATE accounts SET username = ?, email = ?, password = ?, idPermesso = ? WHERE idDipendente = ?";

			// Aggiornamento dei dati nella tabella "dipendenti"
			stmtDipendenti = conn.prepareStatement(queryDipendenti);
			stmtDipendenti.setString(1, accountDTO.getNome());
			stmtDipendenti.setString(2, accountDTO.getCognome());
			stmtDipendenti.setDate(3, accountDTO.getDataDiNascita());
			stmtDipendenti.setDouble(4, accountDTO.getStipendio());
			stmtDipendenti.setBoolean(5, accountDTO.isSesso());
			stmtDipendenti.setDate(6, accountDTO.getDataDiAssunzione());
			stmtDipendenti.setString(7, accountDTO.getLuogoNascita());
			stmtDipendenti.setInt(8, accountDTO.getId());
			int rowsAffectedDipendenti = stmtDipendenti.executeUpdate();

			// Aggiornamento dei dati nella tabella "accounts"
			stmtAccounts = conn.prepareStatement(queryAccounts);
			stmtAccounts.setString(1, accountDTO.getUsername());
			stmtAccounts.setString(2, accountDTO.getEmail());
			stmtAccounts.setString(3, accountDTO.getPassword());
			stmtAccounts.setInt(4, accountDTO.getIdPermesso());
			stmtAccounts.setInt(5, accountDTO.getIdDipendente());
			int rowsAffectedAccounts = stmtAccounts.executeUpdate();

			// Verifica se entrambe le query hanno avuto successo
			if (rowsAffectedDipendenti > 0 && rowsAffectedAccounts > 0) {
				conn.commit(); // Commetti la transazione solo se entrambe le query hanno avuto successo
				updated = true;
			} else {
				conn.rollback(); // Rollback se una delle query fallisce
			}
		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback(); // Rollback in caso di errore
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (stmtDipendenti != null)
					stmtDipendenti.close();
				if (stmtAccounts != null)
					stmtAccounts.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return updated;
	}

	public ResultSet getDipAccByCf(String cf) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT * " + "FROM dipendenti d " + "JOIN accounts a ON d.id = a.idDipendente "
				+ "WHERE d.codice_fiscale = ?";

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, cf);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public int eliminaDipAcc(String cf) {
		int result = 0;
		Connection conn = null;
		PreparedStatement psDipendente = null;
		PreparedStatement psAccount = null;

		try {
			conn = Connessione.getConnection();
			conn.setAutoCommit(false);

			// Elimina l'account associato al dipendente
			String sqlAccount = "DELETE FROM accounts WHERE idDipendente = (SELECT id FROM dipendenti WHERE codice_fiscale = ?)";
			psAccount = conn.prepareStatement(sqlAccount);
			psAccount.setString(1, cf);
			psAccount.executeUpdate();

			// Elimina il dipendente
			String sqlDipendente = "DELETE FROM dipendenti WHERE codice_fiscale = ?";
			psDipendente = conn.prepareStatement(sqlDipendente);
			psDipendente.setString(1, cf);
			result = psDipendente.executeUpdate();

			conn.commit(); // "esegui" la transazione

		} catch (SQLException e) {
			if (conn != null) {
				try {
					conn.rollback(); // Rollback in caso di errore
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
			e.printStackTrace();
		} finally {
			try {
				if (psAccount != null)
					psAccount.close();
				if (psDipendente != null)
					psDipendente.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
	}



	public boolean isCfExists(String cf) {
		Connection connection = Connessione.getConnection();
		String query = "SELECT COUNT(*) FROM dipendenti WHERE codice_fiscale = ?";
		try {
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, cf);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1) > 0;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public ResultSet getDipAcc(int offset, int limit) {
		Connection conn = Connessione.getConnection();
		String sql = "SELECT d.*, a.username, a.email, a.idPermesso "
				+ "FROM dipendenti d JOIN accounts a ON d.id = a.idDipendente " + "LIMIT ? OFFSET ?";
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, limit);
			ps.setInt(2, offset);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet searchDipendenti(String cf, String nome, String cognome, String luogoNascita, Double stipendioMax,
			Integer permesso, int offset, int limit) {
		Connection conn = Connessione.getConnection();
		StringBuilder sql = new StringBuilder(
				"SELECT d.*, a.username, a.email, a.idPermesso FROM dipendenti d JOIN accounts a ON d.id = a.idDipendente WHERE 1=1 ");
		if (cf != null && !cf.isEmpty()) {
			sql.append("AND d.codice_fiscale = ? ");
		}
		if (nome != null && !nome.isEmpty()) {
			sql.append("AND d.nome LIKE ? ");
		}
		if (cognome != null && !cognome.isEmpty()) {
			sql.append("AND d.cognome LIKE ? ");
		}
		if (luogoNascita != null && !luogoNascita.isEmpty()) {
			sql.append("AND d.luogo_nascita LIKE ? ");
		}
		if (stipendioMax != null) {
			sql.append("AND d.stipendio <= ? ");
		}
		if (permesso != null) {
			sql.append("AND a.idPermesso = ? ");
		}
		sql.append("LIMIT ? OFFSET ?");

		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = conn.prepareStatement(sql.toString());
			int index = 1;
			if (cf != null && !cf.isEmpty()) {
				ps.setString(index++, cf);
			}
			if (nome != null && !nome.isEmpty()) {
				ps.setString(index++, "%" + nome + "%");
			}
			if (cognome != null && !cognome.isEmpty()) {
				ps.setString(index++, "%" + cognome + "%");
			}
			if (luogoNascita != null && !luogoNascita.isEmpty()) {
				ps.setString(index++, "%" + luogoNascita + "%");
			}
			if (stipendioMax != null) {
				ps.setDouble(index++, stipendioMax);
			}
			if (permesso != null) {
				ps.setInt(index++, permesso);
			}
			ps.setInt(index++, limit);
			ps.setInt(index++, offset);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rs;
	}

	public int getTotalDipendenti(String cf, String nome, String cognome, String luogoNascita, Double stipendioMax,
			Integer permesso) {
		Connection connection = Connessione.getConnection();
		StringBuilder query = new StringBuilder("SELECT COUNT(*) FROM dipendenti WHERE 1=1");

		if (cf != null && !cf.isEmpty())
			query.append(" AND codice_fiscale LIKE ?");
		if (nome != null && !nome.isEmpty())
			query.append(" AND nome LIKE ?");
		if (cognome != null && !cognome.isEmpty())
			query.append(" AND cognome LIKE ?");
		if (luogoNascita != null && !luogoNascita.isEmpty())
			query.append(" AND luogo_nascita LIKE ?");
		if (stipendioMax != null)
			query.append(" AND stipendio <= ?");
		if (permesso != null)
			query.append(" AND idPermesso = ?");

		try {
			PreparedStatement ps = connection.prepareStatement(query.toString());
			int paramIndex = 1;

			if (cf != null && !cf.isEmpty())
				ps.setString(paramIndex++, "%" + cf + "%");
			if (nome != null && !nome.isEmpty())
				ps.setString(paramIndex++, "%" + nome + "%");
			if (cognome != null && !cognome.isEmpty())
				ps.setString(paramIndex++, "%" + cognome + "%");
			if (luogoNascita != null && !luogoNascita.isEmpty())
				ps.setString(paramIndex++, "%" + luogoNascita + "%");
			if (stipendioMax != null)
				ps.setDouble(paramIndex++, stipendioMax);
			if (permesso != null)
				ps.setInt(paramIndex++, permesso);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	

	
	
	public boolean updateGuestAccount(AccountDTO accountDTO) {
	    Connection conn = null;
	    PreparedStatement stmtDipendenti = null;
	    PreparedStatement stmtAccounts = null;
	    boolean updated = false;

	    try {
	        conn = Connessione.getConnection();
	        conn.setAutoCommit(false); // Inizia la transazione

	        String queryDipendenti = "UPDATE dipendenti SET nome = ?, cognome = ?, data_di_nascita = ?, stipendio = ?, sesso = ?, data_di_assunzione = ?, luogo_nascita = ? WHERE id = ?";
	        String queryAccounts = "UPDATE accounts SET username = ?, email = ?, password = ? WHERE idDipendente = ?";

	        // Aggiornamento dei dati nella tabella "dipendenti"
	        stmtDipendenti = conn.prepareStatement(queryDipendenti);
	        stmtDipendenti.setString(1, accountDTO.getNome());
	        stmtDipendenti.setString(2, accountDTO.getCognome());
	        stmtDipendenti.setDate(3, accountDTO.getDataDiNascita());
	        stmtDipendenti.setDouble(4, accountDTO.getStipendio());
	        stmtDipendenti.setBoolean(5, accountDTO.isSesso());
	        stmtDipendenti.setDate(6, accountDTO.getDataDiAssunzione());
	        stmtDipendenti.setString(7, accountDTO.getLuogoNascita());
	        stmtDipendenti.setInt(8, accountDTO.getId());
	        int rowsAffectedDipendenti = stmtDipendenti.executeUpdate();

	        // Aggiornamento dei dati nella tabella "accounts"
	        stmtAccounts = conn.prepareStatement(queryAccounts);
	        stmtAccounts.setString(1, accountDTO.getUsername());
	        stmtAccounts.setString(2, accountDTO.getEmail());
	        stmtAccounts.setString(3, accountDTO.getPassword());
	        stmtAccounts.setInt(4, accountDTO.getId());
	        int rowsAffectedAccounts = stmtAccounts.executeUpdate();

	        // Verifica se entrambe le query hanno avuto successo
	        if (rowsAffectedDipendenti > 0 && rowsAffectedAccounts > 0) {
	            conn.commit(); // Commetti la transazione solo se entrambe le query hanno avuto successo
	            updated = true;
	        } else {
	            conn.rollback(); // Rollback se una delle query fallisce
	        }
	    } catch (SQLException e) {
	        if (conn != null) {
	            try {
	                conn.rollback(); // Rollback in caso di errore
	            } catch (SQLException ex) {
	                ex.printStackTrace();
	            }
	        }
	        e.printStackTrace();
	    } finally {
	        try {
	            if (stmtDipendenti != null) stmtDipendenti.close();
	            if (stmtAccounts != null) stmtAccounts.close();
	            if (conn != null) conn.close();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    return updated;
	}

}