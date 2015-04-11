package tests;

import static org.junit.Assert.*;

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
 *
 * @author Jamie
 *
 */

public class QuestionTest {

	Question question;
	
	@Before
	public void setUp() throws Exception {
		this.question = new Question(0, "This is a question");
	}

	/**A Question cannot be created with a null question*/
	@Test(expected = NullPointerException.class)
	public void nullConstructor(){
		new Question(1, null);
	}
	
	/**question cannot be set to null*/
	@Test
	public void setQuestionNull() {
		fail("Not yet implemented");
	}
	
	/**question can be changed to a new String*/
	@Test
	public void setQuestionString() {
		fail("Not yet implemented");
	}

	/**answer is added to the end of the list if it is a valid String*/
	@Test
	public void addAnswerString() {
		fail("Not yet implemented");
	}
	
	/**answer throws NullPointerException if passed null*/
	@Test(expected = NullPointerException.class)
	public void addAnswerNull() {
		fail("Not yet implemented");
	}

	/**changes answer if given valid position and valid string*/
	@Test
	public void changeAnswerValid() {
		fail("Not yet implemented");
	}
	
	/**returns False if passed an invalid ID (out of bounds)*/
	@Test
	public void changeAnswerInvalidID() {
		fail("Not yet implemented");
	}
	
	/**returns False if passed an invalid Answer (null)*/
	@Test
	public void changeAnswerInvalidAnswer() {
		fail("Not yet implemented");
	}

	/**returns true and swaps if both are in bounds*/
	@Test
	public void swapAnswerValid() {
		fail("Not yet implemented");
	}

	/**returns false and does nothing if first ID is out of bounds (OOB)*/
	@Test
	public void swapAnswer1OOB() {
		fail("Not yet implemented");
	}
	
	/**returns false and does nothing if second ID is out of bounds (OOB)*/
	@Test
	public void swapAnswer2OOBd() {
		fail("Not yet implemented");
	}
	
	/**checks that if one of the swapped answers is the correct answer, 
	 * the correct answer changes
	 */
	public void swapAnswerCorrect(){
		
	}
	
	/**passes if answers is in bounds*/
	@Test
	public void changeCorrectAnswerPass() {
		fail("Not yet implemented");
	}
	
	/**passes if answers is in bounds*/
	@Test
	public void changeCorrectAnswerFail() {
		fail("Not yet implemented");
	}

}
