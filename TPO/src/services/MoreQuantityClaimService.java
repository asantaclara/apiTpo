package services;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import backEnd.ClaimType;
import backEnd.Client;
import backEnd.Invoice;
import backEnd.MoreQuantityClaim;
import dao.ClientDAO;
import dao.InvoiceDAO;
import dao.MoreQuantityClaimDAO;
import dao.ProductDAO;
import dto.MoreQuantityClaimDTO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;
import exceptions.InvalidRoleException;
import exceptions.InvalidTransitionException;
import exceptions.InvalidUserException;
import exceptions.InvalidZoneException;
import observer.Observable;

public class MoreQuantityClaimService extends Observable{
	private static MoreQuantityClaimService  instance = null;
	
	public static MoreQuantityClaimService getIntance() {
		if (instance == null) {
			instance = new MoreQuantityClaimService();
		}
		return instance;
	}
	
	private MoreQuantityClaimService() {
		
	}
	
	public int addMoreQuantityClaim(MoreQuantityClaimDTO dto) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidProductItemException, InvalidClaimException {
		int clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		
		Client existingClient = ClientService.getIntance().getClientById(clientId);
		
		List<ProductItemDTO> productItemsDTO = dto.getProducts();
		
		ClaimType claimType = ClaimType.valueOf(dto.getClaimType());
		
		int invoiceId = dto.getInvoiceId();
		Invoice existingInvoice = InvoiceService.getIntance().getInvoiceById(invoiceId);
		
		String description = dto.getDescription();
		
		MoreQuantityClaim newClaim = new MoreQuantityClaim(existingClient, new Date(), description, claimType, existingInvoice);
		
		for (ProductItemDTO productItemDTO : productItemsDTO) {
			newClaim.addProductItem(ProductService.getIntance().getProductById(productItemDTO.getProduct().getProductId()), productItemDTO.getQuantity());
		}
		
		newClaim.save();
		updateObservers(newClaim);
		return newClaim.getClaimId();
	}
	
	private void updateObservers(MoreQuantityClaim claim) {
		List<MoreQuantityClaimDTO> claimToSend = new LinkedList<>();
		claimToSend.add(claim.toDTO());
		updateObservers(claimToSend);
	}

	public List<MoreQuantityClaimDTO> getAllMoreQuantityClaimsDTO() throws ConnectionException, InvalidClaimException, InvalidInvoiceException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidProductItemException {
		List<MoreQuantityClaimDTO> claimsDTO = new LinkedList<>();
		
		for (MoreQuantityClaim m : new MoreQuantityClaimDAO().getAllMoreQuantityClaims()) {
			claimsDTO.add(m.toDTO());
		}
		return claimsDTO;
	}
	public List<MoreQuantityClaimDTO> getAllMoreQuantityClaimsDTOFromClient(int clientId) throws ConnectionException, InvalidClaimException, InvalidInvoiceException, AccessException, InvalidClientException, InvalidProductException, InvalidZoneException, InvalidUserException, InvalidRoleException, InvalidTransitionException, InvalidProductItemException {
		List<MoreQuantityClaimDTO> claimsDTO = new LinkedList<>();
		
		for (MoreQuantityClaim m : new MoreQuantityClaimDAO().getAllMoreQuantityClaimsFromClient(clientId)) {
			claimsDTO.add(m.toDTO());
		}
		return claimsDTO;
	}
	
}
