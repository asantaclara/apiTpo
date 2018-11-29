package dto;

public class ClaimQuantityPerMonthDTO {

	private int quantity;
	private String month;
	
	
	public ClaimQuantityPerMonthDTO() {
		
	}
	
	public int getQuantity() {
		return quantity;
	}
	public String getMonth() {
		return month;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[2];
		
		aux[0] = month;
		aux[1] = String.valueOf(quantity);
		
		return aux;
	}
	
	
}
