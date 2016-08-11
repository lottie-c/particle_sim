package simulation;

public abstract class AbstractEvent implements Event {

	private final double time;

    /**
     * Constructor for AbstractEvent.
     */
    public AbstractEvent(double t) {
    	this.time = t;
    }

    /**
     * Returns the time (in ticks) at which this event will occur.
     */
    @Override
    public double time() {
        return this.time;
    }

    /**
     * Compares this object with the specified Event.
     */
    @Override
    public int compareTo(Event that) {
		if(this.time < that.time()){
	        	return -1;
		}else if(this.time == that.time()){
			return 0;
		}else{
			return 1;
		}
    }

}
