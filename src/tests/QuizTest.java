package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**Before will setup a quiz with 1 question added to it.
 * 
 * Tests:
 * 
 * addQuestion1AddValid		- add a valid question -> question list increases
 * addQuestion2AddInvalid	- add null question -> throws null pointer exception
 * addQuestion3ValidActive	- add a valid question, quiz is active -> returns false, no change
 * addQuestion4InvalidActive- add an invalid question, quiz is active -> returns false, no change
 * addQuestion5ValidComplete- add a valid question, quiz is complete -> returns false, no change
 * addQuestion6InvalidComplete-add an invalid question, quiz is active -> returns false, no change
 * getQuestion1IndexInBound - get a question valid index -> returns a copy of the correct question
 * getQuestion2IndexOOB		- get question out of bounds -> throws IndexOutOfBoundsException
 * removeQuestion1InBound	- remove question in bounds -> returns true, list changed
 * removeQuestion2OOB		- remove question oob -> returns false and list unchanged
 * removeQuestion3IBActive  - remove question in bounds, quiz active -> returns false and list unchanged
 * removeQuestion4IBComplete- remove question in bounds, quiz complete -> returns false and list unchanged
 * swapQuestion1InBounds	- swap 2 questions in bounds -> returns true, swapped
 * swapQuestion2FirstOOB	- swap, first index OOB	-> returns false, quiz unchanged
 * swapQuestion3SecondOOB	- swap, second index OOB -> returns false, quiz unchanged
 * swapQuestion4IBActive 	- swap valid index, quiz is active -> returns false, quiz unchanged
 * swapQuestion5IBcomplete 	- swap valid index, quiz is complete -> returns false, quiz unchanged
 * activateInactive			- activate -> returns true, quiz is now flagged as active
 * activateActive			- activate, quiz already active -> return false, quiz unchanged
 * activateComplete			- activate, quiz is already complete -> return false, quiz unchanged
 * completeInactive			- complete, inactive -> returns false, quiz is unchanged
 * completeActive			- complete, quiz already active -> return true, active is set to false and complete to true
 * completeComplete			- activate, quiz is already complete -> return false, quiz unchanged

 * 
 * 
 * @author Jamie MacIver
 *
 */
public class QuizTest {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testComplete() {
		fail("Not yet implemented");
	}

}
