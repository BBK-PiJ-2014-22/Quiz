package network;

import java.rmi.RemoteException;

import components.PlayerImpl;
import components.Question;
import components.Quiz;

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
		if (!(sc.currentQuiz.getQuizID() == 4 || sc.currentQuiz.getQuizName().equals("Quiz 4"))){ 
			systemExit("SC CreateQuiz test failed - quiz wrong."
					   +"\nExpected:  4:Quiz 4"
					   +"\nActual:   "+sc.currentQuiz.getQuizID()+":"+sc.currentQuiz.getQuizName());
		}
		System.out.println("Quizzes 0 to 4 created");
		//Current Status:
		//5 quiz masters, 5 quizzes assigned to QuizMaster 0, Quiz 4 is active quiz

		sc.login(1, "QuizMaster 1");
		sc.createQuiz("Quiz 5");
		sc.createQuiz("Quiz 6");
		sc.createQuiz("Quiz 7");
		System.out.println("Quizmaster 1 logged in, quizes 5/6/7 created");
		
		//Checking the pretty display for QuizMaster 1 only contains quizzes 5 & 6
		if (!sc.getPrettyQuizList().equals("[ID=5, Name=Quiz 5, Status=INACTIVE]\n"+
										   "[ID=6, Name=Quiz 6, Status=INACTIVE]\n"+
										   "[ID=7, Name=Quiz 7, Status=INACTIVE]")){
			systemExit("Failed quiz list display. Got:\n"+sc.getPrettyQuizList());
		}
		
		//Builds the quiz 5 and 6 ready for use
		
	
		buildQuiz(sc, 7); //Tests answers within the build
		if (sc.currentQuiz.getQuestionList().size() != 7) systemExit("SC unable to edit question 7");
		
		
		//Tries to change to question 6
		if (!sc.editQuiz(6)) systemExit("SC unable to edit question 6");
		buildQuiz(sc,6);
		if (sc.currentQuiz.getQuestionList().size() != 6) systemExit("List size wrong for quiz 6. Returned "+
																	 sc.currentQuiz.getQuestionList().size());
		
		//Tries to change to question 5
		if (!sc.editQuiz(5)) systemExit("SC unable to edit question 5");
		buildQuiz(sc,5);
		if (sc.currentQuiz.getQuestionList().size() != 5) systemExit("List size wrong for quiz 5. Returned "+
																   	  sc.currentQuiz.getQuestionList().size());
	
		


			
		
		//Test edge cases:
		// Try and edit other player
		// Try and activate quizzes in weird states
				
	
		
		
	
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
	
	/**Adds a number of questions to to a quiz. They will have the following characteristics;
	 * 
	 * 1) a number of answers equal to numberOfQuestions
	 * 2) Name in ascending order (Question 0 to Question NoQs)
	 * 3) Correct answer will match to the question number
	 * 
	 * @param numberOfQuestions
	 */
	public void buildQuiz( SetupClient sc, int numberOfQuestions) throws RemoteException{
		for (int i = 0 ; i < numberOfQuestions ; i++){
			sc.addQuestion("Question "+i);
			for (int j = 0 ; j < numberOfQuestions ; j++) sc.addAnswer("Answer "+j);
			sc.setCorrectAnswer(i);
			if ((sc.currentQuiz.getQuestion(i).getCorrectAnswer()) != i)
				systemExit("Setup test failed for"+sc.currentQuestion.display()
						  +"\nExpected:0"
						  +"\nAcutal:" +sc.currentQuiz.getQuestion(i).getCorrectAnswer());
		}
	}
	
	
	public void systemExit(String message){
		System.err.println(message);
		System.exit(0);
	}

}
