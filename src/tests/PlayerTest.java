package tests;

import static org.junit.Assert.*;

import org.junit.Test;

import components.Player;

/**Tests for the Player object. Apart from getters Test, ensures that a Player cannot have  a null name
 * 
 * @author Jamie MacIver
 *
 */
public class PlayerTest {

	/**Test get name */
	@Test
	public void getNameTest(){
		Player player = new Player(1, "Jamie");
		assertEquals("Jamie", player.getName());
	}

	/**Test get ID*/
	@Test
	public void getIDtest(){
		Player player = new Player(1, "Jamie");
		assertEquals(1, player.getId());
	}

	/**Tests toString*/
	@Test
	public void toStringTest(){
		Player player = new Player(1, "Jamie");
		assertEquals("Player [id=1, name=Jamie]", player.toString());
	}
	
	/**Tests that a player cannot be created with a null name*/
	@Test (expected = NullPointerException.class)
	public void nullNameTest(){
		new Player(1, null);
	}
	
	/**Tests the .equals() method - totally equal*/
	@Test
	public void testEqualsEquals(){
		assertEquals(true, new Player(1, "Jamie").equals(new Player(1, "Jamie")));
	}

	/**Tests the .equals() method - ID equal*/
	@Test
	public void testEqualsIDmatches(){
		assertEquals(false, new Player(1, "Jamie").equals(new Player(1, "Bob")));
	}
	
	/**Tests the .equals() method - Name equal*/
	@Test
	public void testEqualsNameMatches(){
		assertEquals(false, new Player(1, "Jamie").equals(new Player(2, "Jamie")));
	}

	/**Tests the .equals() method - Nothing equal*/
	@Test
	public void testEqualsNoMatch(){
		assertEquals(false, new Player(1, "Jamie").equals(new Player(2, "Bob")));
	}

	/**Tests the .equals() method -random object*/
	@Test
	public void testEqualsRandomObject(){
		assertEquals(false, new Player(1, "Jamie").equals("Jamie"));
	}

	

}
