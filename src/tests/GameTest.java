package tests;


import static org.junit.Assert.*;

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
 * Sets up active quiz with 3 questions (correct answers are 012)
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
 * @author Jamie
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class GameTest {

	Player player;
	Quiz quiz;
	Game game;
	
	@Before
	public void setUp() throws Exception {
		
		this.player = new Player(0, "Player 0");
		this.quiz = new Quiz(0, "Quiz 0", new Player(1, "Quiz Master 0"));
		for (int i = 0 ; i < 4 ; i++)
			this.quiz.addQuestion(createQuestion(i));
		this.game = new Game(this.player, this.quiz);
	}

	@Test
	public void testGetNextQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAnswerQuestion() {
		fail("Not yet implemented");
	}
	
	private Question createQuestion(int questionNumber){
		Question result = new Question("Question "+questionNumber);
		for (int i = 0 ; i < 5 ; i++)
			result.addAnswer("Answer "+i);
		result.setCorrectAnswer(questionNumber);
		return result;
	}
}
