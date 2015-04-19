package network;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import components.Player;
import components.PlayerImpl;

public class QuizServerSetupTest {

	SetupInterface setupServer;
	QuizServer quizServer;
	
	@Before
	public void setUp() throws Exception {
		setupServer = new QuizServer();
		quizServer = (QuizServer) setupServer;
	}
	
	@Test
	public void testCreatePlayer1ReturnTest() throws RemoteException {
		Player player = setupServer.createPlayer("Player");
		assertEquals(new PlayerImpl(0, "Player"), player);
	}
	
	@Test
	public void testCreatePlayer2ListTest() throws RemoteException {
		List<Player> expected = new ArrayList<Player>();
		for (int i = 0 ; i < 5 ; i++){
			setupServer.createPlayer("Player"+i);
			expected.add(new PlayerImpl(i, "Player"+i));
		assertEquals(expected, quizServer.getPlayerList());	
		}
	}


	@Test
	public void testCreateQuiz() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetQuizList() {
		fail("Not yet implemented");
	}

	@Test
	public void testLogin() {
		fail("Not yet implemented");
	}



}
