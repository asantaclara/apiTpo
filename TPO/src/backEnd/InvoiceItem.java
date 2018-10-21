package backEnd;

import dto.InvoiceItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;

public class InvoiceItem {

	private Invoice invoice;
	private String inconsistency;
	private int id = 0;
	
	public InvoiceItem(Invoice invoice, String inconsistency) {
		this.inconsistency = inconsistency;
		this.invoice = invoice;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getInconsistency() {
		return inconsistency;
	}
	
	public InvoiceItemDTO toDTO() {
		return new InvoiceItemDTO(invoice.getId(), inconsistency);
	}
	
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}
}
