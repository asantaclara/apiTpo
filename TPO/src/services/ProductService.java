package services;

import java.util.LinkedList;
import java.util.List;

import backEnd.Invoice;
import backEnd.Product;
import backEnd.ProductItem;
import dao.InvoiceDAO;
import dao.ProductDAO;
import dto.ProductDTO;
import exceptions.AccessException;
import exceptions.ConnectionException;
import exceptions.InvalidClientException;
import exceptions.InvalidInvoiceException;
import exceptions.InvalidProductException;
import exceptions.InvalidZoneException;

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

	public List<ProductDTO> getInvoiceProducts(int invoiceId) throws AccessException, InvalidInvoiceException, ConnectionException, InvalidClientException, InvalidProductException, InvalidZoneException {
		Invoice invoice = new InvoiceDAO().getInvoice(invoiceId);
		List<ProductDTO> returnList = new LinkedList<>();
		
		for (ProductItem pi : invoice.getItems()) {
			returnList.add(pi.getProduct().toDTO());
		}
		
		return returnList;
	}

	public List<ProductDTO> getAllProducts() throws ConnectionException, AccessException, InvalidProductException {
		List<Product> aux =  new ProductDAO().getAllProducts();
		List<ProductDTO> returnList = new LinkedList<>();
		
		for (Product product : aux) {
			returnList.add(product.toDTO());
		}
		
		return returnList;
	}
	
}
