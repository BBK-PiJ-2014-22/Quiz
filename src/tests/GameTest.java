package tests;


import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import components.Game;
import components.Player;
import components.PlayerImpl;
import components.Question;
import components.QuestionImpl;
import components.Quiz;
import components.QuizImpl;
import components.QuizStatus;
import components.GameImpl;


/**
 * Before:
 * Sets up active quiz with 3 questions (correct answers are 012). Each question has 5 possible answers
 * 
 * Tests:
 * const1QuizInactive		-> quiz is inactive, throws IllegalStateException 
 * const3QuizActive			-> quiz is active, constructs game
 * const2QuizComplete		-> quiz is inactive, throws IllegalStateException
 *
 * getNextQuestion1First	-> first attempt to GNQ returns proper string
 * getNextQuestion2Several	-> multi attempts to GNQ returns same string
 * getNextQuestion3GameComp	-> GNQ when game complete returns "none"
 * getNextQuestion4QuizComp	-> GNQ when quiz complete returns "none"
 *
 * answerQuestion1First		-> AQ adds to answers list , returns true
 * answerQuestion2Several	-> AQ adds to answers list, increments GNQ, returns true
 * answerQuestion3Last		-> Final AQ completes quiz, totals score, returns true
 * answerQuestion4QuizComp 	-> AQ when quiz complete returns false
 *
 * isCompleted1First		-> isComplete returns false at start
 * isCompleted2Penultimate	-> isComplete returns false at penultimate
 * isCompleted3Completed	-> isComplete returns true at finish
 * 
 * getScoreLive				-> Live quiz
 * getScoreNoneScored		->
 * getScoreFullMarks		->
 * getScoreHalfMarks		->	  
 * 
 * 
 * @author Jamie
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class GameTest {

	Player player;
	Quiz quiz;
	
	@Before
	public void setUp() throws Exception {
		
		this.player = new PlayerImpl(0, "Player 0");
		this.quiz = new QuizImpl(0, "Quiz 0", new PlayerImpl(1, "Quiz Master 0"));
		for (int i = 0 ; i < 4 ; i++)
			this.quiz.addQuestion(createQuestion(i));
	}

	
	 /** const1QuizInactive		-> quiz is inactive, throws IllegalStateException
	 * @throws RemoteException */
	@Test(expected = IllegalStateException.class)
	public void const1QuizInactive() throws RemoteException{
		new GameImpl(player, quiz);
	}
	
	 /** const3QuizActive			-> quiz is active, constructs game
	 * @throws RemoteException */
	@Test
	public void const2QuizIActive() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		Object[] expected = {this.player, this.quiz, 0, false, new ArrayList<Integer>()};
		Object[] actual = {game.getPlayer(), game.getQuiz(), game.getScore(), game.isCompleted(), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}
	
	 /** const2QuizComplete		-> quiz is inactive, throws IllegalStateException
	 * @throws RemoteException */
	@Test(expected = IllegalStateException.class)
	public void const3QuizComplete() throws RemoteException{
		quiz.activate();
		quiz.complete();
		new GameImpl(player, quiz);
	}
	
	 /** getNextQuestion1First	-> first attempt to GNQ returns proper string
	 * @throws RemoteException */
	@Test
	public void  getNextQuestion1First() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		String expected = quiz.getQuestion(0).toString();
		assertEquals(expected, game.getNextQuestion());
	}
	
	 /** getNextQuestion2Several	-> multi attempts to GNQ returns same string
	 * @throws RemoteException */
	@Test
	public void  getNextQuestion2Several() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		String expected = game.getNextQuestion(); //This is to test that no increment is happening from get
		assertEquals(expected, game.getNextQuestion());
	}
	
	 /** getNextQuestion3GameComp	-> GNQ when game complete returns "none"
	 * @throws RemoteException */
	@Test
	public void  getNextQuestion3QuizComplete() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		quiz.complete();
		String expected = "none";
		assertEquals(expected, game.getNextQuestion());
	}
	
	/** getNextQuestion4QIncrement	-> GNQ increments with answers
	 * @throws RemoteException */
	@Test
	public void  getNextQuestion4Increment() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		game.answerQuestion(0);
		String expected = quiz.getQuestion(1).toString();
		assertEquals(expected, game.getNextQuestion());
	}
	
	/** getNextQuestion5QuizComp	-> Returns "none" after game completion
	 * @throws RemoteException */
	@Test
	public void  getNextQuestion5Increment() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		for (int i = 0 ; i < 4 ; i++)
			game.answerQuestion(i);
		String expected = "none";
		assertEquals(expected, game.getNextQuestion());
	}

	
	 /** answerQuestion1First		-> AQ adds to answers list , returns true
	 * @throws RemoteException */
	@Test
	public void answerQuestion1First() throws RemoteException{
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		expectedAnswers.add(0);
		Object[] expected = {true, expectedAnswers};

		quiz.activate();
		Game game = new GameImpl(player, quiz);
	
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		
		assertArrayEquals(expected, actual);
	}
	
	 /** answerQuestion2Several	-> AQ adds to answers list, increments GNQ, returns true
	 * @throws RemoteException */
	@Test
	public void answerQuestion2Several() throws RemoteException{
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		boolean result = false;
	
		for (int i = 0 ; i < 3 ; i++){
			expectedAnswers.add(i);
			result = game.answerQuestion(i);
		}
			
		Object[] expected = {true, expectedAnswers};
		Object[] actual = {result, game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion3Last		-> Final AQ completes quiz,  returns true
	 * @throws RemoteException */
	@Test
	public void answerQuestion3Last() throws RemoteException{
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		boolean result = false;
	
		for (int i = 0 ; i < 4 ; i++){
			expectedAnswers.add(i);
			result = game.answerQuestion(i);
		}
			
		Object[] expected = {true, expectedAnswers};
		Object[] actual = {result, game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion4QuizComp 	-> AQ when quiz complete returns false
	 * @throws RemoteException */
	@Test
	public void answerQuestion4QuizCompl() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		quiz.complete();
		Object[] expected = {false, new ArrayList<Integer>()};
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}
	
	 /** answerQuestion5GameComp 	-> AQ when game complete returns false
	 * @throws RemoteException */
	@Test
	public void answerQuestion5GameCompl() throws RemoteException{
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new GameImpl(player, quiz);
	
		for (int i = 0 ; i < 4 ; i++){
			expectedAnswers.add(i);
			game.answerQuestion(i);
		}	
		//At this point the game should have completed
		Object[] expected = {false, expectedAnswers};
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion6AnswerOOB	-> returns false
	 * @throws RemoteException */
	@Test
	public void answerQuestion6AnswerOOB() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player, quiz);
		
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		expectedAnswers.add(0);
		game.answerQuestion(0);
		
		Object[] expected = {false, expectedAnswers};
		Object[] actual = {game.answerQuestion(6), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}

	
	/** isCompleted1First		-> isComplete returns false at start
	 * @throws RemoteException */
	@Test 
	public void isComplete1First() throws RemoteException{
		 quiz.activate();
		 Game game = new GameImpl(player, quiz);
		 assertEquals(false, game.isCompleted());
	}
	
	/** isCompleted2Penultimate	-> isComplete returns false at penultimate
	 * @throws RemoteException */
	@Test 
	public void isComplete2Penultimate() throws RemoteException{
		 quiz.activate();
		 Game game = new GameImpl(player, quiz);
		 for (int i = 0 ; i < 3 ; i++)
			 game.answerQuestion(i);
		 assertEquals(false, game.isCompleted());
	}
	
	 /** isCompleted3Completed	-> isComplete returns true at finish
	 * @throws RemoteException */
	@Test 
	public void isComplete3Complete() throws RemoteException{
		 quiz.activate();
		 Game game = new GameImpl(player, quiz);
		 for (int i = 0 ; i < 4 ; i++)
			 game.answerQuestion(i);
		 assertEquals(true, game.isCompleted());
	}
	

	 /** getScoreLive			-> Live quiz   
	 * @throws RemoteException */
	@Test
	public void getScore1Live() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player,quiz);
		for (int i = 0; i < 3 ; i++)
			game.answerQuestion(i); //these will be correct answers
		assertEquals(0, game.getScore()); //As score is only calculated at the end
	}
	
	 /** getScoreNoneScored		->
	 * @throws RemoteException */
	@Test
	public void getScore2NoneScored() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player,quiz);
		int[] answers = {1,2,3,0};
		for (int i : answers)
			game.answerQuestion(i); //these will be incorrect answers
		assertEquals(0, game.getScore());
	}
	
	 /** getScoreHalfMarks
	 * @throws RemoteException */
	@Test
	public void getScore3HalfMarks() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player,quiz);
		int[] answers = {0,1,3,0};
		for (int i : answers)
			game.answerQuestion(i); //these will be incorrect answers
		assertEquals(2, game.getScore());
	}
	
	 /** getScoreFullMarks		->	
	 * @throws RemoteException */
	@Test
	public void getScore4FullMarks() throws RemoteException{
		quiz.activate();
		Game game = new GameImpl(player,quiz);
		int[] answers = {0,1,2,3};
		for (int i : answers)
			game.answerQuestion(i); //these will be incorrect answers
		assertEquals(4, game.getScore());
	}
	
	public static Question createQuestion(int questionNumber) throws RemoteException{
		Question result = new QuestionImpl("Question "+questionNumber);
		for (int i = 0 ; i < 5 ; i++)
			result.addAnswer("Answer "+i);
		result.setCorrectAnswer(questionNumber);
		return result;
	}
}
