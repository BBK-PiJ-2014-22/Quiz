package network;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import components.Player;
import components.Question;


/**The SetupClientUI class exists exclusively to handle the User Interface for the Setup Client
 * All methods contained handle input from the console and printing of information back to the 
 * console, with a few exceptions (non-void return types) which pass Strings for use in various
 * places throughout.
 * 
 * 
 * @author Jamie
 *
 */
public class SetupClientUI {
	
	SetupClient client;
	Scanner sc;

	public static void main(String[] args){
		SetupClientUI scui = new SetupClientUI();
		scui.launch();
	}

	/**The launch method loads a scanner, and then runs methods to display
	 * welcome message, options selection and exit.
	 */
	public void launch(){
		this.client = new SetupClient();
		client.launch();
		try {
			sc = new Scanner(System.in);
			welcome();
			login();
			options();
			exit();
		} catch (RemoteException e) {
			System.err.println("Remote Connection Error in Setup Client");
			e.printStackTrace();
		}
		
	}
	
		
	/**Prints out a welcome message
	 * 
	 */
	private void welcome(){
		System.out.println("\n\nWelcome to the QuizServer Setup Client.");
		System.out.println("This client will allow you to create quizzes for people "
						 + "to play and manage any games which you have previously created.\n");
		System.out.println("Before you begin, please login or create a new player ID.");
	}
	
	/**Manages this login process for the SetupClient. Once completed, the SetupClient will have a
	 * player assigned to it and be able to proceed. 
	 * 
	 * @throws RemoteException
	 */
	private void login() throws RemoteException{
		Boolean result = false;
		while (!result){
			System.out.println("Select option:");
			System.out.println("	[L] - Login");
			System.out.println("	[C] - Create Player");
			String response = sc.nextLine().substring(0, 1).toLowerCase();

			if (response.equals("l")){
				System.out.println("Enter login ID and Name (e.g. 0 Player 0)");
				String line = sc.nextLine(); //This does not use nextInt because the name can include spaces
				int firstSpace = line.indexOf(' ');
				if (firstSpace > 0){
					int id;
					try{
						id = Integer.parseInt(line.substring(0, firstSpace));
						result = client.login(id, line.substring(firstSpace+1, line.length()));
						if (!result) System.out.println("Invalid login details");
						else System.out.println("Login succesful. Welcome back "+client.player.getName());
					}catch (NumberFormatException ex){
						System.out.println("Please put ID first\n");
					}
				}else{
					System.out.println("Invalid format. Please enter ID, followed by a space, followed by name\n");
				}
			}
			else if (response.equalsIgnoreCase("c")){
				System.out.println("Enter a user name");
				String entry = sc.nextLine();
				if (entry != null) {
					result = client.createPlayer(entry);
					System.out.println("New player created:");
					System.out.println("Player ID:"+client.player.getId()+" Name:"+client.player.getName());
				}
				else System.out.println("Name must not be blank");
			}else System.out.println("Not a valid option.");
		}
	}
	
	/**Manages options selection for the top layer and will run appropriate methods depending on option selected.
	 * Considered the main menu.
	 * 
	 * @throws RemoteException
	 */
	private void options() throws RemoteException{
		boolean running = true;
		while (running) {
			System.out.println("\nPlease select an option from the list:");
			System.out.println("		[1] - Create new Quiz");
			System.out.println("		[2] - Edit Quiz");
			System.out.println("		[3] - Activate a Quiz");
			System.out.println("		[4] - Complete a Quiz");
			System.out.println("		[5] - View all your quizzes");
			System.out.println("		[6] - View all games for one of your quizzes");
			System.out.println("		[7] - Get the winners for a quiz");
			System.out.println("		[8] - Help");
			System.out.println("		[0] - Exit the game");
			
			try{
				int option = Integer.parseInt(sc.nextLine());

				switch  (option){
					case 1: this.createQuiz();
							break;
					case 2: this.editQuiz();
							break;
					case 3: this.activateQuiz();
							break;
					case 4: this.completeQuiz();
							break;
					case 5: this.viewQuizzes();
							break;
					case 6: this.viewGames();
							break;
					case 7: this.printWinners();
							break;
					case 8: this.getHelp();
							break;
					case 0: running = false;
							break;
					default: System.out.println("Invalid option");
							break;
				}
				
			}catch (NumberFormatException ex){
				System.out.println("Not a valid option");
			}
		}
	}
	
	
	/**Handles the UI for a user to create a new quiz. They will be able to edit the quiz afterwards
	 * if they make the correct selection
	 * 
	 * @throws RemoteException
	 */
	private void createQuiz() throws RemoteException{
		System.out.println("Please enter a name for your quiz");
		int result = client.createQuiz(sc.nextLine());
		System.out.println("Your quiz has been created and assigned the ID:"+result);
		System.out.println("Please use this to access the quiz in future");
		System.out.println("\nWould you like to edit this quiz? ");
		System.out.println("Y for yes, any other key to go back to the options menu)");
		
		String proceed = sc.nextLine().toLowerCase();
		if (proceed.equals("y")){
			client.editQuiz(result);
			editQuiz();
		}
	}
	
