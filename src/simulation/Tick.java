package simulation;

public class Tick extends AbstractEvent {

	/*constructor*/
	public Tick(double t) {
		super(t);
	}

	// Always returns true (see spec).
	public boolean isValid() {return true;}

	//when an event 'happens'
	public void happen(ParticleEventHandler h) {
		h.reactTo(this);
		return;
	}
}
