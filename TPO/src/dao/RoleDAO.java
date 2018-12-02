package dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Role;
import backEnd.Roles;
import backEnd.User;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class RoleDAO {
//'DISTRIBUTION_RESPONSABLE',
//'INVOICING_RESPONSABLE',
//'CALL_CENTER_RESPONSABLE',
//'ZONE_RESPONSABLE',
//'QUERY_USER',
//'ADMINISTRATOR'
	
	public int idByRole(Roles role) throws AccessException, ConnectionException, InvalidRoleException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT * FROM Roles WHERE Role = '" + role.toString() + "'"; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
		
	}


}
