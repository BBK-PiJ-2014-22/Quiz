package network;

import java.rmi.RemoteException;
import java.util.Scanner;

public class SetupClientUI {
	
	SetupClient client;

	public static void main(String[] args) {
		SetupClientUI scui = new SetupClientUI();
		scui.launch();
	}

	public void launch(){
		this.client = new SetupClient();
	}
	
		
	private void welcome(){
		System.out.println("Welcome to the QuizServer Setup Client.");
		System.out.println("This client will allow you to create quizzes for people "
						 + "to play and manage any games which you have previously created.\n");
		System.out.println("Before you begin, please login or create a new player ID.");
	}
	
	private void loginUI() throws RemoteException{
		Scanner sc = new Scanner(System.in);
		Boolean result = false;
		while (!result){
			System.out.println("Select option:");
			System.out.println("	[L] - Login");
			System.out.println("	[C] - Create Player");
			String response = sc.nextLine().substring(0, 1).toLowerCase();

			if (response.equals("l")){
				System.out.println("Enter login ID and Name (e.g. 0 Player 0)");
					result = login(sc.nextInt(), sc.nextLine());
					if (!result) System.out.println("Invalid login details");
				}
			else if (response.equalsIgnoreCase("c")){
				System.out.println("Enter a user name");
				String entry = sc.nextLine();
				if (entry != null) result = createPlayer(entry);
				else System.out.println("Name must not be blank");
			}
		}
	}
}

