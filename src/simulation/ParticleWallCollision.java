package simulation;

/*Constructor*/
public class ParticleWallCollision extends Collision {
	
	private Particle particle;
	private Wall wall;
	
	public ParticleWallCollision(Particle ps, Wall w, double t){
		super(t, new Particle[]{ps});
		this.particle = ps;
		this.wall = w;
	}

	/*Function updates any objects that are part of
	 * the collision and passes on the message that
	 * the collision has occurred. */
	@Override
	public void happen(ParticleEventHandler h) {
		// TODO Auto-generated method stub
		Particle.collide(this.particle, this.wall);
		h.reactTo(this);
		return;
		
	}

}
