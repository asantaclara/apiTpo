package dto;

public class UserDTO {

	private int userId;
	private String name;
	private String principalRole;
	private String secondaryRole;
	private String userName;
	private String password;
	private int ranking;
	private int avgReponseTime;
	
	
	
	public int getRanking() {
		return ranking;
	}

	public int getAvgReponseTime() {
		return avgReponseTime;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public void setAvgReponseTime(int avgReponseTime) {
		this.avgReponseTime = avgReponseTime;
	}

	public UserDTO() {
	
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setUserId(int userId) {
		this.userId = userId;
	}


	public void setName(String name) {
		this.name = name;
	}


	public void setPrincipalRole(String principalRole) {
		this.principalRole = principalRole;
	}


	public void setSecondaryRole(String secondaryRole) {
		this.secondaryRole = secondaryRole;
	}


	public int getUserId() {
		return userId;
	}
	public String getName() {
		return name;
	}
	public String getPrincipalRole() {
		return principalRole;
	}
	public String getSecondaryRole() {
		return secondaryRole;
	}
	
	@Override
	public String toString() {
		return userId + "-" + userName;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[7];
		
		aux[0] = String.valueOf(ranking);
		aux[1] = String.valueOf(avgReponseTime);
		aux[2] = String.valueOf(userId);
		aux[3] = name;
		aux[4] = userName;
		aux[5] = principalRole;
		aux[6] = secondaryRole;
		
		return aux;
	}
	
	public boolean equals(Object other){
	    boolean result;
	    
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } else {
	        UserDTO otherUserDTO = (UserDTO)other;
	        result = userId == otherUserDTO.getUserId();
	    }
	    
	    return result;
	}
}
