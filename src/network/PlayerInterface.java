package network;

import java.rmi.RemoteException;
import java.util.List;

import components.Game;
import components.Player;
import components.Quiz;

public interface PlayerInterface {
	
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

	
	/**Begins a new game for the player on the passed quiz. If there already
	 * exists a game with the same player/quiz combination, it will throw an 
	 * IllegalStateException
	 * 
	 * @param player The player who will be playing this game
	 * @param quiz The quiz which the Game will draw questions from
	 * @return a Game for the player and quiz passed
	 * @throws IllegalStateException if the game already exists 
	 */
	public Game startNewGame(Player player, Quiz quiz) throws RemoteException;

	/**Returns the full list of games which the player has (both active and inactive)
	 * 
	 * @param player The player who's list to get
	 * @return List of all games belonging to the player
	 */
	public List<Game> getGameList(Player player) throws RemoteException;
	
	/**Returns the list of all quizzes which are currently active and which the player
	 * does not already have a game.
	 */
	public List<Quiz> getActiveQuizList(Player player) throws RemoteException;

}
