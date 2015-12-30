package es.upm.dit.gsi;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.cetask.TaskEngine;
import edu.wpi.cetask.TaskModel;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.Agent;
import edu.wpi.disco.lang.Propose;
import edu.wpi.disco.lang.Utterance;
import es.upm.dit.gsi.pruebaLorena.MyAgent;
import es.upm.dit.gsi.model.*;

/**
 * Servlet implementation class pruebaLorena
 */
//@WebServlet("/pruebaLorena")
public class pruebaLorena extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected String path = null;
	private TaskEngine model;
	User user = new User("user");
	private  String agente = "";
	private List<Plugin.Item> items;
	private List<Plugin.Item> items2;
	private ArrayList<String> menu= new ArrayList<String>();
	private final Interaction interaction = new Interaction(new MyAgent("agent"), user);
	private final Disco disco = interaction.getDisco();
	private final Disco disco2 = new Disco(interaction);
	private Agent agente2= new Agent("agent");
	   private final boolean 
       guess = interaction.getProperty("interaction@guess", true),
       retry = interaction.getProperty("interaction@retry", true);
  
 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public pruebaLorena() {
        super();
        // TODO Auto-generated constructor stub
    }
    public void init(ServletConfig servletConfig) throws ServletException{
       this.path = servletConfig.getInitParameter("path");
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//interact();
		
		interaction.load(path);
		items = user.generate(interaction);
		items2 = agente2.respond(interaction, true, guess, retry);
		  user(Propose.Should.newInstance(disco, true, newInstance("Borrow")),
		             // null argument allows plan recognition to determine contributes
		            null); 
		
		String p =interaction.format(items.get(0), true, true);
		agente2.occurred(interaction,items.get(0),true);
		System.out.println(p);
		interaction.run();
		// creamos una session y pasamos los valores del menu y del agente
		HttpSession session = request.getSession();
		session.
		session.setAttribute("pasar", agente);
		session.setAttribute("opciones", menu);
	
		
		request.getRequestDispatcher("router.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
 
	   
	  
	
	   public String interact() {
		   
		   // using example task model from Disco source release
		  // ServletContext context = getServletContext();
		   //COGE LIBRARY.XML
		  // String path = context.getRealPath("/WEB-INF/models/Library.xml");
		   System.out.println(path);
	       interaction.load(path);

	       // typically the embedding system is calling Disco inside of a loop,
	       // but here we just have straight-line code to illustrate some
	       // typical calls

	       // user utterance, e.g., from speech recognition or GUI
	     user(Propose.Should.newInstance(disco, true, newInstance("Borrow")),
	             // null argument allows plan recognition to determine contributes
	            null); 

	       //agent response
	      // agent();

	       // report of user action
	     user(newInstance("GoToLibrary"), null);
	       // agent response
	    //agent();

	         // user utterance 
	       
	      user(new Propose.What(disco, true, 
	               newInstance("ChooseBook"), 
	               "input", 
	               // note Javascript eval, since Library model uses Javascript data
	               interaction.eval("new Book(\"Heinlein\", \"Stranger in a Strange Land\")", "ComponentExample")),
	               null);

	         // agent response
	        agent();
	         
	         // example of generating menu of possible user utterances, e.g., for GUI
	         // or to restrict grammar for speech recognition
	         
	         //items = user.generate(interaction);
	         items = interaction.getExternal().generate(interaction);
	         // print out formatted choices on system console
	         //espacio en blanco, salto de linea
	         
	         System.out.println();
	         
	         // necesitamos rellenar el menu con las posibles opciones de menu dependiendo si la respuesta 
	         // es aceptada rechazada o demas....
	         for (Plugin.Item item : items) {
	        	 for (int i = 0; i < 1; i++) {
	        	String p = interaction.format(item, true, true);
	        	
	        	menu.add(i, p);
	        	System.out.println("MENU ROUTER: "+p);
	        	 }
	         }
	         
	         // choose second utterance from menu
	         Plugin.Item item = items.get(0);
	         //user(item.task, item.contributes);
	         
	         //System.out.println(item);
	        // Plugin.Item item2 = items.get(2);
	         //System.out.println(item2);
	         //Plugin.Item item3 = items.get(3);
	        // System.out.println(item3);
	        // Plugin.Item item4 = items.get(0);
	        // System.out.println(item4);
	         //user(item2.task, item2.contributes);
	         // if optional Disco console window used, go to window now and try typing in 
	         // commands, such as 'history'
	      
	         // next line is here only to keep optional Disco console window open in 
	         // this demo until you type 'quit' or close it
	         try { interaction.join(); } catch (InterruptedException e) {}
		   return "";
	         
	   }
	 private boolean agent () {      
	      // see simple model for agent turn at Agent.respond()
		 
	      return interaction.getSystem().respond(interaction, false, guess, retry);
	   }
	   
	   private void user (Task task, Plan contributes) {
		   /**
		    * Thread-safe method to notify interaction that given <em>primitive</em>
		    * task has occurred. Typically used in dialogue loop.
		    * 
		    * @param external true if performed by user, false if by system
		    * @param occurrence task that has occurred
		    * @param contributes plan to which this task contributes, or null
		    * 
		    * @see #occurredUtterance(Utterance,Plan,String)
		    */
	      interaction.occurred(true, task, contributes);
	   }
	   
	   private Task newInstance (String task) { 
		   /**
		    * Thread-safe method to find task class with given id.
		    * 
		    * @see Disco#getTaskClass(String)
		    */
	      return interaction.getTaskClass(task).newInstance();
	   }
	   
	   public class MyAgent extends Agent {
	      
	      private MyAgent (String name) { super(name); }
	      
	      @Override
	      public void say (Interaction interaction, Utterance utterance) {
	         // here is where you would put natural language generation
	         // and/or pass utterance string to TTS or GUI
	         // for now we just call Disco's default formatting and print
	         // out result on system console
	    	  // pasamos el valor del agente a la variable para poder pasarlo al jsp
	    	agente= interaction.format(utterance);
	        System.out.println("AGENT LORENA: "+ agente);
	      }
	   }

}
