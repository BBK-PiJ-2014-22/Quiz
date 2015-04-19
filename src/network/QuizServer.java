package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import components.*;

public class QuizServer extends UnicastRemoteObject implements SetupInterface {

	public QuizServer() throws RemoteException{
		super();
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Player createPlayer(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**Returns full list of players*/
	public List<Player> getPlayerList() throws RemoteException {
		return null;
	}



}
