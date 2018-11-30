package backEnd;

import dao.InvoiceItemDAO;
import dto.InvoiceItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;

public class InvoiceItem {

	private Invoice invoice;
	private String inconsistency;
	private int id = 0;
	
	public InvoiceItem(Invoice invoice, String inconsistency) throws InvalidInvoiceItemException {
		if(invoice == null || inconsistency.length() == 0) {
			throw new InvalidInvoiceItemException("Invalid invoiceItem");
		}
		this.inconsistency = inconsistency;
		this.invoice = invoice;
	}
	
	public Invoice getInvoice() {
		return invoice;
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) throws InvalidInvoiceItemException {
		if(id == 0) {
			throw new InvalidInvoiceItemException("The id is invalid");
		}
		this.id = id;
	}
	
	public String getInconsistency() {
		return inconsistency;
	}
	
	public InvoiceItemDTO toDTO() {
		return new InvoiceItemDTO(invoice.getId(), inconsistency);
	}
	
	public void save(int claimId) throws ConnectionException, AccessException, InvalidProductException, InvalidInvoiceItemException {
		new InvoiceItemDAO().save(this, claimId);
		
	}
}
