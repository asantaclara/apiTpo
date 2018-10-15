package dto;

public class ProductDTO {

	private int productId;
	private String title;
	private String description;
	private float price;
	
	public ProductDTO(int productId, String title, String description, float price) {
		this.productId = productId;
		this.title = title;
		this.description = description;
		this.price = price;
	}
	
	public int getProductId() {
		return productId;
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
	
	
}
