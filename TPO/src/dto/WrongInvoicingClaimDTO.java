package dto;

import java.util.LinkedList;
import java.util.List;

public class WrongInvoicingClaimDTO extends ClaimDTO {


	private List<InvoiceItemDTO> invoices;
	
	public WrongInvoicingClaimDTO(int claimId, String clientCuit, String description) {
		super(claimId, clientCuit, description);
		invoices = new LinkedList<>();
	}

	public List<InvoiceItemDTO> getInvoices() {
		return invoices;
	}
	
	public void addInvoiceItemDTO(InvoiceItemDTO invoiceItem) {
		invoices.add(invoiceItem);
	}
}
