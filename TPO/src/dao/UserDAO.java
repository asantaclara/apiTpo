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

	public void save(User user) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm1;
			PreparedStatement prepStm2;
			
			if(user.getId() != 0) {
				throw new InvalidUserException("User already in data base");
			}
			
			user.setId((SqlUtils.lastId("Users", "UserId") + 1)); 
			
			try {
				con.setAutoCommit(false);
				prepStm1 = con.prepareStatement("insert into Users values(?,?,?,?,?)");
				prepStm1.setInt(1, user.getId());
				prepStm1.setString(2, user.getName());
				prepStm1.setInt(3, new RoleDAO().idByRole(user.getPrincipalRole()));
				prepStm1.setInt(4, new RoleDAO().idByRole(user.getSecondaryRole()));
				prepStm1.setByte(5, (byte) ((user.isActive() == true) ? 1 : 0));
				
				prepStm2 = con.prepareStatement("insert into UserLogin values (?,?,?)");
				prepStm2.setInt(1, user.getId());
				prepStm2.setString(2, user.getUserName());
				prepStm2.setString(3, user.getPassword());
				
			} catch (SQLException e) {
				e.printStackTrace();
				throw new AccessException("Access error");
			}		
			
			try {
				
				prepStm1.execute();
				prepStm2.execute();
				con.commit();
				
			} catch (SQLException e) {
				try {
					con.rollback();		
				} catch (SQLException e2) {
					throw new AccessException("DB Error");
				}
				if(e.getErrorCode() == 2627) {
					throw new InvalidUserException("Duplicate userName");
				}
				throw new AccessException("Save error");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	
	public void modify(User user) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();
		try {
			PreparedStatement prepStm1;
			PreparedStatement prepStm2;
			
			if(user.getId() == 0) {
				throw new InvalidUserException("User not in data base");
			}
			
			try {
				con.setAutoCommit(false);
				prepStm1 = con.prepareStatement("UPDATE Users SET Name = ?, RolId1 = ?, RolId2 = ?, Active = ? WHERE UserId = " + user.getId());
				prepStm1.setString(1, user.getName());
				prepStm1.setInt(2, new RoleDAO().idByRole(user.getPrincipalRole()));
				prepStm1.setInt(3, new RoleDAO().idByRole(user.getSecondaryRole()));
				prepStm1.setByte(4, (byte) ((user.isActive() == true) ? 1 : 0));
				
				prepStm2 = con.prepareStatement("UPDATE UserLogin SET Name = ?, Password = ? WHERE UserId = " + user.getId());
				prepStm2.setString(1, user.getUserName());
				prepStm2.setString(2, user.getPassword());
			} catch (SQLException e) {
				throw new AccessException("Access error");
			}
			try {
				prepStm1.execute();
				prepStm2.execute();
				con.commit();
			} catch (SQLException e) {
				try {
					con.rollback();			
				} catch (SQLException e2) {
					throw new AccessException("DB Error");
				}
				if(e.getErrorCode() == 2627) {
					throw new InvalidUserException("Duplicate userName");
				}
				throw new AccessException("Save error");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}
	public User getUser(int userId) throws InvalidUserException, ConnectionException, AccessException, InvalidRoleException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT Users.UserId, Users.Name, Users.Active as UserActive, Role1.Role as Role1, Role2.Role as Role2, "
					+ "UserLogin.name as Username, UserLogin.password as password FROM Users JOIN Roles AS Role1 ON "
					+ "Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON Users.RolId2 = Role2.RoleId JOIN "
					+ "UserLogin on Users.UserId = UserLogin.userId WHERE Users.UserId = " + userId; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					if(rs.getByte(3) == 1) { 
						User newUser = new User(rs.getString(2), Roles.valueOf(rs.getString(4)), rs.getString(6), rs.getString(7));
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public List<User> getAllUsers() throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		String SQL = "SELECT Users.UserId, Users.Name, Users.Active, Role1.Role as Role1, Role2.Role as Role2, UserLogin.name as Username, "
				+ "UserLogin.password as password FROM Users JOIN Roles AS Role1 ON Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON "
				+ "Users.RolId2 = Role2.RoleId JOIN UserLogin on Users.UserId = UserLogin.userId WHERE Users.Active = 1";

		return getAllUsersPrivate(SQL);
	}
	
	public static List<User> getAllUserByRole(Roles role) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		
		String SQL = "SELECT Users.UserId, Users.Name, Users.Active, Role1.Role as Role1, Role2.Role as Role2, UserLogin.name as Username, "
				+ "UserLogin.password as password FROM Users JOIN Roles AS Role1 ON Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON "
				+ "Users.RolId2 = Role2.RoleId JOIN UserLogin on Users.UserId = UserLogin.userId WHERE Users.Active = 1 "
				+ "AND Role2.Role = '" + role.name() + "'";
		
		return getAllUsersPrivate(SQL);

	}
	
	private static List<User> getAllUsersPrivate(String sql) throws ConnectionException, AccessException, InvalidRoleException, InvalidUserException{
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				List<User> returnList = new LinkedList<>();
				User newUser = null;
				
				while(rs.next()){				
					newUser = new User(rs.getString(2), Roles.valueOf(rs.getString(4)), rs.getString(6), rs.getString(7));
					newUser.addRole(Roles.valueOf(rs.getString(5)));
					newUser.setId(rs.getInt(1));
					returnList.add(newUser);
				}
				return returnList;
				
			} catch (SQLException e) {
				throw new ConnectionException("Data not reachable");
			}
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}

	public User getUserByUsername(String userName) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();  
		try {
			Statement stmt = SqlUtils.createStatement(con);  
			ResultSet rs = null;
			
			String sql = "SELECT Users.UserId, Users.Name, Users.Active as UserActive, Role1.Role as Role1, Role2.Role as Role2, "
					+ "UserLogin.name as Username, UserLogin.password as password FROM Users JOIN Roles AS Role1 ON "
					+ "Users.RolId1 = Role1.RoleId JOIN Roles AS Role2 ON Users.RolId2 = Role2.RoleId JOIN "
					+ "UserLogin on Users.UserId = UserLogin.userId WHERE UserLogin.name = '" + userName + "'"; 
			
			rs = SqlUtils.executeQuery(stmt, con, sql);
			
			try {
				if(rs.next()){
					if(rs.getByte(3) == 1) { 
						User newUser = new User(rs.getString(2), Roles.valueOf(rs.getString(4)), rs.getString(6), rs.getString(7));
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
			
		} finally {
			SqlUtils.closeConnection(con);
		}
	}


}
