package backEnd;

import java.util.List;

import dto.RoleDTO;

public class Role {

	private static int roleId;
	private Roles description;
	private List<User> users;
	private List<String> boardToShow;
	
	public Role(Roles description, List<String> boardToShow) {
		this.description = description;
		this.boardToShow = boardToShow;
	}
	
	public void addUser(User user) {
		users.add(user);
	}
	
	public void removeUser(User user) {
		users.remove(user);
	}
	
	public RoleDTO toDTO() {
		return new RoleDTO(description, -1);
		//Chequear que onda con este constructor
	}
}
