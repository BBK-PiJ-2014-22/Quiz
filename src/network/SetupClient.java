package network;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import components.Player;

public class SetupClient {
	
	Player login;
	
	
	public static void main(String[] args) {
		SetupClient client = new SetupClient();
		client.launch();
	}
	
	public void launch(){
		if (System.getSecurityManager() == null) {
			System.setSecurityManager(new SecurityManager());
		}
		try {
			String name = "QuizServer";
			Registry registry = LocateRegistry.getRegistry(1099);
			SetupInterface server = (SetupInterface) registry.lookup(name);
			login = (Player)server.createPlayer("Bob Smith");
			System.out.println(login);
			
		}catch (Exception e){
			System.err.println("SetupClient Excpetion");
			e.printStackTrace();
		}
	}
}
