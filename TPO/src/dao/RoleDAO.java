package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import backEnd.Roles;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;

public class RoleDAO {
//'DISTRIBUTION_RESPONSABLE',
//'INVOICING_RESPONSABLE',
//'CALL_CENTER_RESPONSABLE',
//'ZONE_RESPONSABLE',
//'QUERY_USER',
//'ADMINISTRATOR'
	
	public static int idByRole(Roles role) throws AccessException, ConnectionException, InvalidRoleException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		String SQL = "SELECT * FROM Roles WHERE Role = '" + role.toString() + "'"; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				return rs.getInt(1);
			}
			else{
				throw new InvalidRoleException("Role not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
		
	}
}
