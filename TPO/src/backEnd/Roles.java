package backEnd;

public enum Roles {

	DISTRIBUTION_RESPONSABLE(1),
	INVOICING_RESPONSABLE(2),
	CALL_CENTER_RESPONSABLE(3),
	ZONE_RESPONSABLE(4),
	QUERY_USER(5),
	ADMINISTRATOR(6);
	
	int aux;
	
	Roles(int aux){
		this.aux = aux;
	}
	
	int getRole() {
		return aux;
	}

}
