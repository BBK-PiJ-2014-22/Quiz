package components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Game extends Remote {

	public boolean answerQuestion(int answer) throws RemoteException;
	
	public List<Integer> getAnswers() throws RemoteException;
	
	public String getNextQuestion() throws RemoteException;
	
	public Player getPlayer() throws RemoteException;
	
	public Quiz getQuiz() throws RemoteException;
	
	public int getScore() throws RemoteException;
	
	public boolean isCompleted() throws RemoteException;
	
	public String display() throws RemoteException;
	
}
