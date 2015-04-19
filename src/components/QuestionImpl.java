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
public class QuestionImpl implements Question {
	
	private String question;
	List<String> answers;
	private int correctAnswer; //-1 represents unset
	
	/**Standard constructor for the question object. Will create a question with no answers 
	 * with the correct answer set to -1
	 * 
	 * @param id the questions ID (creating class should guarantee uniqueness)
	 * @param question the question the be displayed
	 * @throws NullPointerException if question is null
	 */
	public QuestionImpl(String question) {
		if (question.equals(null))
			throw new NullPointerException();
		this.question = question;
		this.correctAnswer = -1;
		this.answers = new ArrayList<String>();
	}
	
	/**Returns the String question
	 * 
	 * @return String question
	 */
	@Override
	public String getQuestion() {
		return question;
	}

	/**Sets the main question heading. Does not accept null
	 * 
	 * @param question new question to set
	 * @throws NullPointerException if null is passed to the method
	 */
	@Override
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
	@Override
	public int getCorrectAnswer() {
		return correctAnswer;
	}

	/**Sets the correct answer and returns true. If the int passed does not match to 
	 * an answer, it will not change the answer and will return false.
	 * 
	 * @param correctAnswer position of the correct answer in the answerList
	 * @return true if changed, false if not
	 */
	@Override
	public boolean setCorrectAnswer(int correctAnswer) {
		if (correctAnswer >= 0 && correctAnswer < this.answers.size()){
			this.correctAnswer = correctAnswer;
			return true;
		}else{
			return false;
		}
	}
	
	/**Returns a list of all answers the question currently holds
	 * 
	 * @return list of all answer the question holds
	 */
	@Override
	public List<String> getAnswers() {
		return answers;
	}
	
	/**Adds an answer to the list.
	 * 
	 * @param answer string, not null, of the answer
	 * @throws NullPointerException if the string is Null
	 */
	@Override
	public void addAnswer(String answer){
		if (answer == null)
			throw new NullPointerException();
		else
			this.answers.add(answer);
	}
	
	/**Removes an answer. Will return true if successful. If the answer
	 * removed was also the correct answer, it will reset the correct answer
	 * to -1 (representing unset)
	 * 
	 * @param id id/position in list of the answer to remove
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean removeAnswer(int id){
		try{
			this.answers.remove(id);
			if (id == this.correctAnswer)
				this.correctAnswer = -1;
			return true;
		}catch (IndexOutOfBoundsException ex){
			return false;
		}
	}
	
	/**Changes the answer at position ID to the new string. Note that this will
	 * have no effect on whether it is the correct answer or not. Will return true 
	 * if successful, false if either out of bounds or not a valid answer (null)
	 * 
	 * @param id int representing position of the answer to change
	 * @param answer string of the new answer
	 * @return boolean, true if succesful, false otherwise
	 */
	@Override
	public boolean changeAnswer(int id, String answer){
		try{
			if (answer == null)
				throw new NullPointerException();
			else{
				this.answers.remove(id);
				this.answers.add(id, answer);	
				return true;
			}
		}catch (NullPointerException|IndexOutOfBoundsException ex){
			return false;
		}
	}
	
	/**Swaps the order of two answers within the list. Will return true
	 * if successful, false if not (either index out of bounds both IDs
	 * are the same)
	 * 
	 * @param id1 first answer to swap
	 * @param id2 second answer to swap
	 * @return true if successful, false if not.
	 */
	@Override
	public boolean swapAnswer(int id1, int id2){
		if (id1 == id2)
			return false;
		else{
			try{
				String string1 = this.answers.get(id1);
				String string2 = this.answers.get(id2);
				this.answers.remove(id1);
				this.answers.add(id1, string2);
				this.answers.remove(id2);
				this.answers.add(id2, string1);
				
				if (this.getCorrectAnswer() == id1)
					this.setCorrectAnswer(id2);
				else if (this.getCorrectAnswer() == id2)
					this.setCorrectAnswer(id1);

				return true;
			}catch (IndexOutOfBoundsException ex){
				return false;
			}
		}	
	}


	@Override
	public String toString() {
		String string = this.question;
		for (int i = 0 ; i < this.answers.size() ; i++)
			string += "\n	"+i+": "+this.answers.get(i);
		return string;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((answers == null) ? 0 : answers.hashCode());
		result = prime * result + correctAnswer;
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
		QuestionImpl other = (QuestionImpl) obj;
		if (answers == null) {
			if (other.answers != null)
				return false;
		} else if (!answers.equals(other.answers))
			return false;
		if (correctAnswer != other.correctAnswer)
			return false;
		if (question == null) {
			if (other.question != null)
				return false;
		} else if (!question.equals(other.question))
			return false;
		return true;
	}
	
}
