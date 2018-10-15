package dto;

import java.util.List;

import backEnd.Role;

public class BoardDTO {

	private int boardID;
	private List<ClaimDTO> claims;
	private Role role;
	
	public BoardDTO(int boardID, Role role) {
		super();
		this.boardID = boardID;
		this.claims = claims;
		this.role = role;
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

	public Role getRole() {
		return role;
	}
	
	
	

	
}
