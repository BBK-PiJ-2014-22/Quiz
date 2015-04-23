package network;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Scanner;

import components.Game;
import components.Player;
import components.Quiz;

public class PlayerClient {

	Player player;
	List<Quiz> activeQuizList;
	List<Game> gameList;
	PlayerInterface server;
	Game currentGame;  
	
	public PlayerClient(){
		player = null;
		activeQuizList = null;
		gameList = null;
		server = null;
		currentGame = null;  
	}
	
	public static void main(String[] args){
		
	}
	
	public void launch(){
		
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
	 */
	public boolean login(int id, String name){
		return false;
	}
	
	/**Creates a new player on the server to be used. The player will be
	 * assigned to the this.player variable.
	 * 
	 * @param name name of the player
	 * @return true if successfully logged in,false otherwise
	 */
	public boolean createPlayer(String name){
		return false;
	}
	
	/**Assigns the list of all quizzes which are currently active and which the player
	 * does not already have a game for to the this.activeQuizList variable.
	 */
	public void assignActiveQuizList(){
		
	}
	
	/**Assigns the list of all games that the player has started or completed to the 
	 * this.gameList variable
	 */
	public void assignGameList(){
		
	}
	
	/**Begins a new game for the player on the passed quiz and assigns it to the
	 * currentGame variable. If the game can not be started for any reason it will 
	 * return false and not start the game. This can happen if a game already exists
	 * for that quiz or the quiz has been made inactive.
	 * 
	 * @param quiz The quiz which the Game will draw questions from
	 */
	public boolean startNewGame(Quiz quiz){
		return false;
	}
	
	/**Interface for playing a game. Will run until completion or paused
	 * 
	 */
	public void playGame(){
		
	}
	
	/**Pauses the current game.
	 * 
	 */
	public void pauseGame(){
		
	}
	
	/**Continues a paused game. Returns true if the game is an active game, 
	 * and launches the player interface. Returns false otherwise.*/
	public boolean continueGame(int id){
		return false;
	}
	
	/**Returns a pretty, readable version of the game list for the player to
	 * see.
	 * @return pretty represnetation of games
	 */
	public String getPrettyGameList(){
		return null;
	}
	
	/**Returns a pretty, readable version of the quiz list for the player to
	 * see
	 */
	public String getPrettyQuizList(){
		return null;
	}
}
