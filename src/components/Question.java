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
	List<String> answers;
	private int correctAnswer;
	private int id; //-1 represents unset
	
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
	
	/**Returns the String question
	 * 
	 * @return String question
	 */
	public String getQuestion() {
		return question;
	}

	/**Sets the main question heading. Does not accept null
	 * 
	 * @param question new question to set
	 * @throws NullPointerException if null is passed to the method
	 */
	public void setQuestion(String question) {
		if (question.equals(null))
			throw new NullPointerException();
		else{
			this.question = question;
		}
	}

	/**Returns the correct answer. -1 represents unset
	 * 
	 * @return
	 */
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**Sets the correct answer and returns true. If the int passed does not match to 
	 * an answer, it will not change the answer and will return false.
	 * 
	 * @param correctAnswer position of the correct answer in the answerList
	 * @return true if changed, false if not
	 */
	public boolean setCorrectAnswer(int correctAnswer) {
		if (correctAnswer >= 0 && correctAnswer < this.answers.size()){
			this.correctAnswer = correctAnswer;
			return true;
		}else{
			return false;
		}
	}

	public List<String> getAnswers() {
		return answers;
	}
	
	public void addAnswer(String answer){
		if (answer == null)
			throw new NullPointerException();
		else
			this.answers.add(answer);
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