	/**User Interface for editing a quiz. First a quiz must be loaded into the client's
	 * currentQuiz. Once in place (or if already in place through another method) the
	 * quizMaster will be able to edit that quiz.
	 * 
	 * @throws RemoteException
	 */
	private void editQuiz() throws RemoteException{
		//Will get a quiz assigned correctly if one hasn't already been assigned
		//This is most circumstances, unless this has been launched from createQuiz()
		while (client.currentQuiz == null){
			System.out.println("Please enter quiz ID of an INACTIVE quiz to edit.");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (client.editQuiz(id)) System.out.println("Now editing quiz "+client.currentQuiz.getQuizName());
				else askToViewQuizzes();
					
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
		
		if (client.currentQuiz != null){
			System.out.println("What would you like to edit?");
			editOptions();
			
		}

		client.currentQuiz = null; //Should be a method
	}
	
	/** Manages UI for editing a quiz. Considered the Quiz Edit menu.
	 * 
	 * @throws RemoteException
	 */
	private void editOptions() throws RemoteException{
		boolean running = true;
		while (running) {
			System.out.println("\nEditing Quiz:");
			printQuiz();
			System.out.println("\nPlease select an option from the list:");
			System.out.println("		[1] - Add a Question");
			System.out.println("		[2] - Edit a Question");
			System.out.println("		[3] - Help");
			System.out.println("		[0] - Exit to Menu");
			
			try{
				int option = Integer.parseInt(sc.nextLine());

				switch  (option){
					case 1: this.addQuestion();
							break;
					case 2: this.editQuestion();
							break;
					case 3: this.editQuizHelp();
							break;
					case 0: running = false;
							break;
					default: System.out.println("Invalid option");
							break;
				}
				
			}catch (NumberFormatException ex){
				System.out.println("Not a valid option");
			}
		}
	}
	
	/**Allows the user to add a question to the current quiz
	 * 
	 * @throws RemoteException
	 */
	private void addQuestion() throws RemoteException {
		System.out.println("Enter question");
		client.addQuestion(sc.nextLine());
		System.out.println("Would you like to edit this question? (y to edit)");
		if (sc.nextLine().toLowerCase().equals("y")) editQuestion();
	}
	
	/**Loads a question to the client.currentQuestion to allow editing.
	 * 
	 * @throws RemoteException
	 */
	private void editQuestion() throws RemoteException {
		System.out.println(client.currentQuestion.display());
		//Will run if current question is null
		//If this has been loaded from addQuestion the question will already be assigned
		while (client.currentQuestion == null){
			System.out.println("Please enter question id");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (client.editQuestion(id)) System.out.println("Now editing question "+id);
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
		questionOptions();
		client.currentQuestion = null;
	}
	
	/**Displays help for the edit page
	 * 
	 */
	private void editQuizHelp() {
		// TODO Auto-generated method stub
		
	}
	
	/**Options page for editing questions within a quiz
	 * 
	 * @throws RemoteException
	 */
	private void questionOptions() throws RemoteException {
		boolean running = true;
		while (running) {
			System.out.println("Editing Question:");
			printQuestion();
			System.out.println("\nPlease select an option from the list:");
			System.out.println("		[1] - Add an answer");
			System.out.println("		[2] - Swap two answers");
			System.out.println("		[3] - Set correct answer");
			System.out.println("	    [4] - Change an answer");
			System.out.println("		[0] - Exit to Menu");
			
			try{
				int option = Integer.parseInt(sc.nextLine());

				switch  (option){
					case 1: this.addAnswer();
							break;
					case 2: this.swapAnswer();
							break;
					case 3: this.setCorrectAnswer();
							break;
					case 4: this.changeAnswer();
							break;
					case 0: running = false;
							break;
					default: System.out.println("Invalid option");
							break;
				}
				
			}catch (NumberFormatException ex){
				System.out.println("Not a valid option");
			}
		}
		
	}
	
	/**Allows the user to change an answer's wording
	 * 
	 * @throws RemoteException
	 */
	private void changeAnswer() throws RemoteException {
		System.out.println("\nEnter ID followed by new answer (e.g. 0 New Answer)");
		
		boolean changed = false;
		while (!changed){
			String line = sc.nextLine();
			int firstSpace = line.indexOf(' ');
			int id;
			try{
				id = Integer.parseInt(line.substring(0, firstSpace));
				String answer = line.substring(firstSpace,line.length());
				changed = client.changeAnswer(id, answer);
				if (changed) System.out.println("Answer changed\n");
				else System.out.println("Unable to change answer. Check entry\n");				
			}catch (NumberFormatException ex){
				System.out.println("Please put ID first\n");
			}
		}
	}

	/**Allows the user to change the correct answer
	 * 
	 * @throws RemoteException
	 */
	private void setCorrectAnswer() throws RemoteException {
		boolean set = false;
		while (!set){
			System.out.println("Please enter answer id");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (client.setCorrectAnswer(id)) set = true;
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
			System.out.println("Correct answer set");
		}
		
	}

	/**Allows the user to add a new answer
	 * 
	 * @throws RemoteException
	 */
	private void addAnswer() throws RemoteException {
		boolean added = false;
		while (!added){
			System.out.println("\nEnter new answer");
			String answer = sc.nextLine();
			if (answer != null) System.out.println("Answer must not be blank");
			else client.addAnswer(answer);
		}

		
	}

	/**Allows the user to swap two answers in order
	 * 
	 * @throws RemoteException
	 */
	private void swapAnswer() throws RemoteException {
		boolean swapped = false;
		while (!swapped){
			System.out.println("Enter two question IDs to swap.  -1 to exit");
			String entry = sc.nextLine();
			int firstSpace = entry.indexOf(' ');
			if (entry.equals("-1")) break;
			else if (firstSpace < 0) System.out.println("Enter two IDs separated by a space");
			else{
				try{
					int id1 = Integer.parseInt(entry.substring(0, firstSpace));
					int id2 = Integer.parseInt(entry.substring(firstSpace, entry.length()));
					swapped = client.swapAnswer(id1, id2);
					if (swapped) System.out.println("Questions "+id1+" and "+id2+" swapped");
					else System.out.println("Swap unsuccessful. Check IDs are in range");
				}catch (NumberFormatException ex){
					System.out.println("Enter two IDs separated by a space");
				}
			}
		} 			
	}



	/**Activates an inactive quiz
	 * 
	 * @throws RemoteException
	 */
	private void activateQuiz() throws RemoteException{
		boolean activated = false;
		while (!activated){
			System.out.println("\nPlease enter quiz ID of an INACTIVE quiz to activate.");
			System.out.println("Enter -1 to return to menu");
			try{
				int quizID = Integer.parseInt(sc.nextLine());
				if (quizID < 0) break;
				activated = client.activateQuiz(quizID);
				if (!activated) {
					System.out.println("Invalid entry. Must be an INACTIVE quiz you own");
					askToViewQuizzes();
				}else System.out.println(client.server.getQuiz(quizID).getQuizName()+" activated");
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
	}
	
	/**Completes an active quiz
	 * 
	 * @throws RemoteException 
	 * 
	 */
	public void completeQuiz() throws RemoteException{
		boolean completed = false;
		while (!completed){
			System.out.println("\nPlease enter quiz ID of an ACTIVE quiz to complete.");
			System.out.println("Enter -1 to return to menu");
			try{
				int quizID = Integer.parseInt(sc.nextLine());
				if (quizID < 0) break;
				completed = client.completeQuiz(quizID);
				if (!completed) {
					System.out.println("Invalid entry. Must be an ACTIVE quiz you own");
					askToViewQuizzes();
				}else {
					System.out.println(client.server.getQuiz(quizID).getQuizName()+" completed");
					System.out.println(prettyWinnersList(quizID));
				}
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
	}
	
	/**Asks the player if they want to view all of their quizzes. If they 
	 * enter y it will display it on screen. Anything else will do nothing.
	 * 
	 */
	public void askToViewQuizzes() throws RemoteException{
		System.out.println("Press y to view all of your quizzes");
		if (sc.nextLine().toLowerCase().equals("y")) viewQuizzes();
	}
	
	/**Displays quiz
	 * 
	 */
	public void viewQuizzes() throws RemoteException{
		System.out.println("\nAll quizzes:");
		System.out.println(client.getPrettyQuizList());
	}
	/**Prints out a list of all games for a specific quiz
	 * 
	 * @throws RemoteException 
	 */
	public void viewGames() throws RemoteException{
		boolean printed = false;
		while (!printed){
			System.out.println("Please enter quiz id");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				System.out.println(client.getPrettyGamesList(id));
				printed = true;
		}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}	
		}
	}
	
	/**Prints a list of winners for an owned quiz.
	 * 
	 * @throws RemoteException 
	 * 
	 */
	public void printWinners() throws RemoteException{
		String winners = null;
		while (winners == null){
			System.out.println("\nPlease enter quiz ID of a quiz you own");
			System.out.println("Enter -1 to return to menu");
			try{
				int quizID = Integer.parseInt(sc.nextLine());
				if (quizID < 0) break;
				winners = prettyWinnersList(quizID);
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
			System.out.println(winners);
		}
	}
	
	/**Prints a pretty display of the current quiz
	 * 
	 * @throws RemoteException
	 */
	public void printQuiz() throws RemoteException{
		if (client.currentQuiz == null) System.out.println("Error: No current quiz");
		else{
			String result= "";
			result += "Quiz:"+client.currentQuiz.getQuizID()+":"+client.currentQuiz.getQuizName()+"\n";
			if (client.currentQuiz.getQuestionList() == null ||
				client.currentQuiz.getQuestionList().size() == 0) result+= "No questions";
			else{
				for (Question question: client.currentQuiz.getQuestionList())
					result += question.getQuestion()+"\n";
			}
			System.out.println(result);
		}
	}
	

	/**Prints a pretty display of the current question
	 * 
	 * @throws RemoteException
	 */
	public void printQuestion() throws RemoteException{
		if (client.currentQuestion == null) System.out.println("Error: No current question");
		else{
			List<String> answerlist = client.currentQuestion.getAnswers();
			String result= "";
			result += "Question:"+client.currentQuestion.getQuestion()+"\n";
			if (answerlist == null || answerlist.size() ==  0) result+= "No answers";
			else{
				for (String answer: answerlist)
					result += answer+"\n";
			}
			System.out.println(result);
		}
	}
	
	/**Displays help for the main section
	 * 
	 */
	public void getHelp(){
		System.out.println("Method Not Yet Implemented (getHelp");
		//TODO - Write help
	}
	
	/**Displays exit message
	 * 
	 */
	public void exit(){
		sc.close();
		System.out.println("Thank you for using SetupClient.");
	}
	
	/**Returns a readable representation of the winners list for 
	 * a quiz, or a message if the quiz does not belong to the player
	 * 
	 * @param id
	 * @return
	 * @throws RemoteException
	 */
	private String prettyWinnersList(int id) throws RemoteException{
		List<Player> winners = client.getWinners(id);
		if (winners == null) return "ID does not match to your quiz";
		String result = "\nQuiz:      "+client.server.getQuiz(id).getQuizName()
					   +"\nTop Score: "+client.getHighScore(id)
				       +"\nWinners:   "+winners.size()+"\n";
		for (Player player : winners){
			result += player.display()+"\n";
		}
		return result;
	}
}



