package dto;

import java.util.LinkedList;
import java.util.List;

public class WrongInvoicingClaimDTO extends ClaimDTO {


	private List<InvoiceItemDTO> invoices = new LinkedList<>();
	
	public WrongInvoicingClaimDTO() {
		
	}
	
	public List<InvoiceItemDTO> getInvoices() {
		return invoices;
	}
	
	public void addInvoiceItemDTO(int invoiceId, String inconsistency) {
		invoices.add(new InvoiceItemDTO(invoiceId, inconsistency));
	}
	
	@Override
	public List<TransitionDTO> getTransitions() {
		return transitions;
	}

	@Override
	public void addTransition(TransitionDTO dto) {
		transitions.add(dto);
	}
}
