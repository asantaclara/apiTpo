package services;

import backEnd.Product;
import dao.ProductDAO;
import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidProductException;

public class ProductService {
	
	private static ProductService  instance = null;
	
	public static ProductService getIntance() {
		if (instance == null) {
			instance = new ProductService();
		}
		return instance;
	}
	
	private ProductService() {
		
	}
	
	public int addProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		Product p = new Product(dto.getTitle(), dto.getDescription(), dto.getPrice());
		p.saveInDB();
		return p.getProductId();
	}
	public void modifyProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		
		if(dto.getProductId() == 0) {
			throw new InvalidProductException("Id missing");
		}
		Product existingProduct = new ProductDAO().getProduct(dto.getProductId());
		
		if(existingProduct != null) {
			existingProduct.modify(dto.getTitle(), dto.getDescription(), dto.getPrice());
		}
	}
	public void removeProduct(ProductDTO dto) throws ConnectionException, AccessException, InvalidProductException {
		Product productToRemove = new ProductDAO().getProduct(dto.getProductId());
		
		productToRemove.deactivateProduct();
	}
	
}
