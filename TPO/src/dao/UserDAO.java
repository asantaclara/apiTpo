package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import backEnd.Roles;
import backEnd.User;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class UserDAO {

	public static void save(User user) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		if(user.getId() != 0) {
			throw new InvalidUserException("User already in data base");
		}
		
		user.setId((SqlUtils.lastId("Users", "UserId") + 1)); 
		
		try {
			prepStm = con.prepareStatement("insert into Users values(?,?,?,?,?)");
			prepStm.setInt(1, user.getId());
			prepStm.setString(2, user.getName());
			prepStm.setInt(3, RoleDAO.idByRole(user.getPrincipalRole()));
			prepStm.setInt(4, RoleDAO.idByRole(user.getSecondaryRole()));
			prepStm.setByte(5, (byte) ((user.isActive() == true) ? 1 : 0));
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccessException("Access error");
		}		
		
		try {
			prepStm.execute();
		} catch (SQLException e) {
			throw new AccessException("Save error");
		}
	}
	
	public static User getUser(int userId) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}

		String SQL = "SELECT Users.UserId, Users.Name, Users.Active as UserActive, Role1.Role as Role1, Role2.Role as Role2 "
				+ "FROM Users JOIN Roles AS Role1 ON Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON Users.RolId2 = Role2.RoleId WHERE Users.UserId = " + userId; 
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		try {
			if(rs.next()){
				if(rs.getByte(3) == 1) { 
					User newUser = new User(rs.getString(2), Roles.valueOf(rs.getString(4)));
					newUser.addRole(Roles.valueOf(rs.getString(5)));
					newUser.setId(rs.getInt(1));
					return newUser;
				} else {
					throw new InvalidUserException("The user is not active");
				}
			}
			else{
				throw new InvalidUserException("User not found");
			}
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public static List<User> getAllUsers() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		String SQL = "SELECT Users.UserId, Users.Name, Users.Active, Role1.Role as Role1, Role2.Role as Role2 FROM "
				+ "Users JOIN Roles AS Role1 ON Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON Users.RolId2 = Role2.RoleId WHERE Users.Active = 1";

		return getAllUsersPrivate(SQL);
	}
	
	public static List<User> getAllUserByRole(Roles role) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		
		String SQL = "SELECT Users.UserId, Users.Name, Users.Active, Role1.Role as Role1, Role2.Role as Role2 FROM Users JOIN Roles AS Role1 ON Users.RolId1 = Role1.RoleId "
				+ "JOIN Roles AS Role2 ON Users.RolId2 = Role2.RoleId WHERE Users.Active = 1 AND Role2.Role = '" + role.name() + "'";
		
		return getAllUsersPrivate(SQL);

	}
	
	private static List<User> getAllUsersPrivate(String SQL) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		Connection con = SqlUtils.getConnection();  
		Statement stmt = null;  
		ResultSet rs = null;
		
		try {
			stmt = con.createStatement();
		} catch (SQLException e1) {
			throw new AccessException("Access error");
		}
		
		try {
			rs = stmt.executeQuery(SQL);
		} catch (SQLException e1) {
			e1.printStackTrace();
			throw new AccessException("Query error");
		}
		
		try {
			List<User> returnList = new LinkedList<>();
			User newUser = null;
			
			while(rs.next()){				
				newUser = new User(rs.getString(2), Roles.valueOf(rs.getString(4)));
				newUser.addRole(Roles.valueOf(rs.getString(5)));
				newUser.setId(rs.getInt(1));				
				returnList.add(newUser);
			}
			return returnList;
			
		} catch (SQLException e) {
			throw new ConnectionException("Data not reachable");
		}
	}

	public static void modify(User user) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
		
		if(user.getId() == 0) {
			throw new InvalidUserException("User not in data base");
		}
	
		try {
			prepStm = con.prepareStatement("UPDATE Users SET Name = ?, RolId1 = ?, RolId2 = ?, Active = ? WHERE UserId = " + user.getId());
			prepStm.setString(1, user.getName());
			prepStm.setInt(2, RoleDAO.idByRole(user.getPrincipalRole()));
			prepStm.setInt(3, RoleDAO.idByRole(user.getSecondaryRole()));
			prepStm.setByte(4, (byte) ((user.isActive() == true) ? 1 : 0));
			
		} catch (SQLException e) {
			throw new AccessException("Access error");
		}
		try {
			prepStm.execute();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new AccessException("Save error");
		}
	}


}
