package dto;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class WrongInvoicingClaimDTO extends ClaimDTO {


	private List<InvoiceItemDTO> invoices;
	
	public WrongInvoicingClaimDTO(int claimId, int clientId, String description, Date date) {
		super(claimId, clientId, description, date);
		invoices = new LinkedList<>();
	}

	public List<InvoiceItemDTO> getInvoices() {
		return invoices;
	}
	
	public void addInvoiceItemDTO(InvoiceItemDTO invoiceItem) {
		invoices.add(invoiceItem);
	}
}
