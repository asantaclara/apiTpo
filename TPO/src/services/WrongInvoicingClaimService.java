package services;

import java.util.Date;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.WrongInvoicingClaim;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dto.InvoiceItemDTO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;

public class WrongInvoicingClaimService {

	private static WrongInvoicingClaimService  instance = null;
	
	public static WrongInvoicingClaimService getIntance() {
		if (instance == null) {
			instance = new WrongInvoicingClaimService();
		}
		return instance;
	}
	
	private WrongInvoicingClaimService() {
		
	}
	
	public int addWrongInvoicingClaim(WrongInvoicingClaimDTO dto) throws ConnectionException, AccessException, InvalidClientException, InvalidZoneException, InvalidClaimException, InvalidInvoiceException, InvalidProductException, InvalidInvoiceItemException {
		
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		WrongInvoicingClaim newClaim = new WrongInvoicingClaim(existingClient, new Date(), dto.getDescription());
		
		List<InvoiceItemDTO> invoiceItems = dto.getInvoices();
		
		for (InvoiceItemDTO invoiceItemDTO : invoiceItems) {
			int invoiceId = invoiceItemDTO.getInvoiceId();
			Invoice existingInvoice = new InvoiceDAO().getInvoice(invoiceId);
			
			if(existingInvoice.validateClient(existingClient)) { //Si el cliente pertenece a la factura que me mandaron
				String inconsistency = invoiceItemDTO.getInconsistency();
				newClaim.addInovice(existingInvoice, inconsistency);
			}else {
				throw new InvalidClaimException("Invoice doesn't belong to client");
			}
		}
		newClaim.save();
		
		return newClaim.getClaimId();
	}
}