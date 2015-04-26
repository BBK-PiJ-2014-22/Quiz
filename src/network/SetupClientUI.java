package network;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import components.Player;

public class SetupClientUI {
	
	SetupClient client;
	Scanner sc;

	public static void main(String[] args){
		SetupClientUI scui = new SetupClientUI();
		scui.launch();
	}

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
	
	/**Manages options selection and will run appropriate 
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
					case 7: this.getWinners();
							break;
					case 8: this.getHelp();
							break;
					case 0: running = false;
							break;
				}
				
			}catch (NumberFormatException ex){
				System.out.println("Not a valid option");
			}
		}
	}
	
	
	/**Handles the UI for a user to create a new quiz. They will be able to edit the quiz afterwards.
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
		debug(proceed);
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
			
		}

		client.currentQuiz = null; //Should be a method
	}
	
	private void editOptions() throws RemoteException{
		boolean running = true;
		while (running) {
			System.out.println("\nPlease select an option from the list:");
			System.out.println("		[1] - Add a Question");
			System.out.println("		[2] - Edit a Question");
			System.out.println("		[3] - Swap question order");
			System.out.println("		[4] - Change quiz name");
			System.out.println("		[5] - View all Questions");
			System.out.println("		[6] - Help");
			System.out.println("		[0] - Exit to Menu");
			
			try{
				int option = Integer.parseInt(sc.nextLine());

				switch  (option){
					case 1: this.addQuestion();
							break;
					case 2: this.editQuestion();
							break;
					case 3: this.swapQuestion();
							break;
					case 4: this.changeQuizName();
							break;
					case 5: this.viewQuestions();
							break;
					case 6: this.editQuizHelp();
							break;
					case 7: this.getWinners();
							break;
					case 8: this.getHelp();
							break;
					case 0: running = false;
							break;
				}
				
			}catch (NumberFormatException ex){
				System.out.println("Not a valid option");
			}
		}
	}
	
		
	/**
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
	
	/**
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
	
	/**
	 * 
	 */
	public void viewQuizzes() throws RemoteException{
		System.out.println("\nAll quizzes:");
		System.out.println(client.getPrettyQuizList());
	}
	/**
	 * 
	 */
	public void viewGames(){
		System.out.println("Method Not Yet Implemented (viewGames)");
		//TODO - Implement
	}
	/**
	 * @throws RemoteException 
	 * 
	 */
	public void getWinners() throws RemoteException{
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
	
	/**
	 * 
	 */
	public void getHelp(){
		System.out.println("Method Not Yet Implemented (getHelp");
	}
	
	/**
	 * 
	 */
	public void exit(){
		sc.close();
		System.out.println("Thank you for using SetupClient.");
	}
	
	/**
	 * 
	 */
	private void debug(String string){
		System.out.println(string);
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



