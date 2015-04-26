package network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import components.Game;
import components.Player;
import components.Quiz;

public class PlayerClient {

	Player player;
	PlayerInterface server;
	Game currentGame;  
	
	public PlayerClient(){
		player = null;
		server = null;
		currentGame = null;  
	}
	
	public static void main(String[] args){
		PlayerClient pc = new PlayerClient();
		pc.launch();
	}
	
	public void launch(){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "QuizServer";
			Registry registry = LocateRegistry.getRegistry(1099);
			this.server = (PlayerInterface)registry.lookup(name);
		
		}catch (Exception e){
			System.err.println("PlayerClient Exception");
			e.printStackTrace();
		}
	}
	
	//Login methods
	/**Logs the player in if the ID and Name match to a corresponding
	 * ID and Name within the system. If successful, the player will
	 * be assigned to the this.player variable. This will return true if 
	 * successfully logged in. Be aware that nothing guarantees uniqueness
	 * of logged in players.
	 * 
	 * @param id player's ID
	 * @param name name of the player
	 * @return true if successfully logged in,false otherwise
	 * @throws RemoteException 
	 */
	public boolean login(int id, String name) throws RemoteException{
		player = server.login(id, name);
		if (this.player == null) return false;
		else return true;
	}
	
	/**Creates a new player on the server to be used. The player will be
	 * assigned to the this.player variable.
	 * 
	 * @param name name of the player
	 * @return true if successfully logged in,false otherwise
	 * @throws RemoteException 
	 */
	public boolean createPlayer(String name) throws RemoteException{
		player = server.createPlayer(name);
		if (this.player == null) return false;
		else return true;
	}
	
	/**Begins a new game for the player on the passed quiz and assigns it to the
	 * currentGame variable. If the game can not be started for any reason it will 
	 * return false and not start the game. This can happen if a game already exists
	 * for that quiz or the quiz has been made inactive.
	 * 
	 * @param id The quiz which the Game will draw questions from
	 */
	public boolean startNewGame(int id) throws RemoteException{
		currentGame = server.startNewGame(player, id);
		return  this.currentGame != null;
	}
	
	/**Interface for playing a game. Will run until completion or paused
	 * 
	 */


}
