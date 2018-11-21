package dto;

public class ClaimsPerCategoryDTO {

	int moreQuantityClaims, wrongInvoicingClaims, compositeClaims, incompatibleZoneClaims;
	
	public ClaimsPerCategoryDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getMoreQuantityClaims() {
		return moreQuantityClaims;
	}

	public int getWrongInvoicingClaims() {
		return wrongInvoicingClaims;
	}

	public int getCompositeClaims() {
		return compositeClaims;
	}

	public int getIncompatibleZoneClaims() {
		return incompatibleZoneClaims;
	}

	public void setMoreQuantityClaims(int moreQuantityClaims) {
		this.moreQuantityClaims = moreQuantityClaims;
	}

	public void setWrongInvoicingClaims(int wrongInvoicingClaims) {
		this.wrongInvoicingClaims = wrongInvoicingClaims;
	}

	public void setCompositeClaims(int compositeClaims) {
		this.compositeClaims = compositeClaims;
	}

	public void setIncompatibleZoneClaims(int incompatibleZoneClaims) {
		this.incompatibleZoneClaims = incompatibleZoneClaims;
	}
	
	
	
}
