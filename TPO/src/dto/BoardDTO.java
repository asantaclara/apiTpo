package dto;

import java.util.List;

import backEnd.Role;

public class BoardDTO {

	private int boardID;
	private List<ClaimDTO> claims;
	private int roleId;
	
	public BoardDTO() {
	}
	
	public void setBoardID(int boardID) {
		this.boardID = boardID;
	}
	
	public void setRole(int roleId) {
		this.roleId = roleId;
	}
	
	public void addClaimDTO(ClaimDTO claimDTO) {
		claims.add(claimDTO);
	}

	public int getBoardID() {
		return boardID;
	}

	public List<ClaimDTO> getClaims() {
		return claims;
	}

	public int getRoleId() {
		return roleId;
	}
	
	
	

	
}
