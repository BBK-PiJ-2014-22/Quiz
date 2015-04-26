package network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import components.Player;
import components.Quiz;

public interface SetupInterface extends Remote{
	
	
	/**Creates a new quiz with the corresponding name and returns. The quiz should
	 * automatically be assigned an ID and be assigned to the player that created it.
	 * 
	 * @param name Name of the quiz. Should not be null
	 * @param player Player who is owner/creator of the quiz
	 * @return new Quiz object created
	 * @throws RemoteException
	 * @throws NullPointerException
	 * @throws IllegalArgumentException
	 */
	public Quiz createQuiz(Player player, String name) throws RemoteException;

	/**Gets a string represention all of the Player's quizzes, with IDs so they can acces them
	 * 
	 * @param player
	 * @return
	 */
	public String getQuizDisplayList(Player player) throws RemoteException;
	
	/** Returns the quiz with the corresponding ID
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	
	public Quiz getQuiz(int id) throws RemoteException;
	

	/**Should return the player object if the ID and Name match to
	 * a player that already exists in the system. Will return null
	 * if no such player exists.
	 * 
	 * @param id ID corresponding to an existing player
	 * @param name name matching to the player ID
	 * @return player object corresponding to name and ID. null if no such player exists
	 */
	public Player login(int id, String name) throws RemoteException;
	
	/**Creates and returns a new player object on the server with name matching the
	 * paramater. Should assign it an ID on creation for future use.
	 * 
	 * @param name Player name to use
	 * @return new Player object created.
	 */
	public Player createPlayer(String name) throws RemoteException;
	
	/**Gets the list of winners for a specific quiz. It will return null if the player does
	 * not own that quiz or it is not completed.
	 * 
	 * @return list of winners if the player owns a quiz, or null if it is unowned or not completed 
	 */
	public List<Player> getWinners(int id) throws RemoteException;
	
	/**Gets the high score of a specific quiz
	 * 
	 */
	public int getHighScore(int id) throws RemoteException;
}
