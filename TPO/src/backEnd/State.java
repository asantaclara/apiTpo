package backEnd;

public enum State {
	
	
	ENTERED(1),
	IN_TREATMENT(2),
	SOLVED(3),
	CLOSED(4);

	int aux;

	private State(int aux) {
		this.aux = aux;
	}
	
	public int getValue() {
		return aux;
	}
}
