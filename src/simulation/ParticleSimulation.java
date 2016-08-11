package simulation;

import java.lang.reflect.InvocationTargetException;
import javax.swing.SwingUtilities;

import utils.List;
import utils.MinPriorityQueue;

public class ParticleSimulation implements Runnable, ParticleEventHandler{

    private static final long FRAME_INTERVAL_MILLIS = 40;
    
    private final ParticlesModel model;
    private final ParticlesView screen;
    private double time=0;
    private MinPriorityQueue<Event> queue;
    
    /**
     * Constructor.
     */
    public ParticleSimulation(String name, ParticlesModel m) {
    	this.model=m;
    	this.screen = new ParticlesView(name, m);
    	this.queue = new MinPriorityQueue<Event>();
    	Tick tick = new Tick(1);
    	queue.add(tick);
    	/*predict collisions for all particles in m and add 
    	 * to queue*/
    	Iterable<Collision> cs = model.predictAllCollisions(time);
    	for(Collision elem : cs){
    		queue.add(elem);
    	}
   
    }

    /**
     * Runs the simulation.
     */
    @Override
    public void run() {
        try {
            SwingUtilities.invokeAndWait(screen);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
       /*While there are events in the queue 
        * execute them*/
        while(queue.size()>0){
	        Event event = queue.remove();
	        if(event.isValid()){
	        	double newTime = event.time();
	        	double dt = newTime - time;
	        	time += dt;
	        	/*move particles for the time elapsed 
	        	 * between collisions*/
	        	model.moveParticles(dt);
	        	event.happen(this);
        	}
        }
    }

    /*Event tells handler that it has occurred, then the
     * reactTo() function performs other actions 
     * necessary as a result of the event */
	@Override
	public void reactTo(Tick tick) {
		try {
			Thread.sleep(FRAME_INTERVAL_MILLIS);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		screen.update();
		/*add new tick  to 
		 * drive simulation*/
		queue.add(new Tick(tick.time()+1));	
	}

	/*Function updates any objects that are part of
	 * the collision and passes on the message that
	 * the collision has occurred.
	 * For each particle in the collision
	 * predict new collisions and add them
	 * to the queue*/
	@Override
	public void reactTo(Collision c) {
		Particle[] particles = c.getParticles();
		Iterable<Collision> cs;
		for (int i = 0; i < particles.length; i++){
			cs =model.predictCollisions(particles[i], time);
			for(Collision elem : cs){
	    		queue.add(elem);
			}
	    }
	}
		

}
