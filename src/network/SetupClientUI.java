package network;

import java.rmi.RemoteException;
import java.util.InputMismatchException;
import java.util.Scanner;

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
		while (client.currentQuiz == null){
			System.out.println("Please enter quiz ID of an INACTIVE quiz to edit.");
			System.out.println("Enter -1 to return to menu");
			try{
				int id = Integer.parseInt(sc.nextLine());
				if (id == -1) break;
				else if (client.editQuiz(id)) System.out.println("Now editing quiz"+client.currentQuiz.getQuizName());
				else {
					System.out.println("Invalid entry. No active quiz with that ID belongs to you.");
					System.out.println("Press y to view all of your quizzes");
					if (sc.nextLine().toLowerCase().equals("y")) viewQuizzes();
				}
					
			}catch (NumberFormatException ex){
				System.out.println("Invalid entry. Please enter an ID");
			}
		}
		
		if (client.currentQuiz != null){
			debug("Not yet implemented specific quiz edit");
		}

		client.currentQuiz = null; //Should be a method
	}
		
	private void activateQuiz(){
		System.out.println("Method Not Yet Implemented (activateQuiz)");
	}
	
	public void completeQuiz(){
		System.out.println("Method Not Yet Implemented (completeQuiz)");
	}
	
	public void viewQuizzes() throws RemoteException{
		System.out.println("All quizzes:");
		System.out.println(client.getPrettyQuizList());
	}
	
	public void viewGames(){
		System.out.println("Method Not Yet Implemented (viewGames)");
	}
	
	public void getWinners(){
		System.out.println("Method Not Yet Implemented (getWinners");
	}
	
	public void getHelp(){
		System.out.println("Method Not Yet Implemented (getHelp");
	}
	
	public void exit(){
		sc.close();
		System.out.println("Thank you for using SetupClient.");
	}
	
	private void debug(String string){
		System.out.println(string);
	}
}



