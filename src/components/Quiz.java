package components;

//TODO - Work out how to deal with questions for active quizzes. At present a question can be altered in principle (Add lock to question?)

import java.util.List;
import java.util.ArrayList;

public class Quiz {

	private int quizID; //Set only by constructor
	private Player quizMaster; //Set only be constructor
	private String quizName;
	private List<Question> questions;
	private QuizStatus quizStatus;
	
	public Quiz(int id, String name, Player quizMaster) {
		this.quizID = id;
		this.quizMaster = quizMaster;
		this.quizName = name;
		this.questions = new ArrayList<Question>();
		this.quizStatus = QuizStatus.INACTIVE;
	}
	
	
	//Getters/Setters
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		if (this.quizStatus != QuizStatus.INACTIVE)
			throw new IllegalStateException();
		else
			this.quizName = quizName;
	}

	public QuizStatus getStatus(){
		return quizStatus;
	}
	
	public int getQuizID() {
		return quizID;
	}
	public Player getQuizMaster() {
		return quizMaster;
	}
	public List<Question> getQuestions() {
		return questions;
	}
	
	//standard methods
	@Override
	public String toString() {
		return "Quiz [quizID=" + quizID + ", quizMaster=" + quizMaster
				+ ", quizName=" + quizName + ", questions=" + questions
				+ ", quizStatus=" + quizStatus + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + quizID;
		result = prime * result
				+ ((quizMaster == null) ? 0 : quizMaster.hashCode());
		result = prime * result
				+ ((quizName == null) ? 0 : quizName.hashCode());
		result = prime * result + ((quizStatus == null) ? 0 : quizStatus.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quiz other = (Quiz) obj;
		if (questions == null) {
			if (other.questions != null)
				return false;
		} else if (!questions.equals(other.questions))
			return false;
		if (quizID != other.quizID)
			return false;
		if (quizMaster == null) {
			if (other.quizMaster != null)
				return false;
		} else if (!quizMaster.equals(other.quizMaster))
			return false;
		if (quizName == null) {
			if (other.quizName != null)
				return false;
		} else if (!quizName.equals(other.quizName))
			return false;
		if (quizStatus != other.quizStatus)
			return false;
		return true;
	}
	
	//Main Quiz methods
	
	public boolean addQuestion(Question question){
		return false;
	}
	
	public Question getQuestion(int id){
		return null;
	}
	
	public boolean removeQuestion(int id){
		return false;
	}

	public boolean swapQuestion(int id1, int id2){
		return false;
	}
	
	public boolean activate(){
		if (this.quizStatus != QuizStatus.INACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.ACTIVE;
			return true;
		}
	}
	
	public boolean complete(){
		if (this.quizStatus != QuizStatus.ACTIVE)
			return false;
		else{
			this.quizStatus = QuizStatus.ACTIVE;
			return true;
		}
	}
}
