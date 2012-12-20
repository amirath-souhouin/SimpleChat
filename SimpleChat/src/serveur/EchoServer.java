package serveur;// This file contains material supporting section 3.7 of the textbook:// "Object Oriented Software Engineering" and is issued under the open-source// license found at www.lloseng.com import java.io.*;import common.ChatIF;import ocsf.server.*;/** * This class overrides some of the methods in the abstract  * superclass in order to give more functionality to the server. * * @author Dr Timothy C. Lethbridge * @author Dr Robert Lagani&egrave;re * @author Fran&ccedil;ois B&eacute;langer * @author Paul Holden * @version July 2000 */public class EchoServer extends AbstractServer {  //instance variables *************************************************  	ChatIF serverUI;       //Constructors ****************************************************    /**   * Constructs an instance of the echo server.   *   * @param port The port number to connect on.   */  public EchoServer(int port, ChatIF serverUI)   {    super(port);    this.serverUI=serverUI;  }    //Instance methods ************************************************    /**   * This method handles any messages received from the client.   *   * @param msg The message received from the client.   * @param client The connection from which the message originated.   */  public void handleMessageFromClient    (Object msg, ConnectionToClient client)  {	if(msg.toString().startsWith(Character.toString('#')))	{		handleCmdClient(msg.toString(),client);	}	else	{		System.out.println("Message received: " + msg + " from " + client);		this.sendToAllClients(msg);	}  }    public void handleMessageFromServerUI(String message)  {	  if (message.startsWith(Character.toString('#')))	  {		  handleCmdServerUI(message);	  }	  else	  {		  this.sendToAllClients("Server MSG> " + message);  	  }	    }    public void handleCmdServerUI(String cmd)  {	  if (cmd.startsWith("#quit"))	  {		  this.sendToAllClients("Le server va maintenant etre ferm�");		  try {				this.close();			} catch (IOException e) {				// TODO Auto-generated catch block				e.printStackTrace();			}		  System.exit(0);	  }	  else if (cmd.startsWith("#stop"))	  {		  this.sendToAllClients("Attention, plus de nouvelles connexions possibles");		  this.stopListening();	  }	  else if (cmd.startsWith("#close"))	  {		  this.sendToAllClients("Le server va maintenant etre ferm�");		  this.sendToAllClients("#logoff");		  try {			this.close();		} catch (IOException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}  	  }	  else if (cmd.startsWith("#setport"))	  {		  serverUI.display("Le port a ete modifie");		  setPort(Integer.parseInt(cmd.substring(9)));	  }	  else if (cmd.startsWith("#getport"))	  {		  serverUI.display("Le port server est : " + getPort());	  }	  else if (cmd.startsWith("#start"))	  {		  try {			this.listen();		} catch (IOException e) {			// TODO Auto-generated catch block			e.printStackTrace();		}	  }  }      public void handleCmdClient(String cmd, ConnectionToClient client)  {	  if (cmd.startsWith("#logoff"))	  {		  serverUI.display("Demande de deconnexion");		  try 		  {			  client.close();		  }		  catch (IOException e) 		  {			  serverUI.display("Erreur d'IO");		  }	  }  }    /**   * This method overrides the one in the superclass.  Called   * when the server starts listening for connections.   */  protected void serverStarted()  {    serverUI.display("Server listening for connections on port " + getPort());  }    protected void clientConnected(ConnectionToClient client)   {	  serverUI.display("Un nouveau client est connecte");  }    synchronized protected void clientDisconnected(ConnectionToClient client)   {	  serverUI.display("Un client s'est deconnecte gentillement");  }    synchronized protected void clientException(ConnectionToClient client, Throwable exception)   {	  serverUI.display("Un client s'est deconnecte brutalement");  }    /**   * This method overrides the one in the superclass.  Called   * when the server stops listening for connections.   */  protected void serverStopped()  {	  serverUI.display("Server has stopped listening for connections.");  }    //Class methods ***************************************************		 }//End of EchoServer class