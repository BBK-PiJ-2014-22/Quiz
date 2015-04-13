package tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import components.Question;

/**
 * Tests:
 * 
 * constructorNull 		- A Question cannot be created with a null question
 * setQuestion1Null 	- question cannot be set to null
 * setQuestionString 	- question can be changed to a new String
 * addAnswerString		- answer is added to the end of the list if it is a valid String
 * addAnswerNull		- throws NullPointerException if passed null
 * changeAnswerValid	- returns true and changes the answer if passed valid parameters
 * changeAnswerInvalidID- returns false if ID is not in bounds of list
 * changeAnswerInvalidAnswer- returns false if answer is not valid (null)
 * swapAnswerValid		- returns true and swaps if both are in bounds
 * swapAnswer1OOB		- returns false and does nothing if first ID is out of bounds (OOB)
 * swapAnswer2OOBd		- returns false and does nothing if second ID is out of bounds (OOB)
 * swapAnswerCorrect	- correct answer changes if swapped
 * changeCorrectAnswerPass- passes if answers is in bounds
 * changeCorrectAnswerFail- passes if answers is in bounds
 * removeAnswerPass		- returns true and removes the answer if it is in bounds
 * removeAnswerFail		- returns false if the answer is out of bounds
 * removeAnswerCorrect	- sets correct answer to -1 if the correct answer is removed
 *
 * @author Jamie
 *
 */

public class QuestionTest {

	Question question;
	
	@Before
	public void setUp() throws Exception {
		this.question = new Question("This is a question");
	}

	/**A Question cannot be created with a null question*/
	@Test(expected = NullPointerException.class)
	public void nullConstructor(){
		new Question(null);
	}
	
	/**question cannot be set to null*/
	@Test(expected = NullPointerException.class)
	public void setQuestionNull() {
		this.question.setQuestion(null);
	}
	
	/**question can be changed to a new String*/
	@Test
	public void setQuestionString() {
		String expected = "New Question";
		this.question.setQuestion(expected);
		assertEquals(expected, this.question.getQuestion());
	}

	/**answer is added to the end of the list if it is a valid String*/
	@Test
	public void addAnswerString() {
		
		String[] array= {"Answer 1", "Answer 2", "Answer 3", "Answer 4"};
		List<String> expected = new ArrayList<String>();
		
		for (String answer : array){
			expected.add(answer);
			this.question.addAnswer(answer);
		}
		assertEquals(expected, this.question.getAnswers());
	}
		
	/**answer throws NullPointerException if passed null*/
	@Test(expected = NullPointerException.class)
	public void addAnswerNull() {
		this.question.addAnswer(null);
	}

	/**changes answer if given valid position and valid string*/
	@Test
	public void changeAnswerValid(){
		String newAnswer = "New Answer";
		this.question.addAnswer("Answer 1");
		Object[] expected = {true, newAnswer};
		Object[] actual =  {this.question.changeAnswer(0, newAnswer), this.question.getAnswers().get(0)};
		assertArrayEquals(expected, actual);		
	}
	
	/**returns False if passed an invalid ID (out of bounds)*/
	@Test
	public void changeAnswerInvalidID() {
		assertEquals(false, this.question.changeAnswer(0,"No answer"));
	}
	
	/**returns False if passed an invalid Answer (null)*/
	@Test
	public void changeAnswerInvalidAnswer() {
		this.question.addAnswer("Answer 1");
		assertEquals(false, this.question.changeAnswer(0, null));
	}

	/**returns true and swaps if both are in bounds*/
	@Test
	public void swapAnswerValid() {
		String[] answers= {"Answer 1", "Answer 2"};
		this.question.addAnswer(answers[1]);
		this.question.addAnswer(answers[0]);
		
		List<String> expected = new ArrayList<String>();
		expected.add(answers[0]);
		expected.add(answers[1]);
		
		this.question.swapAnswer(0, 1);
		assertEquals(expected,this.question.getAnswers());
	}

	/**returns false and does nothing if first ID is out of bounds (OOB)*/
	@Test
	public void swapAnswer1OOB() {
		this.question.addAnswer("Answer 1");
		assertEquals(false, this.question.swapAnswer(0, 1));
	}
	
	/**returns false and does nothing if second ID is out of bounds (OOB)*/
	@Test
	public void swapAnswer2OOBd() {
		this.question.addAnswer("Answer 1");
		assertEquals(false, this.question.swapAnswer(1, 0));
	}
	
	/**checks that if one of the swapped answers is the correct answer, 
	 * the correct answer changes
	 */
	@Test
	public void swapAnswerCorrect(){
		this.question.addAnswer("Answer 1");
		this.question.addAnswer("Answer 2");
		this.question.setCorrectAnswer(1);
		this.question.swapAnswer(0,1);
		assertEquals(0, this.question.getCorrectAnswer());
	}
	
	/**passes if answers is in bounds*/
	@Test
	public void changeCorrectAnswerPass() {
		this.question.addAnswer("Answer 1");
		Object[] expected = {true, 0};
		Object[] actual = {this.question.setCorrectAnswer(0), this.question.getCorrectAnswer()};
		assertArrayEquals(expected, actual);
	}
	
	/**fails if answers is out of bounds*/
	@Test
	public void changeCorrectAnswerFail() {
		this.question.addAnswer("Answer 1");
		assertEquals(false, this.question.setCorrectAnswer(1));
	}
	
	/**returns true if answer is in bounds and removes the question*/
	@Test
	public void removeAnswerPass(){
		this.question.addAnswer("Answer 1");
		Object[] expected = {true, new ArrayList<String>()};
		Object[] actual = {this.question.removeAnswer(0), this.question.getAnswers()};
		assertArrayEquals(expected, actual);
	}
	
	/**returns false if answer is out of bounds and no change*/
	@Test
	public void removeAnswerFail(){
		assertEquals(false, this.question.removeAnswer(0));
	}
	
	/**If the correct answer is removed, sets correct answer to -1*/
	@Test
	public void removeAnswerCorrect(){
		this.question.addAnswer("Answer 1");
		this.question.setCorrectAnswer(0);
		this.question.removeAnswer(0);
		assertEquals(-1, this.question.getCorrectAnswer());
	}
}
