package dto;

public class InvoiceItemDTO {

	private int invoiceId;
	private String inconsistency;
	
	public InvoiceItemDTO(int invoiceId, String inconsistency) {
		this.invoiceId = invoiceId;
		this.inconsistency = inconsistency;
	}

	public int getInvoiceId() {
		return invoiceId;
	}
	public String getInconsistency() {
		return inconsistency;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[3];
		
		aux[0] = String.valueOf(invoiceId);
		aux[1] = inconsistency;
		
		return aux;
	}
	
}
