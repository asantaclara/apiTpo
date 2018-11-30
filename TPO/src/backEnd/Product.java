package backEnd;

import dao.ProductDAO;
import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

public class Product {
	
	private String title;
	private String description;
	private float price;
	private int productId=0;
	private boolean activeProduct;
	
	public Product(String title, String description, float price) throws InvalidProductException {
		if(title == null || description == null || price <= 0 || title.length() == 0 || description.length() == 0) {
			throw new InvalidProductException("Missing parameters");
		} else {			
			this.title = title;
			this.description = description;
			this.price = price;
			activeProduct = true;
		}
	}
	
	public int getProductId() {
		return productId;
	}
	
	public void modify(String title, String description, float price) throws ConnectionException, AccessException, InvalidProductException {
		
		if(title != null && title.length() != 0) {			
			this.title = title;
		}
		if (description != null && description.length() != 0) {			
			this.description = description;
		}
		if (price > 0) {			
			this.price = price;
		}
		new ProductDAO().modify(this);
	}
	
	public ProductDTO toDTO() {
		
		ProductDTO aux = new ProductDTO();
		
		aux.setProductId(productId);
		aux.setTitle(title);
		aux.setDescription(description);
		aux.setPrice(price);
		
		return aux;
	}
	
	public void save() throws ConnectionException, AccessException, InvalidProductException {
		new ProductDAO().save(this);
	}
	
	public void modify() throws ConnectionException, AccessException, InvalidProductException {
		new ProductDAO().modify(this);
	}

	public void deactivateProduct() throws ConnectionException, AccessException, InvalidProductException {
		activeProduct = false;
		new ProductDAO().modify(this);
		
	}
	
	public boolean isActive() {
		return activeProduct;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public float getPrice() {
		return price;
	}

	public void setProductId(int productId) throws InvalidProductException {
		if(productId < 1) {
			throw new InvalidProductException("Invalid id");
		}
		this.productId = productId;
	}
	
	
}
