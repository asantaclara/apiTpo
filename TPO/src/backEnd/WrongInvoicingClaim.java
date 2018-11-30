package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.WrongInvoicingClaimDAO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;

public class WrongInvoicingClaim extends IndividualClaim {

	private List<InvoiceItem> invoices;
	
	public WrongInvoicingClaim(Client client, Date date, String description) throws InvalidClaimException{
		super(client, date, description);
		invoices = new LinkedList<>();
	}

	public void addInovice(Invoice invoice, String inconsistency) throws InvalidInvoiceItemException {
		invoices.add(new InvoiceItem(invoice, inconsistency));
	}
	

	@Override
	public WrongInvoicingClaimDTO toDTO() {
		WrongInvoicingClaimDTO aux = new WrongInvoicingClaimDTO();

		aux.setClaimId(claimId);
		aux.setClientId(client.getId());
		aux.setDescription(description);
		aux.setDate(date);
		aux.setState(actualState.name());
		
		for (InvoiceItem i : invoices) {
			aux.addInvoiceItemDTO(i.getId(),i.getInconsistency());
		}
		
		for (Transition transition : transitions) {
			aux.addTransition(transition.toDTO());
		}
		
		return aux;
	}

	@Override
	public void save() throws ConnectionException, AccessException, InvalidProductException, InvalidInvoiceItemException, InvalidClaimException {
		new WrongInvoicingClaimDAO().save(this);
	}
	
	public List<InvoiceItem> getInvoices() {
		return invoices;
	}

	public void addInvoiceItem(InvoiceItem i) {
		invoices.add(i);
	}

}
