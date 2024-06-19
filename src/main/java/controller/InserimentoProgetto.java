package controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DipendenteRepository;
import dao.ProgettoRepository;
import model.Dipendente;
import model.Progetto;

/**
 * Servlet implementation class Inserimento
 */
@WebServlet("/InserimentoProgetto")
public class InserimentoProgetto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InserimentoProgetto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/* preparo la query per recuperare tutti i dipendenti*/
		ProgettoRepository repo=new ProgettoRepository();
		ArrayList<Progetto> array=new ArrayList();
	
		
		ResultSet rs = repo.getProgetto();


		try {
			while (rs.next()) {
				Progetto p=new Progetto();
				p.setId(rs.getInt("id"));
				p.setNomeProgetto(rs.getString("nomeProgetto"));
				p.setDescrizione(rs.getString("descrizione"));
				array.add(p);

			}
			
			RequestDispatcher rd=request.getRequestDispatcher("gestioneprogetti.jsp");
			request.setAttribute("arraypr", array);
			rd.forward(request, response);
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			//error
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			request.setAttribute("msg", "Qualcosa è andato storto!");
			rd.forward(request, response);
		}

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		ProgettoRepository repo=new ProgettoRepository();

		Progetto p=new Progetto();

		p.setNomeProgetto(request.getParameter("nomeProgetto"));
		p.setDescrizione(request.getParameter("descrizione"));


		//preparo la query
		if(repo.inserimentoProgetto(p)>0)
		{
			//success
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			request.setAttribute("msg", "Inserimento avvenuto correttamente!");
			rd.forward(request, response);

		}
		else
		{
			//error
			RequestDispatcher rd=request.getRequestDispatcher("home.jsp");
			request.setAttribute("msg", "Inserimento non avvenuto!");
			rd.forward(request, response);

		}





	}

}