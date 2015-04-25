package components;

//TODO - add log and JavaDoc

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ArrayList;

import network.Log;

public class QuizImpl extends UnicastRemoteObject implements Quiz{

	private int quizID; //Set only by constructor
	private Player quizMaster; //Set only be constructor
	private String quizName;
	private List<Question> questions;
	private QuizStatus quizStatus;
	
	public QuizImpl(int id, String name, Player quizMaster) throws RemoteException {
		this.quizID = id;
		this.quizMaster = quizMaster;
		this.quizName = name;
		this.questions = new ArrayList<Question>();
		this.quizStatus = QuizStatus.INACTIVE;
	}
	
	
	//Getters/Setters
	@Override
	public String getQuizName() throws RemoteException {
		return quizName;
	}
	
	@Override
	public void setQuizName(String quizName) throws RemoteException{
		if (this.quizStatus != QuizStatus.INACTIVE)
			throw new IllegalStateException();
		else if (quizName == null)
			throw  new NullPointerException();
		else
			this.quizName = quizName;
	}

	@Override
	public QuizStatus getStatus() throws RemoteException{
		return quizStatus;
	}
	
	@Override
	public int getQuizID()throws RemoteException {
		return quizID;
	}
	@Override
	public Player getQuizMaster() throws RemoteException{
		return quizMaster;
	}
	
	@Override
	public List<Question> getQuestionList() throws RemoteException{
		return questions;
	}
	
	//standard methods
	@Override
	public String display() throws RemoteException {
		//TODO - Throwing a null pointer exception if quizlist is empty 
		return "Quiz [quizID=" + quizID + ", quizMaster=" + quizMaster.display()
				+ ", quizName=" + quizName + ", quizStatus=" + quizStatus + 
				"questionCount="+ questions.size()+"]";
	}

	
	//Main Quiz methods
	
	@Override
	public boolean addQuestion(Question question)throws RemoteException{
		if (question.equals(null))
			throw new NullPointerException();
		else if (this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.questions.add(question);
			Log.log(quizName+": question added - "+question.display());
			return true;
		}
	}
	
	@Override
	public boolean addQuestion(String question) throws RemoteException{
		return this.addQuestion(new QuestionImpl(question));
	}
	
	@Override
	public Question getQuestion(int id) throws RemoteException{
		return this.questions.get(id);
	}
	
	@Override
	public boolean removeQuestion(int id) throws RemoteException{
		if (id < 0 || id >= this.questions.size() || this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.questions.remove(id);
			Log.log(quizName+": question removed - "+id);
			return true;
		}
	}

	@Override
	public boolean swapQuestion(int id1, int id2) throws RemoteException{
		if (id1 < 0 || id1 >= this.questions.size() ||
			id2 < 0 || id2 >= this.questions.size() ||
			this.quizStatus != QuizStatus.INACTIVE) {
			return false;
		}else{
			Question q1 = this.questions.get(id1);
			Question q2 = this.questions.get(id2);
			this.questions.remove(id1);
			this.questions.add(id1, q2);
			this.questions.remove(id2);
			this.questions.add(id2,q1);
			Log.log(quizName+": question swapped - "+id1+" with "+id2);
			return true;
		}
	}
	
	@Override
	public boolean activate() throws RemoteException{
		if (this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.ACTIVE;
			Log.log(quizName+": Activated");
			return true;
		}
	}
	
	@Override
	public boolean complete() throws RemoteException{
		if (this.quizStatus != QuizStatus.ACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.COMPLETED;
			Log.log(quizName+": Completed");
			return true;
		}
	}
}
