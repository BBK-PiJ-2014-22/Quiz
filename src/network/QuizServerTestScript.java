package network;

import java.rmi.RemoteException;

import components.PlayerImpl;

/**This is a script that mocks up a session as if it was run and tests at each stage that the situation in the 
 * QuizServer remains as expected. As soon as the server encounters a situation that is unexpected it will terminate.
 * 
 * 
 * @author Jamie
 *
 */
public class QuizServerTestScript {
	
	SetupClient sc;
	
	

	public static void main(String[] args) {
		QuizServerTestScript script = new QuizServerTestScript();
		try {
			script.launch();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/**Note the server must be running separately for this to work. 
	 * 
	 */
	public void launch() throws RemoteException{
		
		//Opens up a setup client
		sc = new SetupClient();
		try{
			sc.launch();
		}catch (java.security.AccessControlException ex){
			System.err.println("SetupClient failed to connect");
			ex.printStackTrace();
		}
		
		//creates 5 new players
		for (int i = 0; i < 5; i++)
			createPlayer(i, "Player "+i);
		
		System.out.println("Tests complete");	
	}
	
	public void createPlayer(int expectedID, String name) throws RemoteException{
		if (!sc.createPlayer(name))
			systemExit("SC Create Player failed - status false ");
		if (sc.player.getId() != expectedID || !sc.player.getName().equals(name))
			systemExit("SC Create Player failed - player not assigned correctly. "
					+ "\nExpected: ID="+expectedID+", Name="+name
					+ "\nActual:   ID="+sc.player.getId()+", Name="+sc.player.getName());
		System.out.println("Created Player: ID="+expectedID+", Name="+name);

	
	}
	
	public void systemExit(String message){
		System.err.println(message);
		System.exit(0);
	}

}
