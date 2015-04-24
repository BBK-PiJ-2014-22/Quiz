package components;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**The player object is persisted in the server and accessed through the player and setup clients. 
 * It is immutable and only used for storing player information. When accessing the player client, 
 * the player will need to select an existing player or create a new one as it is needed for a game. 
 * When accessing the setup client, the player will only be able to close quizzes that belong to it.
 * 
 * 
 * 
 * @author Jamie MacIver
 *
 */
public class PlayerImpl extends UnicastRemoteObject implements Player{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4530883014969324647L;
	
	private int id;
	private String name;
	
	public PlayerImpl(int id, String name) throws RemoteException{
		if (name == null)
			throw new NullPointerException();
		else{
			this.id = id;
			this.name = name;
		}		
	}
	
	@Override
	public int getId() throws RemoteException{
		return id;
	}

	@Override
	public String getName() throws RemoteException {
		return name;
	}

	@Override
	public String display()  throws RemoteException{
		return "Player [id=" + id + ", name=" + name + "]";
	}

	@Override
	public boolean match(Object obj) throws RemoteException {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		try{
			Player other = (Player) obj;
			return (this.getId() == other.getId() &&  this.getName() == other.getName());
		}catch (ClassCastException ex){
			System.out.println("Class wrong");
			return false;
		}
	}


}
