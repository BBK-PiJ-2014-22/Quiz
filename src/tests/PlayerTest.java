package tests;

import static org.junit.Assert.*;

import java.rmi.RemoteException;

import org.junit.Test;

import components.Player;
import components.PlayerImpl;

/**Tests for the Player object. Apart from getters Test, ensures that a Player cannot have  a null name
 * 
 * @author Jamie MacIver
 *
 */
public class PlayerTest {

	/**Test get name 
	 * @throws RemoteException */
	@Test
	public void getNameTest() throws RemoteException{
		Player player = new PlayerImpl(1, "Jamie");
		assertEquals("Jamie", player.getName());
	}

	/**Test get ID
	 * @throws RemoteException */
	@Test
	public void getIDtest() throws RemoteException{
		Player player = new PlayerImpl(1, "Jamie");
		assertEquals(1, player.getId());
	}

	/**Tests toString*/
	@Test
	public void displayTest() throws RemoteException{
		Player player = new PlayerImpl(1, "Jamie");
		assertEquals("Player [id=1, name=Jamie]", player.display());
	}
	
	/**Tests that a player cannot be created with a null name
	 * @throws RemoteException */
	@Test (expected = NullPointerException.class)
	public void nullNameTest() throws RemoteException{
		new PlayerImpl(1, null);
	}
	
	/**Tests the .equals() method - totally equal
	 * @throws RemoteException */
	@Test
	public void testMatchEquals() throws RemoteException{
		assertEquals(true, new PlayerImpl(1, "Jamie").match(new PlayerImpl(1, "Jamie")));
	}

	/**Tests the .equals() method - ID equal
	 * @throws RemoteException */
	@Test
	public void testMatchIDmatches() throws RemoteException{
		assertEquals(false, new PlayerImpl(1, "Jamie").match(new PlayerImpl(1, "Bob")));
	}
	
	/**Tests the .equals() method - Name equal
	 * @throws RemoteException */
	@Test
	public void testMatchNameMatches() throws RemoteException{
		assertEquals(false, new PlayerImpl(1, "Jamie").match(new PlayerImpl(2, "Jamie")));
	}

	/**Tests the .equals() method - Nothing equal
	 * @throws RemoteException */
	@Test
	public void testMatchNoMatch() throws RemoteException{
		assertEquals(false, new PlayerImpl(1, "Jamie").match(new PlayerImpl(2, "Bob")));
	}

	/**Tests the .equals() method -random object
	 * @throws RemoteException */
	@Test
	public void testEqualsRandomObject() throws RemoteException{
		assertEquals(false, new PlayerImpl(1, "Jamie").equals("Jamie"));
	}
}
