package network;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import components.*;

import components.Quiz;

public class QuizServer extends UnicastRemoteObject implements SetupClient {

	public QuizServer() throws RemoteException{
		super();
	}
	
	@Override
	public Quiz createQuiz(String name) throws RemoteException {
			return (Quiz) new QuizImpl(0, name, (Player)new PlayerImpl(0,"Jamie"));
	}

}
