package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dto.MoreQuantityClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidDateException;
import exceptions.InvalidDescriptionException;

public class MoreQuantityClaim extends IndividualClaim {

	private List<ProductItem> products;
	private ClaimType claimType;
	private Invoice invoice;
	
	public MoreQuantityClaim(Client client, Date date, String description, ClaimType claimType, Invoice invoice) throws InvalidClientException, InvalidDateException, InvalidDescriptionException {
		super(client, date, description);
		products = new LinkedList<>();
		this.invoice = invoice;
		this.claimType = claimType;
	}

	@Override
	public void treatClaim(User responsable, State newState, String description) {
		// TODO Auto-generated method stub

	}

	public void addProductItem(Product product, int quantity) {
		products.add(new ProductItem(product, quantity));
	}
	
	@Override
	public MoreQuantityClaimDTO toDTO() {
		MoreQuantityClaimDTO aux = new MoreQuantityClaimDTO(claimId, client.getId(), description, claimType.name(), invoice.getId());
		
		for (ProductItem p : products) {
			aux.addProductItemDTO(p.toDTO());
		}
		
		return aux;
	}

	@Override
	public void save() throws ConnectionException, AccessException {
		// TODO Auto-generated method stub
		
	}

}