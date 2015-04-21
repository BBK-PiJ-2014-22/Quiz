package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import network.PlayerInterface;
import network.QuizServer;

import org.junit.Before;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import components.Game;
import components.GameImpl;
import components.Player;
import components.PlayerImpl;
import components.Quiz;
import components.QuizImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class QuizServerPlayerInterfaceTest {

	PlayerInterface playerServer;
	QuizServer quizServer;
	
	
	@Before
	public void setUp() throws Exception {
		playerServer = new QuizServer();
		quizServer   = (QuizServer) playerServer;
	}

	@Test
	public void testCreatePlayer1ReturnTest() throws RemoteException {
		Player player = playerServer.createPlayer("Player");
		assertEquals(new PlayerImpl(0, "Player"), player);
	}
	
	@Test
	public void testCreatePlayer2ListTest() throws RemoteException {
		List<Player> expected = new ArrayList<Player>();
		for (int i = 0 ; i < 5 ; i++){
			playerServer.createPlayer("Player"+i);
			expected.add(new PlayerImpl(i, "Player"+i));
		}
		List<Player> actual = quizServer.getFullPlayerList();
		assertEquals(expected, actual);
	}
	
	@Test (expected = NullPointerException.class)
	public void testCreatePlayer3Null() throws RemoteException {
		playerServer.createPlayer(null);
	}
	
	@Test
	public void testLogin1Pass() throws RemoteException {
		for (int i = 0 ; i < 5 ; i++)
			playerServer.createPlayer("Player"+i);
		Player expected = new PlayerImpl(3, "Player3");
		Player actual = playerServer.login(3,  "Player3");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin2FailMismatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			playerServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = playerServer.login(3,  "Player4");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin3FailIDNoMatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			playerServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = playerServer.login(6,  "Player0");
		assertEquals(expected, actual);
	}
	
	@Test
	public void testLogin4FailNameNoMatch() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			playerServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = playerServer.login(0,  "Player6");
		assertEquals(expected, actual);
	}
	
	@Test 
	public void testLogin5FailNull() throws RemoteException{
		for (int i = 0 ; i < 5 ; i++)
			playerServer.createPlayer("Player"+i);
		Player expected = null;
		Player actual = playerServer.login(0,  null);
		assertEquals(expected, actual);
	}


	@Test
	public void startNewGame1Works() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 quiz.activate();
		 Player player = playerServer.createPlayer("Player0");
		 Game actual = playerServer.startNewGame(player, quiz);
		 Game expected = new GameImpl(player, quiz);
		 assertEquals(expected, actual);
	}
	
	 @Test(expected = IllegalStateException.class)
	 public void startNewGame2QuizInactive() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 Player player = playerServer.createPlayer("Player0");
		 playerServer.startNewGame(player, quiz);
	 }
	 
	 @Test(expected = IllegalStateException.class)
	 public void startNewGame3QuizComplete() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 quiz.activate();
		 quiz.complete();
		 Player player = playerServer.createPlayer("Player0");
		 playerServer.startNewGame(player, quiz);
	 }
	
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void startNewGame4UnknownPlayer() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 quiz.activate();
		 playerServer.startNewGame(new PlayerImpl(1, "Player0"), quiz);
	 }
	 
	 @Test(expected = IllegalArgumentException.class)
	 public void startNewGame5UnknownPlayer() throws RemoteException{
		 Quiz quiz = new QuizImpl(0, "Quiz2", quizServer.createPlayer("QuizMaster"));
		 quiz.activate();
		 Player player = playerServer.createPlayer("Player0");
		 playerServer.startNewGame(player, quiz);
	 }
	 
	 @Test(expected = NullPointerException.class)
	 public void startNewGame6NullQuiz() throws RemoteException{
		 Player player = playerServer.createPlayer("Player0");
		 playerServer.startNewGame(player, null);
	 }

	 @Test(expected = NullPointerException.class)
	 public void startNewGame7NullPlayer() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 quiz.activate();
		 playerServer.startNewGame(null, quiz);
	 }
 
	@Test(expected = IllegalStateException.class)
	public void startNewGame8GameExists() throws RemoteException{
		 Quiz quiz = quizServer.createQuiz( quizServer.createPlayer("QuizMaster"), "Quiz2");
		 quiz.activate();
		 Player player = playerServer.createPlayer("Player0");
		 playerServer.startNewGame(player, quiz);
		 new GameImpl(player, quiz);
		 new GameImpl(player, quiz);	 
	}


	/* testGameList1EmptyPlayerList
	 * testGameList2SeveralGames
	 */
	@Test
	public void testGetGameList() {
		fail("Not yet implemented");
	}
	

}
