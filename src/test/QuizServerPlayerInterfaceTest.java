package test;

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

import components.Player;
import components.PlayerImpl;

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
	public void testStartNewGame() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetGameList() {
		fail("Not yet implemented");
	}
	

}
