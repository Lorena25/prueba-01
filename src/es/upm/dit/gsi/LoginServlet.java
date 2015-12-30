package es.upm.dit.gsi;


import java.awt.List;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import es.upm.dit.gsi.vo.VOLogin;

/**
 * Servlet implementation class LoginServlet
 */
// con esto se definde un servlet pero 
//vamos a definirlo en web.xml @WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public LoginServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request,response);
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		String usuario = request.getParameter("usuario");
		String password = request.getParameter("password");
		System.out.println("Usuario:" +usuario);
		System.out.println("Password:" +password);
		
		if((usuario.equals("Lorena")) && (password.equals("Sancho"))){
				System.out.println("welcome...");
				//VOLogin vo = new VOLogin();
				//vo.setUsuario(usuario);
				//vo.setPassword(password);
				//vo.setEdad(21);
				//vo.setNombre("Lorena Sancho");
				//HttpSession session = request.getSession(true);
				//List<VOLogin> lista= new ArrayList<VOLogin>();
				request.setAttribute("usuariologueado", usuario+ " " +password);
				//session.setAttribute("listaUsuario", lista);
				request.getRequestDispatcher("Welcome.jsp").forward(request, response);
		}else{
			System.out.println("error de credenciales de acceso...");
			response.sendRedirect("");
			}
		}
	}


