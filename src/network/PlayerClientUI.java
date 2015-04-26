package network;

import java.rmi.RemoteException;
import java.util.Scanner;

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
	
	private void exit() {
		// TODO Auto-generated method stub
		
	}

	private void options() {
		// TODO Auto-generated method stub
		
	}
}
