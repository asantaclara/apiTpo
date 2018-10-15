package backEnd;

import dto.InvoiceItemDTO;

public class InvoiceItem {

	private Invoice invoice;
	private String inconsistency;
	
	public InvoiceItem(Invoice invoice, String inconsistency) {
		this.inconsistency = inconsistency;
		this.invoice = invoice;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public String getInconsistency() {
		return inconsistency;
	}
	
	public InvoiceItemDTO toDTO() {
		return new InvoiceItemDTO(invoice.getId(), inconsistency);
	}
}
