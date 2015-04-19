package components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Question extends Remote {
	
	public void addAnswer(String answer) throws RemoteException;
	
	public boolean changeAnswer(int answerID, String newAnswer) throws RemoteException;
	
	public List<String> getAnswers() throws RemoteException;
	
	public int getCorrectAnswer() throws RemoteException;
	
	public String getQuestion() throws RemoteException;
	
	public boolean removeAnswer(int answerID) throws RemoteException;
	
	public boolean setCorrectAnswer(int answerID) throws RemoteException;
	
	public void setQuestion(String question) throws RemoteException;
	
	public boolean swapAnswer(int id1 , int id2) throws RemoteException;
	
	
	
	
	

}
