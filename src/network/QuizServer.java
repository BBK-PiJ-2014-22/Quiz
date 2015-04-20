package network;

//TODO - THREAD SAFE - your lists may run into problems with concurrency - make your things synchronised
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import components.*;

public class QuizServer extends UnicastRemoteObject implements SetupInterface, PlayerInterface {

List<Player> playerList;
	List<Quiz> quizList;
	List<Game> gameList;
	
	public QuizServer() throws RemoteException{
		super();
		this.playerList = new ArrayList<Player>();
		this.quizList = new ArrayList<Quiz>();
	}

	@Override
	public Quiz createQuiz(Player player, String name) throws RemoteException {
		if (player == null || name == null)
			throw new NullPointerException();
		//The below intentionally uses == as it must be the very same object not simply an equal one
		else if (player != playerList.get(player.getId())) 
			throw new IllegalArgumentException();
		else{
			quizList.add(new QuizImpl(quizList.size(), name, player));
			return quizList.get(quizList.size()-1);
		}
	}
	
	@Override
	public List<Quiz> getQuizList(Player player) throws RemoteException {
		if (player == null){
			throw new NullPointerException();
		}else{
			List<Quiz> result = new ArrayList<Quiz>();
			for (Quiz quiz : quizList){
				if (quiz.getQuizMaster() == player) //Note intentionally uses == for identity
					result.add(quiz);
			}
			return result;
		}
	}

	@Override
	public Player login(int id, String name) throws RemoteException {
		Player result = null;
		if ((id > 0 && id < playerList.size()) 	&&
			playerList.get(id).getName().equals(name)){
			result = playerList.get(id);
		}
		return result;
	}
			


	@Override
	public Player createPlayer(String name) throws RemoteException {
		this.playerList.add(new PlayerImpl(this.playerList.size(), name));
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
	public Game startNewGame(Player player, Quiz quiz) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getGameList(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}

}
