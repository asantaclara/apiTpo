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
	
	public String[] toDataRow() {
		String[] aux = new String[4];
		
		aux[0] = String.valueOf(moreQuantityClaims);
		aux[1] = String.valueOf(wrongInvoicingClaims);
		aux[2] = String.valueOf(compositeClaims);
		aux[3] = String.valueOf(incompatibleZoneClaims);

		
		return aux;
	}
	
}
