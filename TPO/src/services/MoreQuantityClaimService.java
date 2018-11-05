package services;

import java.util.Date;
import java.util.List;

import backEnd.ClaimType;
import backEnd.Client;
import backEnd.Invoice;
import backEnd.MoreQuantityClaim;
import dao.ClientDAO;
import dao.InvoiceDAO;
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
import exceptions.InvalidZoneException;

public class MoreQuantityClaimService {
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
int 	clientId = dto.getClientId(); //Con este clientId tengo que traer al client desde la BD y lo llamo existingClient.
		
		Client existingClient =  new ClientDAO().getClient(clientId);
		
		List<ProductItemDTO> productItemsDTO = dto.getProducts();
		
		ClaimType claimType = ClaimType.valueOf(dto.getClaimType());
		
		int invoiceId = dto.getInvoiceId();
		Invoice existingInvoice = new InvoiceDAO().getInvoice(invoiceId);
		
		String description = dto.getDescription();
		
		MoreQuantityClaim newClaim = new MoreQuantityClaim(existingClient, new Date(), description, claimType, existingInvoice);
		
		for (ProductItemDTO productItemDTO : productItemsDTO) {
			newClaim.addProductItem(new ProductDAO().getProduct(productItemDTO.getProductId()), productItemDTO.getQuantity());
		}
		
		newClaim.save();
		
		return newClaim.getClaimId();
	}
}
