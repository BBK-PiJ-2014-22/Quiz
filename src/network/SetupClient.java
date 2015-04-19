package network;

import java.rmi.Remote;
import java.rmi.RemoteException;

import components.Player;
import components.Quiz;

public interface SetupClient extends Remote{
	
	public Quiz createQuiz(String name) throws RemoteException;

	
}
