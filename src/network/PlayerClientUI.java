package network;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import components.Game;

public class PlayerClientUI {

	PlayerClient client;
	Scanner sc;

	public static void main(String[] args){
		PlayerClientUI pcui = new PlayerClientUI();
		pcui.launch();
	}

	/**The launch method loads a scanner, and then runs methods to display
	 * welcome message, options selection and exit.
	 */
	public void launch(){
		this.client = new PlayerClient();
		client.launch();
		try {
			sc = new Scanner(System.in);
			welcome();
			login();
			options();
			exit();
		} catch (RemoteException e) {
			System.err.println("Remote Connection Error in Player Client");
			e.printStackTrace();
		}
		
	}
	
	/**Prints out a welcome message
	 * 
	 */
	private void welcome(){
		System.out.println("\n\nWelcome to the QuizServer Player Client.");
		System.out.println("This client will allow you to play any active quizzes and to view "
						 + "any games which you have played or are in progress.\n");
		System.out.println("Before you begin, please login or create a new player ID.");
	}
	
	/**Manages this login process for the PlayerClient. Once completed, the PlayerClient will have a
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
	
	/**Displays the main menu for the PlayerClient and handles option selection
	 * 
	 * @throws RemoteException
	 */
	private void options() throws RemoteException {
		boolean running = true;
		while (running) {
			System.out.println("\nPlease select an option from the list:");
			System.out.println("		[1] - Start new game");
			System.out.println("		[2] - Continue game");
			System.out.println("		[3] - View game list");
			System.out.println("		[4] - View available quizzes");
			System.out.println("		[0] - Exit the game");
			
			try{
				int option = Integer.parseInt(sc.nextLine());

				switch  (option){
					case 1: this.startGame();
							break;
					case 2: this.continueGame();
							break;
					case 3: this.viewGameList();
							break;
					case 4: this.viewQuizList();
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
	
	/**Starts a new game for the player, if they give a valid ID
	 * 
	 * @throws RemoteException
	 */
	private void startGame() throws RemoteException {
		System.out.println("Select active quiz:");
		viewQuizList();
		while (client.currentGame == null){
			System.out.println("Please enter quiz ID of an ACTIVE quiz to play.");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (client.startNewGame(id)) System.out.println("Starting game");
				else {
					System.out.println("Not a valid ID for an active quiz you haven't already started");
				}			
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
		if (client.currentGame != null)	playGame();	
	}

	/**Allows the user to continue a game which is in progress, either due to intentionally pausing
	 * or a connection error
	 * 
	 * @throws RemoteException
	 */
	private void continueGame() throws RemoteException {
		System.out.println("Select in progress game:");
		viewGameList();
		List<Game> gamelist = client.server.getGameList(client.player);
		while (client.currentGame == null){
			System.out.println("Please enter ID game to continue");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (id >= 0 && id < gamelist.size()){
					client.currentGame = gamelist.get(id);
					System.out.println("Starting game");
				}else System.out.println("Not a valid ID for an active quiz you haven't already started");			
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
		if (client.currentGame != null)	playGame();	
	}

	/**Displays all of the users games, regardless of status, and score if applicable
	 * 
	 * @throws RemoteException
	 */
	private void viewGameList() throws RemoteException {
		System.out.println(client.getPrettyGameList());
	}

	/**Displays the list of active quizzes which the player does not already have a game for
	 * 
	 * @throws RemoteException
	 */
	private void viewQuizList() throws RemoteException{
		System.out.println("\n"+client.getPrettyQuizList());
	}

	/**Displays the error message
	 * 
	 */
	private void exit() {
		System.out.println("Goodbye. Thanks for playing.)");		
	}
	
	/**Runs the current game until either the player pauses or he completes the game
	 * 
	 * @throws RemoteException
	 */
	public void playGame() throws RemoteException{
		System.out.println("Starting Quiz :"+client.currentGame.getQuiz().getQuizName());
		boolean completed = false;
		while (!completed){
			System.out.println("Answer so far: "+client.currentGame.getAnswers().size() +"//"+client.currentGame.getQuiz().getQuestionList().size()+"\n");
			String question = client.currentGame.getNextQuestion();
			if (question == "none"){
				System.out.println("Quiz terminated or poorly setup");
				break;
			}
			System.out.println("Please select answer ID to answer a question.");
			System.out.println("Enter -1 to return to menu\n");
			System.out.println(question);
			
			boolean answered = false;
			while(!answered){
				try{				
					int id = Integer.parseInt(sc.nextLine());
					if (id == -1){
						completed = true;
						answered = true;
						client.currentGame = null;			
					}else{
						answered = client.currentGame.answerQuestion(id);
						if (!answered) System.out.println("Not a valid answer");
						completed = client.currentGame.isCompleted();
					}
				}catch (NumberFormatException ex){
					System.out.println("Invalid entry. Please enter an ID");
				}
			}
		}
		if (client.currentGame != null){
			System.out.println("Game Completed!");
			System.out.println("Your score was:"+client.currentGame.getScore());
		}
		client.currentGame = null;
	}
	
	/**Pauses the current game. Removes the current active game.
	 * 
	 */
	public void pauseGame(){
		client.currentGame = null;
	}
	


}
