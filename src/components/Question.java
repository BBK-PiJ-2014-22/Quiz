package components;

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
	
	String question;
	List<String> answers;
	int correctAnswer;
	
	public void changeQuestion(String newQuestion){
		
	}
	
	public void addAnswer(String answer){
		
	}
	
	public boolean changeAnswer(int id, String answer){
		return false;
	}
	
	public boolean swapAnswer(int id1, int id2){
		return false;
	}
	
	public void changeCorrectAnswer(int correctAnswer){
		
	}

}
