package dto;

import java.util.LinkedList;
import java.util.List;

public class WrongInvoicingClaimDTO extends ClaimDTO {


	private List<InvoiceItemDTO> invoices;
	
	public WrongInvoicingClaimDTO(int claimId, int clientId, String description) {
		super(claimId, clientId, description);
		invoices = new LinkedList<>();
	}

	public List<InvoiceItemDTO> getInvoices() {
		return invoices;
	}
	
	public void addInvoiceItemDTO(InvoiceItemDTO invoiceItem) {
		invoices.add(invoiceItem);
	}
}
