package simulation;

public abstract class Collision extends AbstractEvent{
    
	private Particle[] particles;
	private int[] initialHits;
	
    /**
     * Constructor for Collision
     */
    public Collision(double t, Particle[] ps) {
        // TODO implement constructor
    	super(t);
    	this.particles = ps;
    	int length = this.particles.length;
    	this.initialHits = new int[length];
    	for (int i = 0; i < length; i++ ){
    		this.initialHits[i] = this.particles[i].collisions();
    	}
    }

    /**
     * Returns true if this Collision is (still) valid.
     */
    @Override
    public boolean isValid() {
    	int length = this.particles.length;
    	/*compare current particle hits initial hits,
    	 collision is no longer valid if hits has increased */
        for(int i = 0; i < length; i++){
        	if(this.particles[i].collisions() > this.initialHits[i]){
        		return false;
        	}
        }
        return true;
    }

    /**
     * Returns an array containing the Particles involved in this Collision.
     */
    public Particle[] getParticles() {
        // TODO implement this method
        return this.particles;
    }
}
