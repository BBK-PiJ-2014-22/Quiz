package network;

/**This is a script that mocks up a session as if it was run and tests at each stage that the situation in the 
 * QuizServer remains as expected. As soon as the server encounters a situation that is unexpected it will terminate.
 * 
 * 
 * @author Jamie
 *
 */
public class QuizServerTestScript {
	
	

	public static void main(String[] args) {
		QuizServerTestScript script = new QuizServerTestScript();
		script.launch();
	}
	
	/**Note the server must be running separately for this to work. 
	 * 
	 */
	public void launch(){
		
		//Opens up a setup client
		SetupClient sc = new SetupClient();
		try{
			sc.launch();
		}catch (java.security.AccessControlException ex){
			System.err.println("SetupClient failed to connect");
			ex.printStackTrace();
		}
		
		
		System.out.println("Tests complete");
	}
	
	public void systemExit(String message){
		System.out.println(message);
		System.exit(0);
	}

}
