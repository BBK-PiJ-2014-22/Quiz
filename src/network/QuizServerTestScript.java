package network;

import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;

import components.QuizStatus;

/**This is a script that mocks up a session as if it was run and tests at each stage that the situation in the 
 * QuizServer remains as expected. As soon as the server encounters a situation that is unexpected it will terminate.
 * 
 * 
 * @author Jamie
 *
 */
public class QuizServerTestScript {
	
	SetupClient sc;
	PlayerClient pc;

	
	

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
		
		for (int i = 2; i < 5 ; i++){
			sc.editQuiz(i);
			buildQuiz(sc, i);
			sc.activateQuiz(i);
		}
		
		System.out.println("Quizzes 0 to 4 created. 2,3,4 built and activated");
		
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
	
		System.out.println("Quizzes 5,6,7 set to 5 questions each");
		
		//Tests activation and completion
		if (sc.activateQuiz(0)) systemExit("Player 1 returns true for activation of quiz 4");
		if (sc.server.getQuiz(0).getStatus() != QuizStatus.INACTIVE) systemExit("Player 1 has activated quiz 0");
		if (!sc.activateQuiz(5)) systemExit("Player 1 returns false for activation of quiz 5");
		if (sc.server.getQuiz(5).getStatus() != QuizStatus.ACTIVE) systemExit("Player 1 has not activated quiz 5");
		if (!sc.completeQuiz(5)) systemExit("Player 1 returns false for completion of quiz 5");
		if (sc.server.getQuiz(5).getStatus() != QuizStatus.COMPLETED) systemExit("Player 1 has not completed quiz 5");		
		if (!sc.activateQuiz(6)) systemExit("Player 1 returns false for activation of quiz 6");
		if (sc.server.getQuiz(6).getStatus() != QuizStatus.ACTIVE) systemExit("Player 1 has not activated quiz 6");
		if (sc.completeQuiz(7)) systemExit("Player 1 has returns true for completing INACTIVE Quiz 6");
		if (sc.server.getQuiz(7).getStatus() != QuizStatus.INACTIVE) systemExit("Player 2 has completed quiz 6");
		
		System.out.println("Quiz 5 completed, Quiz 6 active, Quiz 7 inactive");
		
		//Tests editing questions
		if (!sc.editQuiz(7)) systemExit("Player 1 unable to edit quiz 7");
		if (sc.editQuiz(6)) systemExit("Player 1 able to edit quiz 6 (Active)");
		if (sc.editQuiz(5)) systemExit("Player 1 able to edit quiz 5 (Completed)");
		if (sc.editQuiz(4)) systemExit("Player 1 able to edit quiz 4 (not owned)");
		if (sc.currentQuiz.getQuizID() != 7) systemExit("Current quiz not set to Quiz 7");
		if (sc.currentQuestion != null) systemExit("Quiz changed but question not null");
		
		if (sc.editQuestion(8)) systemExit("Edit question out of bounds returns true");
		if (!sc.editQuestion(0)) systemExit("Edit question in bounds returns false");
		if (!sc.currentQuestion.getQuestion().equals("Question 0")) systemExit("Edit question not assigned to Question 0"
																		+"Question set to: "+sc.currentQuestion.getQuestion());
		
		sc.setQuestion("Question 0b");
		
		if (!sc.currentQuestion.getQuestion().equals("Question 0b")) systemExit("Question not changed to Question 0b"
																			+"Question set to: "+sc.currentQuestion.getQuestion());
		
		if (sc.changeAnswer(8, "answer")) systemExit("Change answer returns true for out of bounds");
		if (!sc.changeAnswer(0, "Answer 0b")) systemExit("Change answer returns false for in bounds");
		if (!sc.currentQuestion.getAnswers().get(0).equals("Answer 0b")) systemExit("Answer not changed to Answer 0b"
																					+"Current: "+sc.currentQuestion.getAnswers().get(0));
		
