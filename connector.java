package application;
import java.sql.*; 
public class connector {
	private static final String USERNAME = "DBAdmin"; 
	private static final String PASSWORD = "DBAdminpass"; 
	private static final String CONN = "jdbc:mysql://localhost/cosc236"; 
public static Connection DBconnection() throws SQLException{
	
	return DriverManager.getConnection(CONN,USERNAME,PASSWORD); 
}
public static void main (String []args) throws SQLException {
	Connection conn = DBconnection(); 
	if(conn !=null) {
		System.out.print("connected");
	}
}
}
