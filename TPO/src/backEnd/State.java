package backEnd;

public enum State {
	
	ENTERED(1),
	IN_TREATMENT(2),
	SOLVED(3),
	CLOSED(4);
	
	int aux;
	
	State(int aux){
		this.aux = aux;
	}
	
	int getState() {
		return aux;
	}
}
