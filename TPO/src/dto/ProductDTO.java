package dto;

public class ProductDTO {

	private int productId;
	private String title;
	private String description;
	private float price;
	
//	int productId, String title, String description, float price
	
	public ProductDTO() {
	
	}
	
	public void setProductId(int productId) {
		this.productId = productId;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public void setPrice(float price) {
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
	@Override
	public String toString() {
		return title;
	}
	
}
