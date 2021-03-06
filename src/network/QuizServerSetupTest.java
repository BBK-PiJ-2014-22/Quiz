package network;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import components.Player;
import components.PlayerImpl;
import components.Quiz;
import components.QuizImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING) 
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
		}
		List<Player> actual = quizServer.getFullPlayerList();
		assertEquals(expected, actual);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreatePlayer3Null() throws RemoteException {
		setupServer.createPlayer(null);
	}
	
	@Test
	public void testLogin1Pass() throws RemoteException {
		for (int i = 0 ; i < 5 ; i++)
			setupServer.createPlayer("Player"+i);
		Player expected = new PlayerImpl(3, "Player3");
		Player actual = setupServer.login(3,  "Player3");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin2FailMismatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			setupServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = setupServer.login(3,  "Player4");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin3FailIDNoMatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			setupServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = setupServer.login(6,  "Player0");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin4FailNameNoMatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			setupServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = setupServer.login(0,  "Player6");
		assertEquals(expected, actual);
	}
	
	@Test 
	public void testLogin5FailNull() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			setupServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = setupServer.login(0,  null);
		assertEquals(expected, actual);
	}

	@Test
	public void testCreateQuiz1PassSingle() throws RemoteException{
		Player player = setupServer.createPlayer("Player0");
		Quiz expected = new QuizImpl(0, "New Quiz", player);
		Quiz actual = setupServer.createQuiz(player, "New Quiz");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testCreateQuiz2PassMultiple() throws RemoteException{
		
		//TODO - rewrite to test against the full list existing
		Player player0 = setupServer.createPlayer("Player0");
		Player player1 = setupServer.createPlayer("Player1");
		Quiz expected = new QuizImpl(3, "New Quiz3", player1);
		setupServer.createQuiz(player0, "New Quiz");
		setupServer.createQuiz(player0, "New Quiz");
		setupServer.createQuiz(player0, "New Quiz");
		Quiz actual = setupServer.createQuiz(player1, "New Quiz3");

		assertEquals(expected, actual);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateQuiz2FailNullName() throws RemoteException{
		Player player = setupServer.createPlayer("Player0");
		setupServer.createQuiz(player, null);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreateQuiz3FailNullPlayer() throws RemoteException{
		setupServer.createQuiz(null, "New Quiz");
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void testCreateQuiz4FailPlayerNotInServer() throws RemoteException{
		setupServer.createPlayer("Player0");//Note that this will be equal but not identical
		setupServer.createQuiz(new PlayerImpl(0, "Player0"), "New Quiz");
	}

}
