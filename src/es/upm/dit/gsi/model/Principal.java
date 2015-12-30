package es.upm.dit.gsi.model;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

import javax.servlet.ServletContext;

import edu.wpi.cetask.Plan;
import edu.wpi.cetask.Task;
import edu.wpi.disco.Disco;
import edu.wpi.disco.Interaction;
import edu.wpi.disco.User;
import edu.wpi.disco.Agenda.Plugin;
import edu.wpi.disco.Agent;
import edu.wpi.disco.lang.Propose;
import edu.wpi.disco.lang.Utterance;
import es.upm.dit.gsi.pruebaLorena.MyAgent;



public class Principal {
	
	
	
	private  String agente = "";
	private List<Plugin.Item> items;
	private ArrayList<String> menu= new ArrayList<String>();
	//probamos a crear un agente con new Agent ( no con nuestro agent)
	private final Interaction interaction = new Interaction(new Agent("agent"), new User("user"));
	private final Disco disco = interaction.getDisco(); 
 
	  
	   private final boolean 
	         guess = interaction.getProperty("interaction@guess", true),
	         retry = interaction.getProperty("interaction@retry", true);
	   
	   
	   public String interact() {
		   
		   // using example task model from Disco source release
		  // ServletContext context = getServletContext();
		   //COGE LIBRARY.XML
		   //String path = context.getRealPath("/WEB-INF/models/Library.xml");
		 
			    
	       interaction.load(pathModel);

	       // typically the embedding system is calling Disco inside of a loop,
	       // but here we just have straight-line code to illustrate some
	       // typical calls
	       
	       
	       //normalmente el sistema de incrustación está llamando Disco interior de un bucle, 
	       //pero aquí sólo tenemos código de línea recta para ilustrar algunas llamadas típicas

	       // user utterance, e.g., from speech recognition or GUI
	     user(Propose.Should.newInstance(disco, true, newInstance("Borrow")),
	             // null argument allows plan recognition to determine contributes
	             null); 

	       //agent response
	      // agent();

	       // report of user action
	      // user(newInstance("GoToLibrary"), null);
	       // agent response
	      //   agent();

	         // user utterance 
	      user(new Propose.What(disco, true, 
	               newInstance("ChooseBook"), 
	               "input", 
	               // note Javascript eval, since Library model uses Javascript data
	               interaction.eval("new Book(\"Sawyer\", \"Mindscan\")", "ComponentExample")),
	               null);

	         // agent response
	         agent();
	         
	         // example of generating menu of possible user utterances, e.g., for GUI
	         // or to restrict grammar for speech recognition

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
	         Plugin.Item item = items.get(1);
	         user(item.task, item.contributes);
	         
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
	      interaction.occurred(true, task, contributes);
	   }
	   
	   private Task newInstance (String task) { 
		   
	      return interaction.getTaskClass(task).newInstance();
	   }
}
