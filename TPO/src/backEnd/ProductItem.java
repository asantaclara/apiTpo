package backEnd;

import dao.ProductItemDAO;
import dto.ProductItemDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

public class ProductItem {

	private Product product;
	private int quantity;
	private int id = 0;
	
	public ProductItem(Product product, int quantity) {
		this.product = product;
		this.quantity = quantity;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public int getQuantity() {
		return quantity;
	}
	
	public ProductItemDTO toDTO() {
		return new ProductItemDTO(product.getProductId(), quantity);
	}
	
	public void save() throws ConnectionException, AccessException, InvalidProductException {
		ProductItemDAO.save(this);
		
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
}
