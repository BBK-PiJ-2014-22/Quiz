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
	public String toString()  {
		return "Player [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PlayerImpl other = (PlayerImpl) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


}
