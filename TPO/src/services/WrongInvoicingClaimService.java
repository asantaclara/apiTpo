package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.Client;
import backEnd.Invoice;
import backEnd.MoreQuantityClaim;
import backEnd.WrongInvoicingClaim;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.WrongInvoicingClaimDAO;
import dto.InvoiceItemDTO;
import dto.MoreQuantityClaimDTO;
import dto.WrongInvoicingClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidInvoiceItemException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class WrongInvoicingClaimService extends Observable{

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
		Client existingClient =  ClientService.getIntance().getClientById(clientId);
		
		WrongInvoicingClaim newClaim = new WrongInvoicingClaim(existingClient, new Date(), dto.getDescription());
		
		List<InvoiceItemDTO> invoiceItems = dto.getInvoices();
		
		for (InvoiceItemDTO invoiceItemDTO : invoiceItems) {
			int invoiceId = invoiceItemDTO.getInvoiceId();
			Invoice existingInvoice = InvoiceService.getIntance().getInvoiceById(invoiceId);
			
			if(existingInvoice.validateClient(existingClient)) { //Si el cliente pertenece a la factura que me mandaron
				String inconsistency = invoiceItemDTO.getInconsistency();
				newClaim.addInovice(existingInvoice, inconsistency);
			}else {
				throw new InvalidClaimException("Invoice doesn't belong to client");
			}
		}
		newClaim.save();
		updateObservers();
		return newClaim.getClaimId();
	}
	

	public List<WrongInvoicingClaimDTO> getAllWrongInvoicingClaimsDTO() throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidProductItemException {
		
		return listOfClaimsTODTO(new WrongInvoicingClaimDAO().getAllWrongInvoicingClaims());
	}

	public List<WrongInvoicingClaimDTO> getAllWrongInvoicingClaimsDTOFromClient(int clientId) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidProductItemException {
		return listOfClaimsTODTO(new WrongInvoicingClaimDAO().getAllWrongInvoicingClaimsFromClient(clientId));
	}

	public List<WrongInvoicingClaimDTO> getAllClaimsForInvoiceResponsable() throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidProductItemException {
		return listOfClaimsTODTO(new WrongInvoicingClaimDAO().getAllClaimsForInvoiceResponsable());
	}
	private List<WrongInvoicingClaimDTO> listOfClaimsTODTO(List<WrongInvoicingClaim> claims){
		List<WrongInvoicingClaimDTO> claimsDTO =  new LinkedList<>();
		
		for (WrongInvoicingClaim i : claims) {
			claimsDTO.add(i.toDTO());
		}
		return claimsDTO;
	}

	public List<WrongInvoicingClaimDTO> getAllOpenWrongInvoicingClaimsByClient(int clientId) throws ConnectionException, AccessException, InvalidInvoiceException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidClaimException, InvalidProductItemException {
		return listOfClaimsTODTO(new WrongInvoicingClaimDAO().getAllOpenWrongInvoicingClaimsByClient(clientId));
	}
}
