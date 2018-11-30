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
		return productId + "-" + title;
	}
	
	public String[] toDataRow() {
		String[] aux = new String[4];
		
		aux[0] = String.valueOf(productId);
		aux[1] = title;
		aux[2] = description;
		aux[3] = String.valueOf(price);
		
		return aux;
		
	}
	public boolean equals(Object other){
	    boolean result;
	    
	    if((other == null) || (getClass() != other.getClass())){
	        result = false;
	    } else {
	        ProductDTO otherProductDTO = (ProductDTO)other;
	        result = productId == otherProductDTO.getProductId();
	    }
	    
	    return result;
	}
	
	
}
