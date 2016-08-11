package simulation;

public class TwoParticleCollision extends Collision{

		private Particle particle1;
		private Particle particle2;
		
	/*constructor*/
	public TwoParticleCollision(Particle p1, Particle p2, double t) {
		super(t, new Particle[]{p1,p2});
		this.particle1 = p1;
		this.particle2 = p2;
	}

	@Override
	/*Function updates any objects that are part of
	 * the collision and passes on the message that
	 * the collision has occurred. */
	public void happen(ParticleEventHandler h) {
		Particle.collide(this.particle1, this.particle2);
		h.reactTo(this);	
	}

	
}