		if (sc.swapAnswer(0, 10)) systemExit("Swap answer returns true for out of bounds");
		if (!sc.swapAnswer(0, 1)) systemExit("Swap answer returns false for in bounds");
		if (!sc.currentQuestion.getAnswers().get(0).equals("Answer 1")) 
			systemExit("Answer swapped at position 0: "+sc.currentQuestion.getAnswers().get(0));
		if (!sc.currentQuestion.getAnswers().get(1).equals("Answer 0b")) 
			systemExit("Answer swapped at position 1: "+sc.currentQuestion.getAnswers().get(0));
		
		//Remove answer tests
		
		if (!sc.editQuestion(3)) systemExit("Unable to change to question 3");
		if (sc.removeAnswer(7)) systemExit("Returned true when attempting to remove out of bounds answer (7)");
		if (sc.removeAnswer(-1)) systemExit("Returned true when attempting to remove out of bounds answer (-1)");
		if (sc.currentQuestion.getAnswers().size() != 7) systemExit("Size of answer list changed when removing OOB answer");
		if (!sc.removeAnswer(6)) systemExit("Returned false when removing in bounds anwer (6)");
		if (sc.currentQuestion.getAnswers().size() != 6) systemExit("Size of answers incorrect after removal"
																  +"\nExpected: 6 "
																  +"\nActual:   "+sc.currentQuestion.getAnswers().size());
	    if (sc.currentQuestion.getCorrectAnswer() != 3) systemExit("Correct answer changed when removing non-correct answer");
		if (!sc.removeAnswer(3)) systemExit("Returned false when removing in bounds anwer (3)");
		if (sc.currentQuestion.getAnswers().size() != 5) systemExit("Size of answers incorrect after removal"
																  +"\nExpected: 5 "
																  +"\nActual:   "+sc.currentQuestion.getAnswers().size());
	    if (sc.currentQuestion.getCorrectAnswer() != -1) systemExit("Correct answer not reset when correct answer removed");
	    
	    //Remove question tests
	    
	    
	    if (sc.removeQuestion(7)) systemExit("Returned true when attempting to remove out of bounds question (7)");
	    if (sc.removeQuestion(-1)) systemExit("Returned true when attempting to remove out of bounds question (-1)");
	    if (sc.currentQuiz.getQuestionList().size() != 7)  systemExit("Size of questions incorrect when attempting to remove out of bounds questions."
	    												+ "\nExpected: 7" + "\nActual:  "+sc.currentQuiz.getQuestionList().size());
	    if (!sc.removeQuestion(3)) systemExit("Returned false when removeing in bounds question (3)");
	    if (sc.currentQuiz.getQuestionList().size() != 6)  systemExit("Size of questions incorrect after removal"
	    								+ "\nExpected: 6" + "\nActual:  "+sc.currentQuiz.getQuestionList().size());
	    

		System.out.println("Quiz 7 edited in various ways. Do not use for further testing.");
	    	    
	    pc = new PlayerClient();
	    pc.launch();
	    
	    //Login tests
	    if (!pc.login(0, "QuizMaster 0")) systemExit("PC unable to login to QuizMaster 0");
	    if (!pc.player.getName().equals("QuizMaster 0")) systemExit("PC not logged in after succesful login (QM0)");
	    if (pc.login(1, "QuizMaster 0")) systemExit("PC login returns true with bad combination (id=1, QuizMaster 0");
		if (pc.login(0, null)) systemExit("PC login returns true with bad combination (null name)");
	    if (pc.createPlayer(null)) systemExit("PC createPlayer returning true when passed null");
	    if (!pc.createPlayer("Player 5")) systemExit("PC unable to create new Player 5");
	    if (!pc.player.getName().equals("Player 5")) systemExit("PC not logged in after succesful create (Player 5)");
	    
	    System.out.println("Player 5 logged in to PlayerClient");

	    //Try to start games
	    if (pc.startNewGame(5)) systemExit("PC returns true when starting completed game (5)");
	    if (pc.startNewGame(7)) systemExit("PC returns true when starting inactive game (7)");
	    if (pc.currentGame != null) systemExit("PC active game has been set for invalid games");
	    if (!pc.startNewGame(6)) systemExit("PC returns false when starting valid game (6)");
	    if (!pc.currentGame.getQuiz().getQuizName().equals("Quiz 6")) systemExit("PC active game not assigned to Quiz 6");
	    if (!pc.currentGame.getPlayer().equals(pc.player)) systemExit("PC active game not assigned to wrong player");
	    
