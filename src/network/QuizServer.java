package network;

//TODO - THREAD SAFE - your lists may run into problems with concurrency - make your things synchronised
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
	
	@Override
	public ArrayList<Quiz> getQuizList(Player player) throws RemoteException {
		if (player == null){
			throw new NullPointerException();
		}else{
			ArrayList<Quiz> result = new ArrayList<Quiz>();
			for (Quiz quiz : quizList){
				System.out.println(quiz.getQuizMaster().display() + ":"+player.display());
				if (quiz.getQuizMaster().match(player)) 
					result.add(quiz);
			}
			System.out.println("Quiz list of size "+result.size()+" given for player"+player.display());
			return result;
		}
	}

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

	@Override
	public synchronized Player createPlayer(String name) throws RemoteException {
		this.playerList.add(new PlayerImpl(this.playerList.size(), name));
		System.out.println((playerList.get(playerList.size()-1).display() +" created"));
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

	@Override
	public synchronized Game startNewGame(Player player, Quiz quiz) throws RemoteException {
		if (!playerKnown(player) || !quizKnown(quiz))
			throw new IllegalArgumentException();
		for (Game game: this.getGameList(player)){
			if (game.getQuiz() == quiz){
				throw new IllegalArgumentException();
			}
		}
		gameList.add(new GameImpl(player, quiz));
		return gameList.get(gameList.size()-1);
	}

	@Override
	public List<Game> getGameList(Player player) throws RemoteException {
		List<Game> result = new ArrayList<Game>();
		for (Game game : gameList){
			if (game.getPlayer() == player){
				result.add(game);
			}
		}
		return result;
	}

	@Override
	public List<Quiz> getActiveQuizList(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	private boolean playerKnown(Player player) throws RemoteException{
		if (player == null || player.getId() < 0 || player.getId() >= playerList.size())
			return false;
		Player match = playerList.get(player.getId());
		return player == match; //Note this is intentionally identity
	}
	
	private boolean quizKnown(Quiz quiz) throws RemoteException{
		for (Quiz i : quizList){
			if (quiz == i) //Note this is intentionally identity
				return true;
		}
		return false;
	}
	
	private boolean gameKnown(Game game) throws RemoteException{
		for (Game i : gameList){
			if (game == i)  //Note this is intentionally identity
				return true;
		}
		return false;	
	}

}
