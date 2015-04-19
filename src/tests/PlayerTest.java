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
	public void toStringTest(){
		Player player = new PlayerImpl(1, "Jamie");
		assertEquals("Player [id=1, name=Jamie]", player.toString());
	}
	
	/**Tests that a player cannot be created with a null name*/
	@Test (expected = NullPointerException.class)
	public void nullNameTest(){
		new PlayerImpl(1, null);
	}
	
	/**Tests the .equals() method - totally equal*/
	@Test
	public void testEqualsEquals(){
		assertEquals(true, new PlayerImpl(1, "Jamie").equals(new PlayerImpl(1, "Jamie")));
	}

	/**Tests the .equals() method - ID equal*/
	@Test
	public void testEqualsIDmatches(){
		assertEquals(false, new PlayerImpl(1, "Jamie").equals(new PlayerImpl(1, "Bob")));
	}
	
	/**Tests the .equals() method - Name equal*/
	@Test
	public void testEqualsNameMatches(){
		assertEquals(false, new PlayerImpl(1, "Jamie").equals(new PlayerImpl(2, "Jamie")));
	}

	/**Tests the .equals() method - Nothing equal*/
	@Test
	public void testEqualsNoMatch(){
		assertEquals(false, new PlayerImpl(1, "Jamie").equals(new PlayerImpl(2, "Bob")));
	}

	/**Tests the .equals() method -random object*/
	@Test
	public void testEqualsRandomObject(){
		assertEquals(false, new PlayerImpl(1, "Jamie").equals("Jamie"));
	}

	

}
