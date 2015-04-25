package components;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface Quiz extends Remote {
	
	public boolean activate() throws RemoteException;
	
	public boolean addQuestion(String newQuestion) throws RemoteException;
	
	public boolean addQuestion(Question newQuestion) throws RemoteException;
	
	public boolean complete() throws RemoteException;
	
	public Question getQuestion(int id) throws RemoteException;
	
	public List<Question> getQuestionList() throws RemoteException;
	
	public int getQuizID() throws RemoteException;
	
	public Player getQuizMaster() throws RemoteException;
	
	public String getQuizName() throws RemoteException;
	
	public QuizStatus getStatus() throws RemoteException;
	
	public boolean removeQuestion(int id) throws RemoteException;
	
	public void setQuizName(String name) throws RemoteException;
	
	public boolean swapQuestion(int id1 , int id2) throws RemoteException;
	
	public String display() throws RemoteException;
	

}
