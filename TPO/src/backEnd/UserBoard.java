package backEnd;

import java.util.List;

import dto.BoardDTO;

public class UserBoard {

	private static int boardId;
	private List<Claim> claims;
	private Role role;
	private List<String> boardToShow;
	
	public UserBoard(Role role, List<String> boardToShow) {
		this.role = role;
	}
	
	public void addBoardToShow(String board) {
		boardToShow.add(board);
	}
	
	public void treatClaim(int claimId, User user, State newState, String description) {
		
	}
	
	public List<Claim> getAllClaims(){
		return claims; 
	}
	
	public State getClaimState(int claimId) {
		for (Claim c : claims) {
			if (c.getClaimId() == claimId) {
				return c.getActualState();
			}
		}
		return null; // Devolver una exception porque no se encuentra el claimId
	}
	
	public BoardDTO toDTO() {
		BoardDTO aux = new BoardDTO(boardId, role);
		
		for (Claim c : claims) {
			aux.addClaimDTO(c.toDTO());
		}
		
		return aux;
	}
	
	public void addClaim(Claim claim) {
		claims.add(claim);
	}
	
}
