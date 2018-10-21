package backEnd;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import dao.MoreQuantityClaimDAO;
import dto.MoreQuantityClaimDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClaimException;
import exceptions.InvalidProductException;

public class MoreQuantityClaim extends IndividualClaim {

	private List<ProductItem> products;
	private ClaimType claimType;
	private Invoice invoice;
	
	public MoreQuantityClaim(Client client, Date date, String description, ClaimType claimType, Invoice invoice) throws InvalidClaimException {
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