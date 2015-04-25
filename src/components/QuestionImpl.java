package components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

import network.Log;

/**The question is the fundamental element of the Quiz. It should be stored, created and accessed
 * through a Quiz. It consists of an ID, a question (the string displayed to the player when they
 * access the question in the quiz), a list of possible answers and the correct answer (int of the 
 * position in the list). 
 * 
 * @author Jamie MacIver
 *
 */
public class QuestionImpl extends UnicastRemoteObject implements Question {
	
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
	public QuestionImpl(String question) throws RemoteException{
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
	public String getQuestion() throws RemoteException{
		return question;
	}

	/**Sets the main question heading. Does not accept null
	 * 
	 * @param question new question to set
	 * @throws NullPointerException if null is passed to the method
	 */
	@Override
	public void setQuestion(String question) throws RemoteException{
		if (question.equals(null))
			throw new NullPointerException();
		else{
			Log.log(this.question+" changed to "+question);
			this.question = question;
		}
	}

	/**Returns the correct answer. -1 represents unset
	 * 
	 * @return
	 */
	@Override
	public int getCorrectAnswer() throws RemoteException{
		return correctAnswer;
	}

	/**Sets the correct answer and returns true. If the int passed does not match to 
	 * an answer, it will not change the answer and will return false.
	 * 
	 * @param correctAnswer position of the correct answer in the answerList
	 * @return true if changed, false if not
	 */
	@Override
	public boolean setCorrectAnswer(int correctAnswer) throws RemoteException{
		if (correctAnswer >= 0 && correctAnswer < this.answers.size()){
			Log.log(this.question+" correct answer changed from"+this.correctAnswer+" to "+correctAnswer);
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
	public List<String> getAnswers() throws RemoteException{
		return answers;
	}
	
	/**Adds an answer to the list.
	 * 
	 * @param answer string, not null, of the answer
	 * @throws NullPointerException if the string is Null
	 */
	@Override
	public void addAnswer(String answer) throws RemoteException{
		if (answer == null)
			throw new NullPointerException();
		else
			this.answers.add(answer);
			Log.log(this.question+" answer added:"+answer);

	}
	
	/**Removes an answer. Will return true if successful. If the answer
	 * removed was also the correct answer, it will reset the correct answer
	 * to -1 (representing unset)
	 * 
	 * @param id id/position in list of the answer to remove
	 * @return true if successful, false otherwise
	 */
	@Override
	public boolean removeAnswer(int id) throws RemoteException{
		try{
			this.answers.remove(id);
			Log.log(this.question+" answer removed:"+id);
			if (id == this.correctAnswer){
				this.correctAnswer = -1;
				Log.log(this.question+" correct answer reset");
			}
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
	 * @return boolean, true if succesfull, false otherwise
	 */
	@Override
	public boolean changeAnswer(int id, String answer) throws RemoteException{
		try{
			if (answer == null)
				throw new NullPointerException();
			else{
				this.answers.remove(id);
				this.answers.add(id, answer);	
				Log.log(this.question+" answer changed "+id+":"+answer);
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
	public boolean swapAnswer(int id1, int id2) throws RemoteException{
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
				Log.log(this.question+" answers swapped "+id1+":"+id2);
				
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
	public String display() throws RemoteException{
		String string = this.question;
		for (int i = 0 ; i < this.answers.size() ; i++)
			string += "\n	"+i+": "+this.answers.get(i);
		return string;
	}
	
}
