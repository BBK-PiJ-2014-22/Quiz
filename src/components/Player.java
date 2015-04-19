package components;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
	
	public String getName() throws RemoteException;
	
	public int getId() throws RemoteException;

}
