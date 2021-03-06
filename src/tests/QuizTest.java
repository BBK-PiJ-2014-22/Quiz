package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import components.PlayerImpl;
import components.QuestionImpl;
import components.QuizImpl;
import components.QuizStatus;

/**Before will setup a quiz with 1 question added to it.
	 * 
	 * Tests:
	 * 
	 * setQuizName1Inactive		- sets quiz name to String -> quiz name changes
	 * setQuizName2Null 		- sets quiz name to null -> throws NullPointerExpection()
	 * setQuizName3Active 		- sets quiz name to String, quiz is active -> throws IllegalStateException
	 * setQuizName4Complete 	- sets quiz name to String, quiz is complete -> throws IllegalStateException
	 * addQuestion1AddValid		- add a valid question -> question list increases
	 * addQuestion2Active		- add a valid question, quiz is active -> returns false, no change
	 * addQuestion3ValidComplete- add a valid question, quiz is complete -> returns false, no change
	 * addQuestion4Invalid		- add null question -> throws null pointer exception
	 * addQuestion5InvalidActive- add null question -> throws null pointer exception
	 * addQuestion6InvalidComplete- add null question -> throws null pointer exception
	 * getQuestion1IndexInBound - get a question valid index -> returns a copy of the correct question
	 * getQuestion2IndexOOB		- get question out of bounds -> throws IndexOutOfBoundsException
	 * removeQuestionInBound	- remove question in bounds -> returns true and list changed
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
	 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class QuizTest {
	
	QuizImpl quiz;
	
	/**Sets up the quiz to have a single question. It will be inactive.
	 * 
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		this.quiz = new QuizImpl(0, "Quiz 0", new PlayerImpl(0,"QuizMaster 0"));
		this.quiz.addQuestion(new QuestionImpl("Question 0"));
	}
	
	/**Attempts to change the quiz name to a valid string (not null) whilst the quiz is 
	 * inactive. Should result in the quiz name changing.
	 * @throws RemoteException 
	 */
	@Test
	public void setQuizName1Inactive() throws RemoteException{
		String expected = "New Quiz Name";
		this.quiz.setQuizName(expected);
		assertEquals(expected, this.quiz.getQuizName());
	}
	
	/**Attempts to set the quiz name to null. Should throw a NullPointerException*/
	@Test(expected = NullPointerException.class)
	public void setQuizName2Null() throws RemoteException{
		this.quiz.setQuizName(null);
	}
	
	/**Attempts to change the quiz name whilst it is active. Should throw an IllegalArgumentException*/
	@Test(expected = IllegalStateException.class)
	public void setQuizName3Active() throws RemoteException{
		this.quiz.activate();
		this.quiz.setQuizName(null);
	}
	
	/**Attempts to change the quiz name whilst it is active. Should throw an IllegalArgumentException*/
	@Test(expected = IllegalStateException.class)
	public void setQuizName4Completed() throws RemoteException{
		this.quiz.activate();
		this.quiz.complete();
		this.quiz.setQuizName(null);
	}
	
	/**Adds a valid new question, tests that the list increases and the add returns true*/
	@Test
	public void addQuestion1AddValid() throws RemoteException{
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		targetList.add(new QuestionImpl("Question 1"));
		Object[] expected = {true, targetList};
		Object[] actual  = {this.quiz.addQuestion(targetList.get(1)),this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Attempts to add a valid new question but the quiz is Active, tests that the list is unchanged and the add returns false*/
	@Test
	public void addQuestion2Active() throws RemoteException{
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));

		this.quiz.activate();

		Object[] expected = {false, targetList};
		Object[] actual  = {this.quiz.addQuestion(targetList.get(0)),this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Attempts to add a valid new question but the quiz is Complete, tests that the list is unchanged and the add returns false*/
	@Test
	public void addQuestion3Complete() throws RemoteException{
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));

		this.quiz.activate();
		this.quiz.complete();

		Object[] expected = {false, targetList};
		Object[] actual  = {this.quiz.addQuestion(targetList.get(0)),this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Attempts to add a null question. Should throw a NullPointerException*/
	@Test(expected = NullPointerException.class)
	public void addQuestion4Invalid() throws RemoteException{
		this.quiz.addQuestion(null);
	}
	
	/**Attempts to add a null question to an active quiz. Should throw a NullPointerException*/
	@Test(expected = NullPointerException.class)
	public void addQuestion5InvalidActive() throws RemoteException{
		this.quiz.activate();
		this.quiz.addQuestion(null);
	}
	
	/**Attempts to add a null question to an active quiz. Should throw a NullPointerException*/
	@Test(expected = NullPointerException.class)
	public void addQuestion6InvalidComplete() throws RemoteException{
		this.quiz.activate();
		this.quiz.complete();
		this.quiz.addQuestion(null);
	}
	
	/**Gets a question in bounds of the question list, returns the question*/
	@Test
	public void getQuestion1IndexInBound() throws RemoteException{
		QuestionImpl expected = new QuestionImpl("Question 0");
		assertEquals(expected, this.quiz.getQuestion(0));
	}

	/**Tries to get a question out of bounds of the question list, throws IndexOutOfBounds exception*/
	@Test(expected = IndexOutOfBoundsException.class)
	public void getQuestion2IndexOOB() throws RemoteException{
		this.quiz.getQuestion(1);
	}
	
	/**Removes a question in bounds. Tests that the function returns true and the list is changed*/
	@Test
	public void removeQuestion1InBound() throws RemoteException{
		Object[] expected = {true, new ArrayList<QuestionImpl>()};
		Object[] actual = {this.quiz.removeQuestion(0), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Removes a question out of bounds. Tests that the function returns false and the list is unchanged*/
	@Test
	public void removeQuestion2OOB() throws RemoteException{
		List<QuestionImpl> targetList= new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {false, targetList};		
		Object[] actual = {this.quiz.removeQuestion(1), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Removes a question in bounds from active list. Tests that the function returns false and the list is unchanged*/
	@Test
	public void removeQuestion3IBActive() throws RemoteException{
		this.quiz.activate();
		List<QuestionImpl> targetList= new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.removeQuestion(0), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Removes a question in bounds from complete list. Tests that the function returns false and the list is unchanged*/
	@Test
	public void removeQuestion4IBComplete() throws RemoteException{
		this.quiz.activate();
		this.quiz.complete();
		List<QuestionImpl> targetList= new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.removeQuestion(0), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Swaps two questions with IDs that are in bound. Tests taht the function returns true and the list is changed*/
	@Test
	public void swapQuestion1InBounds() throws RemoteException{
		this.quiz.addQuestion(new QuestionImpl("Question 1"));
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 1"));
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {true, targetList};
		Object[] actual = {this.quiz.swapQuestion(0, 1), this.quiz.getQuestionList()};
		
		assertArrayEquals(expected, actual);		
	}
	
	/**Swaps two questions with IDs, first is oob. Tests that the function returns true and the list is changed*/
	@Test
	public void swapQuestion2FirstOOB() throws RemoteException{
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.swapQuestion(1, 0), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Swaps two questions with IDs, first is oob. Tests that the function returns true and the list is changed*/
	@Test
	public void swapQuestion3SecondOOB() throws RemoteException{
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.swapQuestion(0, 1), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Swaps two questions with IDs that are in bound but quiz is active. Tests that the function returns false and the list is unchanged*/
	@Test
	public void swapQuestion4IBActive() throws RemoteException{
		this.quiz.addQuestion(new QuestionImpl("Question 1"));
		this.quiz.activate();
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		targetList.add(new QuestionImpl("Question 1"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.swapQuestion(0, 1), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**Swaps two questions with IDs that are in bound but quiz is active. Tests that the function returns false and the list is unchanged*/
	@Test
	public void swapQuestion5IBComplete() throws RemoteException{
		this.quiz.addQuestion(new QuestionImpl("Question 1"));
		this.quiz.activate();
		this.quiz.complete();
		List<QuestionImpl> targetList = new ArrayList<QuestionImpl>();
		targetList.add(new QuestionImpl("Question 0"));
		targetList.add(new QuestionImpl("Question 1"));
		Object[] expected = {false, targetList};
		Object[] actual = {this.quiz.swapQuestion(0, 1), this.quiz.getQuestionList()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void activateInactive() throws RemoteException{
		Object[] expected = {true, QuizStatus.ACTIVE};
		Object[] actual = {this.quiz.activate(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void activateActive() throws RemoteException{
		this.quiz.activate();
		Object[] expected = {false, QuizStatus.ACTIVE};
		Object[] actual = {this.quiz.activate(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void activateComplete() throws RemoteException{
		this.quiz.activate();
		this.quiz.complete();
		Object[] expected = {false, QuizStatus.COMPLETED};
		Object[] actual = {this.quiz.activate(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void completeInactive() throws RemoteException{
		Object[] expected = {false, QuizStatus.INACTIVE};
		Object[] actual = {this.quiz.complete(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void completeActive() throws RemoteException{
		this.quiz.activate();
		Object[] expected = {true, QuizStatus.COMPLETED};
		Object[] actual = {this.quiz.complete(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
	
	/**	Activates an inactive quiz, checks the status is changed */
	@Test
	public void completeComplete() throws RemoteException{
		this.quiz.activate();
		this.quiz.complete();
		Object[] expected = {false, QuizStatus.COMPLETED};
		Object[] actual = {this.quiz.complete(), this.quiz.getStatus()};
		assertArrayEquals(expected, actual);
	}
}
