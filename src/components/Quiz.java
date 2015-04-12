package components;

import java.util.List;
import java.util.ArrayList;

public class Quiz {

	private int quizID;
	private Player quizMaster;
	private String quizName;
	private List<Question> questions;
	private boolean active;
	
	//Getters/Setters
	public String getQuizName() {
		return quizName;
	}
	public void setQuizName(String quizName) {
		this.quizName = quizName;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
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
				+ ", active=" + active + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (active ? 1231 : 1237);
		result = prime * result
				+ ((questions == null) ? 0 : questions.hashCode());
		result = prime * result + quizID;
		result = prime * result
				+ ((quizMaster == null) ? 0 : quizMaster.hashCode());
		result = prime * result
				+ ((quizName == null) ? 0 : quizName.hashCode());
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
		if (active != other.active)
			return false;
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
		return true;
	}
	
	//Main Quiz methods
	
	

	
	
	
	


/*+boolean addQuestion(question)
+boolean swapQuestion(int, int)
+boolean modifiyQuestion(question)
+void activate()
+void deactivate()
*/
	
}