	    System.out.println("Player 5 starts a game for Quiz 6");
	    	    
	    //Play to completion quiz 6, get 100% score	    
	    int questionListSize = pc.currentGame.getQuiz().getQuestionList().size();
	    
	    for (int i = 0 ; i <  questionListSize; i++){
	    	String questionName = pc.currentGame.getNextQuestion().substring(0, 10);
	    	if (!questionName.equals("Question "+i)) systemExit("Question "+i+" displays:"+questionName);
	    	if (!pc.currentGame.answerQuestion(i)) systemExit("Answer question not returning true for question "+i);
	    	if (i < pc.currentGame.getAnswers().size()-1){
	    		if (pc.currentGame.isCompleted()) systemExit("Game is showing completed early at question"+i);
	    	}
	    }
	    if (!pc.currentGame.isCompleted()) systemExit("Game is not completed at finish (6)");
	    if (pc.currentGame.getScore() != 6) systemExit("Score wrong upon completion. Showing:"+pc.currentGame.getScore());
	    
	    //Plays other games 
	    playGame(pc, 2, Arrays.asList(0,0));
	    if (!pc.currentGame.isCompleted()) systemExit("Game is not completed at finish (2)");
	    if (pc.currentGame.getScore() != 1) systemExit("Score wrong upon completion (2). Showing:"+pc.currentGame.getScore());

	    playGame(pc, 3, Arrays.asList(0,0));
	    if (pc.currentGame.isCompleted()) systemExit("Game is completed early (3)");
	    if (pc.currentGame.getScore() != 0) systemExit("Score showing before completion (3). Showing:"+pc.currentGame.getScore());

	    pc.currentGame.answerQuestion(0);
	    if (!pc.currentGame.isCompleted()) systemExit("Game is not completed at finish (3)");
	    if (pc.currentGame.getScore() != 1) systemExit("Score wrong upon completion (3). Showing:"+pc.currentGame.getScore());

	    playGame(pc, 4, Arrays.asList(3,2,1,0));
	    if (!pc.currentGame.isCompleted()) systemExit("Game is not completed at finish (4)");
	    if (pc.currentGame.getScore() != 0) systemExit("Score wrong upon completion (4). Showing:"+pc.currentGame.getScore());

	    System.out.println("Player 5 completed 4 games");
	    //Player 5's scores:
	   
	    //Game 2: 1
	    //Game 3: 1
	    //Game 4: 0
	    //Game 6: 6
	   
	    pc.createPlayer("Player 6");
	    playGame(pc, 2, Arrays.asList(1,1));
	    playGame(pc, 3, Arrays.asList(0,1,2));
	    playGame(pc, 4, Arrays.asList(3,2,1,0));
	    playGame(pc, 6, Arrays.asList(0,0,0));
	    
	    System.out.println("Player 6 completed 3 games");
	    //Player 6's scores:
	    //Game 2: 1
	    //Game 3: 3
	    //Game 4: 0
	    //Game 6: incomplete
	    
	    pc.createPlayer("Player 7");
	    playGame(pc, 2, Arrays.asList(0));
	    playGame(pc, 3, Arrays.asList(0,1,1));
	    playGame(pc, 4, Arrays.asList(4,3,2,1)); //Won't complete as 4 is not a valid answer
	    playGame(pc, 6, Arrays.asList(0,1,2,0,0,0));
	    
	    System.out.println("Player 7 completed 2 games");
	    //Player 7's scores
	    //Game 2: incomplete
	    //Game 3: 2
	    //Game 4: incomplete
	    //Game 6: 3
	    
