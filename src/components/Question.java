package components;

import java.util.ArrayList;
import java.util.List;

/**The question is the fundamental element of the Quiz. It should be stored, created and accessed
 * through a Quiz. It consists of an ID, a question (the string displayed to the player when they
 * access the question in the quiz), a list of possible answers and the correct answer (int of the 
 * position in the list). 
 * 
 * @author Jamie MacIver
 *
 */
public class Question {
	
	private String question;
	private List<String> answers;
	private int correctAnswer;
	private int id;
	
	/**Standard constructor for the question object. Will create a question with no answers 
	 * with the correct answer set to -1
	 * 
	 * @param id the questions ID (creating class should guarantee uniqueness)
	 * @param question the question the be displayed
	 * @throws NullPointerException if question is null
	 */
	public Question(int id, String question) {
		
		if (question.equals(null))
			throw new NullPointerException();
			
		this.id = id;
		this.question = question;
		this.correctAnswer = -1;
		this.answers = new ArrayList<String>();
	}
	
	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public boolean setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
		return false;
	}

	public List<String> getAnswers() {
		return answers;
	}
	
	public void addAnswer(String answer){
		
	}
	
	public boolean removeAnswer(int id){
		return false;
	}
		
	public boolean changeAnswer(int id, String answer){
		return false;
	}
	
	public boolean swapAnswer(int id1, int id2){
		return false;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Question [question=" + question + ", answers=" + answers
				+ ", correctAnswer=" + correctAnswer + ", id=" + id + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + correctAnswer;
		result = prime * result + id;
		result = prime * result
				+ ((question == null) ? 0 : question.hashCode());
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
		Question other = (Question) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (correctAnswer != other.correctAnswer)
			return false;
		if (id != other.id)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	
}
