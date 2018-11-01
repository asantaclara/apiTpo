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
	
	
}
