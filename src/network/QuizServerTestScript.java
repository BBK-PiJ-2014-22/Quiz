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
			createPlayer(i, "QuizMaster "+i);
			
		//Tests the login process
		if (sc.login(1, "QuizMaster 0"))  systemExit("SC Incorrect LoginTest failed - Returned status of true");
		else if (!sc.login(0, "QuizMaster 0")) systemExit("SC Correct LoginTest failed - Returned status of false");
		else if (sc.player.getId() != 0|| !sc.player.getName().equals("QuizMaster 0"))
			systemExit("SC Create Player failed - player not assigned correctly. "
					+ "\nExpected: ID=0, Name=QuizMaster 0"
					+ "\nActual:   ID="+sc.player.getId()+", Name="+sc.player.getName());
		else System.out.println(sc.player.getName()+" logged in");
		
		//Creates 5 new quizzes
		for (int i = 0 ; i  < 5 ; i++)
			sc.createQuiz("Quiz "+i);
		if (sc.currentQuiz == null) systemExit("SC CreateQuiz test failed - currentQuiz is null");
		if (sc.currentQuiz.getQuizID() != 4 || sc.currentQuiz.getQuizName() != "Quiz 4") 
			systemExit("SC CreateQuiz test failed - quiz wrong."
					   +"\nExpected:  4:Quiz4"
					   +"\nActual:   "+sc.currentQuiz.getQuizID()+":"+sc.currentQuiz.getQuizName());
		System.out.println("Quizzes 0 to 4 created");
		
		//Current Status:
		//5 quiz masters, 5 quizzes assigned to QuizMaster 0, Quiz 4 is active quiz
	
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
