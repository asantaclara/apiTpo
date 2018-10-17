package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;

public class WrongInvoicingClaim extends IndividualClaim {

	private List<InvoiceItem> invoices;
	
	public WrongInvoicingClaim(Client client, Date date, String description) throws InvalidClaimException{
		super(client, date, description);
		invoices = new LinkedList<>();
	}

	public void addInovice(Invoice invoice, String inconsistency) {
		invoices.add(new InvoiceItem(invoice, inconsistency));
	}
	
	@Override
	public void treatClaim(User responsable, State newState, String description) {

	}

	@Override
	public WrongInvoicingClaimDTO toDTO() {
		WrongInvoicingClaimDTO aux = new WrongInvoicingClaimDTO(claimId, client.getId(), description);
		
		for (InvoiceItem i : invoices) {
			aux.addInvoiceItemDTO(i.toDTO());
		}
		
		return aux;
	}

	@Override
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}

}
