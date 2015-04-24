package components;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Player extends Remote {
	
	/**Returns player name
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String getName() throws RemoteException;
	
	/**Returns player id
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public int getId() throws RemoteException;
	
	/**Returns pretty string representation
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public String display() throws RemoteException;
	
	/**Returns true if fields match, false otherwise
	 * 
	 * @return
	 * @throws RemoteException
	 */
	public boolean match(Object obj) throws RemoteException;

}
