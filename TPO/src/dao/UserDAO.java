package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import backEnd.User;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidRoleException;
import exceptions.InvalidUserException;

public class UserDAO {

	public static void save(User user) throws AccessException, ConnectionException, InvalidRoleException, InvalidUserException {
		Connection con = SqlUtils.getConnection();
		PreparedStatement prepStm;
	
		if(user.getUserId() != 0) {
			throw new InvalidUserException("User already in data base");
		}
		
		user.setUserId((SqlUtils.lastId("Users", "UserId") + 1)); 
		
		try {
			prepStm = con.prepareStatement("insert into Users values(?,?,?,?,?)");
			prepStm.setInt(1, user.getUserId());
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
}
