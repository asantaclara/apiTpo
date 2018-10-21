package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.MoreQuantityClaimDAO;
import dto.MoreQuantityClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidProductItemException;

public class MoreQuantityClaim extends IndividualClaim {

	private List<ProductItem> products = new LinkedList<>();
	private ClaimType claimType;
	private Invoice invoice;
	
	public MoreQuantityClaim(Client client, Date date, String description, ClaimType claimType, Invoice invoice) throws InvalidClaimException, InvalidInvoiceException {
		super(client, date, description);		
		this.claimType = claimType;
		
		if(invoice.validateClient(client)) {			
			this.invoice = invoice;
		} else {
			throw new InvalidInvoiceException("The invoice doesn't belog to the client");
		}
		
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) {
		// TODO Auto-generated method stub

	}

	public void addProductItem(Product product, int quantity) throws InvalidProductItemException {
		if(invoice.validateProductItem(product, quantity)){			
			products.add(new ProductItem(product, quantity));
		} else {
			throw new InvalidProductItemException("The product doesn't belong to the invoice");
		}
	}
	
	@Override
	public MoreQuantityClaimDTO toDTO() {
		MoreQuantityClaimDTO aux = new MoreQuantityClaimDTO(claimId, client.getId(), description, claimType.name(), invoice.getId(), date);
		
		for (ProductItem p : products) {
			aux.addProductItemDTO(p.toDTO());
		}
		
		return aux;
	}

	public ClaimType getClaimType() {
		return claimType;
	}
	
	public List<ProductItem> getProducts() {
		return products;
	}

	public Invoice getInvoice() {
		return invoice;
	}
	
	@Override
	public void save() throws ConnectionException, AccessException, InvalidClaimException, InvalidProductException {
		MoreQuantityClaimDAO.save(this);
	}

}