package connection_pool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**the constructor of ConnectionPool creates a set of <b> five </b>
 * connection to  <i>coupon_system_database </i> found in mySQL server.
 */
public class ConnectionPool {
	
	
	private static ConnectionPool instance = new ConnectionPool();
	private String url = "jdbc:mysql://localhost:3306/coupon_system_database";
	private String user = "root";
	private String password = "1234";
	private Set<Connection> set = new HashSet<>();
	private static final int Pool_Size = 5;
	

	
	private ConnectionPool(){
		for (int i = 0; i < Pool_Size; i++) {
			try {
				this.set.add(DriverManager.getConnection(url, user, password));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static ConnectionPool getInstance() {
		return instance;
	}
	
	
	public synchronized Connection getConnection() {
		
		Connection con = null;
		Iterator<Connection> itt = set.iterator();
		
		while (set.isEmpty()) {  // as long as there isn't any connection left
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// if here, there is a connection
		con = itt.next();
		set.remove(con);
		return con;
	}
	
	
	public synchronized void returnConnection(Connection con) {
		set.add(con);
		notifyAll();
	}
	
	public synchronized void closeAllConnections() {
		
		Iterator<Connection> itt = set.iterator();
		
		while (set.size()<Pool_Size) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while (itt.hasNext()) {
			try {
				itt.next().close();
				itt.remove();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	
	

}
