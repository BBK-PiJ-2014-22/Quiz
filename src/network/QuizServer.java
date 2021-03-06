package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import components.*;

public class QuizServer extends UnicastRemoteObject implements SetupInterface, PlayerInterface {


	private static final long serialVersionUID = 7444117470330269850L;
	
	List<Player> playerList;
	List<Quiz> quizList;
	List<Game> gameList;
	
	public QuizServer() throws RemoteException{
		super();
		this.playerList = new ArrayList<Player>();
		this.quizList = new ArrayList<Quiz>();
		this.gameList = new ArrayList<Game>();
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public synchronized Quiz createQuiz(Player player, String name) throws RemoteException {
		if (player == null || name == null){
			System.out.println("Create Quiz Failed: passed null information");
			throw new NullPointerException();
		}else if (!player.getName().equals(playerList.get(player.getId()).getName())){ 
			System.out.println("Create Quiz Failed: "
					+ "\nUnknown player"+player.getId()+":"+player.getName()
					+ "\nPlayer at "+player.getId()+" = "+playerList.get(player.getId()).getName()
					+"\n\nFirst"+player
					+"\n\nSecond"+playerList.get(player.getId()));
				
			throw new IllegalArgumentException();
		}else{
			quizList.add(new QuizImpl(quizList.size(), name, player));
			System.out.println("Created Quiz:"+(quizList.size()-1)+" Quizzes in system:"+quizList.size());
			return quizList.get(quizList.size()-1);
		}
	}
	
	/**{@inheritDoc}
	 * 
	 */
	@Override
	public String getQuizDisplayList(Player player) throws RemoteException{
		String result = "";
		
		for (Quiz quiz : quizList){
			if (quiz.getQuizMaster().match(player))
				result += "[ID="+quiz.getQuizID()+", Name="+quiz.getQuizName()+", Status="+quiz.getStatus()+"]\n";
		}
		return result.substring(0, result.length()-1);
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Player login(int id, String name) throws RemoteException {
		Player result = null;
		if ((id >= 0 && id < playerList.size()) 	&&
			playerList.get(id).getName().equals(name)){
			result = playerList.get(id);
			System.out.println(result.display()+" logged in");
		}else{
			System.out.println("Login failed with "+id+" and "+name);
		}
		return result;
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public synchronized Player createPlayer(String name) throws RemoteException {
		if (name == null){
			Log.log("Login failed when passed null");
			return null;
		}
		this.playerList.add(new PlayerImpl(this.playerList.size(), name));
		Log.log((playerList.get(playerList.size()-1).display() +" created"));
		return playerList.get(playerList.size()-1);
	}
	
	
	/**Returns full list of players*/
	public List<Player> getFullPlayerList() throws RemoteException {
		return playerList;
	}
	
	/**Returns full quiz list*/
	public List<Quiz> getFullQuizList() throws RemoteException {
		return quizList;
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public synchronized Game startNewGame(Player player, int id) throws RemoteException {
		Quiz quiz = quizList.get(id);
		if (quiz.getStatus() != QuizStatus.ACTIVE) return null;
		if (gameKnown(quiz, player)) return null;
		else{
			gameList.add(new GameImpl(player, quiz));
			return gameList.get(gameList.size()-1);
		}
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public List<Game> getGameList(Player player) throws RemoteException {
		List<Game> result = new ArrayList<Game>();
		for (Game game : gameList){
			if (game.getPlayer().equals(player)){
				result.add(game);
			}
		}
		return result;
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public List<Quiz> getActiveQuizList(Player player) throws RemoteException {
		List<Quiz> result = new ArrayList<Quiz>();
		for (Quiz quiz : quizList){
			if (quiz.getStatus() == QuizStatus.ACTIVE && !gameKnown(quiz, player))
				result.add(quiz);
		}
		System.out.println(result);
		return result;
	}
	/**Returns true if the quiz/player combination already exists
	 * 
	 */
	private boolean gameKnown(Quiz quiz, Player player) throws RemoteException{
		for (Game i : gameList){
			if (i.getQuiz().equals(quiz) && i.getPlayer().equals(player)) return true;
		}
		return false;	
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public Quiz getQuiz(int id) throws RemoteException {
		if (id >= 0 && id < quizList.size()) return quizList.get(id);
		else return null;
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public List<Player> getWinners(int id) throws RemoteException {
		Quiz match = quizList.get(id);
		int highScore = getHighScore(id);
		List<Player> result = new ArrayList<Player>();
		for (Game game : gameList){
			if (game.getScore() == highScore && 
				game.getQuiz().equals(match) &&
				game.isCompleted())
				result.add(game.getPlayer());
		}
		return result;								
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public int getHighScore(int id) throws RemoteException {
		int highScore = 0;
		Quiz match = quizList.get(id);
		for (Game game : gameList)
			if (game.getScore() > highScore && 
				game.getQuiz().equals(match) &&
				game.isCompleted()) 
				highScore = game.getScore();
		return highScore;
	}

	/**{@inheritDoc}
	 * 
	 */
	@Override
	public List<Game> getGamesList(int id, Player player) throws RemoteException {
		List<Game> result = new ArrayList<Game>();
		Quiz match = quizList.get(id);
		
		if (match.getQuizMaster().equals(player))
			for (Game game : gameList)
				if (game.getQuiz().equals(match)) result.add(game);
		
		return result;
	}

}
