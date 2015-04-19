package network;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class QuizServerLauncher {
	
	public static void main(String args[]){
		QuizServerLauncher launcher = new QuizServerLauncher();
		launcher.launch();
	}
	
	public void launch(){
		if (System.getSecurityManager() == null){
			System.setSecurityManager(new SecurityManager());
		}
		try{
			LocateRegistry.createRegistry(0);
			QuizServer server = new QuizServer();
			String registryHost = "//localhost/";
			String serviceName = "QuizServer";
			Naming.rebind(registryHost + serviceName, server);
		}catch (MalformedURLException ex) {
			ex.printStackTrace();
		}catch (RemoteException ex){
			ex.printStackTrace();
		}
	}
}
