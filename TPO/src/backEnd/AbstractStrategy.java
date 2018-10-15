package backEnd;

public abstract class AbstractStrategy {

	public abstract void treatClaim(User responsable, State newState, String description);
}