	    //QuizMaster 0 logs in and competes the games
	    sc.login(0,  "QuizMaster 0");

	    
	    //Tests quiz 2. Should have two winners, 5 and 6
	    if (!sc.completeQuiz(2)) systemExit("Quiz 2 complete returned false");
	    if (sc.getWinners(2).size() != 2) systemExit("Quiz 2 winners list wrong size. "
	    										   + "\nExpected: 2\n Actual:"+sc.getWinners(2).size());
	    if (!sc.getWinners(2).get(0).getName().equals("Player 5") &&
	        !sc.getWinners(2).get(0).getName().equals("Player 6")) 
	    	systemExit("Winners list for Quiz 2 wrong. 0th element"+sc.getWinners(2).get(0).getName());
	    if (!sc.getWinners(2).get(1).getName().equals("Player 5") &&
		    !sc.getWinners(2).get(1).getName().equals("Player 6")) 
		    	systemExit("Winners list for quiz 2 wrong. 1st element"+sc.getWinners(2).get(1).getName());
	    if (sc.getHighScore(2) != 1) systemExit("High score wrong (2). Expected: 1 Actual:"+sc.getHighScore(2));
		
	    //Tests quiz 3. Should have one winner, player 6
	    if (!sc.completeQuiz(3)) systemExit("Quiz 3 complete returned false");
	    if (sc.getWinners(3).size() != 1) systemExit("Quiz 3 winners list wrong size. "
	    										   + "\nExpected: 1\n Actual:"+sc.getWinners(3).size());
	    if (!sc.getWinners(3).get(0).getName().equals("Player 6"))
	    	systemExit("Winners list for Quiz 3 wrong. 0th element"+sc.getWinners(2).get(0).getName());
	    if (sc.getHighScore(3) != 3) systemExit("High score wrong (3). Expected: 3 Actual:"+sc.getHighScore(2));

	    //Tests quiz 4. Should have 2 winners (5 and 6). Crucial that 7 is not included (0 score but incomplete)
	    if (!sc.completeQuiz(4)) systemExit("Quiz 4 complete returned false");
	    if (sc.getWinners(4).size() != 2) systemExit("Quiz 4 winners list wrong size. "
	    											+ "\nExpected: 2\n Actual:"+sc.getWinners(4).size());
	    if (!sc.getWinners(2).get(0).getName().equals("Player 5") &&
	        !sc.getWinners(2).get(0).getName().equals("Player 6")) 
	    		systemExit("Winners list for Quiz 2 wrong. 0th element"+sc.getWinners(2).get(0).getName());
	    if (!sc.getWinners(2).get(1).getName().equals("Player 5") &&
	    	!sc.getWinners(2).get(1).getName().equals("Player 6")) 
	    		systemExit("Winners list for quiz 2 wrong. 1st element"+sc.getWinners(2).get(1).getName());
	    if (sc.getHighScore(4) != 0) systemExit("High score wrong (4). Expected: 0 Actual:"+sc.getHighScore(2));

	    
	    //Test quiz 6. Should have 1 winner (5)
	    sc.login(1, "QuizMaster 1");
	    if (!sc.completeQuiz(6)) systemExit("Quiz 6 complete returned false");
	    if (sc.getWinners(6).size() != 1) systemExit("Quiz 6 winners list wrong size. "
	    											+ "\nExpected: 2\n Actual:"+sc.getWinners(6).size());
	    if (!sc.getWinners(6).get(0).getName().equals("Player 5"))
	    	systemExit("Winners list for Quiz 6 wrong. 0th element"+sc.getWinners(2).get(0).getName());
	    if (sc.getHighScore(6) != 6) systemExit("High score wrong (6). Expected: 6 Actual:"+sc.getHighScore(2));

	    
	    //Tests for quiz 1 with no players. Should be an empty winners list.
	    sc.activateQuiz(1);
	    sc.completeQuiz(1);
	    if (sc.getWinners(1).size() != 0) systemExit("Quiz 0 winners list populated:"+sc.getWinners(1));

	    System.out.println("Games list for Quiz 7:"+sc.getPrettyGamesList(7));
	    System.out.println("Games list for Quiz 6:"+sc.getPrettyGamesList(6));
	    System.out.println("Games list for Quiz 4:"+sc.getPrettyGamesList(4));
	    
	    
	    		

	
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
	
	/**Plays a game. No checks on if successful and tests should be written on end state.
	 * 
	 * @param pc
	 * @param quizID
	 * @param answers
	 * @throws RemoteException
	 */
	public void playGame(PlayerClient pc, int quizID, List<Integer> answers) throws RemoteException{
		pc.startNewGame(quizID);
		for (int answer: answers){
			pc.currentGame.answerQuestion(answer);
		}
	}

}
