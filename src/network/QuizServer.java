package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import components.*;

public class QuizServer extends UnicastRemoteObject implements SetupInterface {

	List<Player> playerList;
	
	
	public QuizServer() throws RemoteException{
		super();
		this.playerList = new ArrayList<Player>();
	}

	@Override
	public Quiz createQuiz(Player player, String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Quiz> getQuizList(Player player) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
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
	public List<Player> getPlayerList() throws RemoteException {
		return playerList;
	}

}
