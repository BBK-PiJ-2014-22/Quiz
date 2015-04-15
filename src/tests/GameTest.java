package tests;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import components.Player;
import components.Quiz;
import components.QuizStatus;
import components.Game;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
public class GameTest {

	Player player;
	Quiz quiz;
	Game game;
	
	@Before
	public void setUp() throws Exception {
		
	}

	@Test
	public void testGetNextQuestion() {
		fail("Not yet implemented");
	}

	@Test
	public void testAnswerQuestion() {
		fail("Not yet implemented");
	}

}
