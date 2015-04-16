package tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import components.Player;
import components.Question;
import components.Quiz;
import components.QuizStatus;
import components.Game;


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
		
		this.player = new Player(0, "Player 0");
		this.quiz = new Quiz(0, "Quiz 0", new Player(1, "Quiz Master 0"));
		for (int i = 0 ; i < 4 ; i++)
			this.quiz.addQuestion(createQuestion(i));
	}

	
	 /** const1QuizInactive		-> quiz is inactive, throws IllegalStateException*/
	@Test(expected = IllegalStateException.class)
	public void const1QuizInactive(){
		new Game(player, quiz);
	}
	
	 /** const3QuizActive			-> quiz is active, constructs game*/
	@Test
	public void const2QuizIActive(){
		quiz.activate();
		Game game = new Game(player, quiz);
		Object[] expected = {this.player, this.quiz, 0, false, new ArrayList<Integer>()};
		Object[] actual = {game.getPlayer(), game.getQuiz(), game.getScore(), game.isCompleted(), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}
	
	 /** const2QuizComplete		-> quiz is inactive, throws IllegalStateException*/
	@Test(expected = IllegalStateException.class)
	public void const3QuizComplete(){
		quiz.activate();
		quiz.complete();
		new Game(player, quiz);
	}
	
	 /** getNextQuestion1First	-> first attempt to GNQ returns proper string*/
	@Test
	public void  getNextQuestion1First(){
		quiz.activate();
		Game game = new Game(player, quiz);
		String expected = quiz.getQuestion(0).toString();
		assertEquals(expected, game.getNextQuestion());
	}
	
	 /** getNextQuestion2Several	-> multi attempts to GNQ returns same string*/
	@Test
	public void  getNextQuestion2Several(){
		quiz.activate();
		Game game = new Game(player, quiz);
		String expected = game.getNextQuestion(); //This is to test that no increment is happening from get
		assertEquals(expected, game.getNextQuestion());
	}
	
	 /** getNextQuestion3GameComp	-> GNQ when game complete returns "none"*/
	@Test
	public void  getNextQuestion3QuizComplete(){
		quiz.activate();
		Game game = new Game(player, quiz);
		quiz.complete();
		String expected = "none";
		assertEquals(expected, game.getNextQuestion());
	}
	
	/** getNextQuestion4QIncrement	-> GNQ increments with answers*/
	@Test
	public void  getNextQuestion4Increment(){
		quiz.activate();
		Game game = new Game(player, quiz);
		game.answerQuestion(0);
		String expected = quiz.getQuestion(1).toString();
		assertEquals(expected, game.getNextQuestion());
	}
	
	/** getNextQuestion5QuizComp	-> Returns "none" after game completion*/
	@Test
	public void  getNextQuestion5Increment(){
		quiz.activate();
		Game game = new Game(player, quiz);
		for (int i = 0 ; i < 4 ; i++)
			game.answerQuestion(i);
		String expected = "none";
		assertEquals(expected, game.getNextQuestion());
	}

	
	 /** answerQuestion1First		-> AQ adds to answers list , returns true*/
	@Test
	public void answerQuestion1First(){
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		expectedAnswers.add(0);
		Object[] expected = {true, expectedAnswers};

		quiz.activate();
		Game game = new Game(player, quiz);
	
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		
		assertArrayEquals(expected, actual);
	}
	
	 /** answerQuestion2Several	-> AQ adds to answers list, increments GNQ, returns true*/
	@Test
	public void answerQuestion2Several(){
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new Game(player, quiz);
		boolean result = false;
	
		for (int i = 0 ; i < 3 ; i++){
			expectedAnswers.add(i);
			result = game.answerQuestion(i);
		}
			
		Object[] expected = {true, expectedAnswers};
		Object[] actual = {result, game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion3Last		-> Final AQ completes quiz,  returns true*/
	@Test
	public void answerQuestion3Last(){
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new Game(player, quiz);
		boolean result = false;
	
		for (int i = 0 ; i < 4 ; i++){
			expectedAnswers.add(i);
			result = game.answerQuestion(i);
		}
			
		Object[] expected = {true, expectedAnswers};
		Object[] actual = {result, game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion4QuizComp 	-> AQ when quiz complete returns false*/
	@Test
	public void answerQuestion4QuizCompl(){
		quiz.activate();
		Game game = new Game(player, quiz);
		quiz.complete();
		Object[] expected = {false, new ArrayList<Integer>()};
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}
	
	 /** answerQuestion5GameComp 	-> AQ when game complete returns false*/
	@Test
	public void answerQuestion5GameCompl(){
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		quiz.activate();
		Game game = new Game(player, quiz);
	
		for (int i = 0 ; i < 4 ; i++){
			expectedAnswers.add(i);
			game.answerQuestion(i);
		}	
		//At this point the game should have completed
		Object[] expected = {false, expectedAnswers};
		Object[] actual = {game.answerQuestion(0), game.getAnswers()};
		
		assertArrayEquals(expected, actual);	
	}
	
	 /** answerQuestion6AnswerOOB	-> returns false*/
	@Test
	public void answerQuestion6AnswerOOB(){
		quiz.activate();
		Game game = new Game(player, quiz);
		
		List<Integer> expectedAnswers = new ArrayList<Integer>();
		expectedAnswers.add(0);
		game.answerQuestion(0);
		
		Object[] expected = {false, expectedAnswers};
		Object[] actual = {game.answerQuestion(6), game.getAnswers()};
		assertArrayEquals(expected, actual);
	}

	
	
	/*
	 *
	 * isCompleted1First		-> isComplete returns false at start
	 * isCompleted2Penultimate	-> isComplete returns false at penultimate
	 * isCompleted3Completed	-> isComplete returns true at finish
	 * 
	 * getScoreLive				-> Live quiz
	 * getScoreNoneScored		->
	 * getScoreFullMarks		->
	 * getScoreHalfMarks		->					
	*/

	public static Question createQuestion(int questionNumber){
		Question result = new Question("Question "+questionNumber);
		for (int i = 0 ; i < 5 ; i++)
			result.addAnswer("Answer "+i);
		result.setCorrectAnswer(questionNumber);
		return result;
	}
}
