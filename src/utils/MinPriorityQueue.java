package utils;
import java.util.*;

public class MinPriorityQueue<T extends Comparable<T>> {
    
	private ArrayList<T> queue;
   	private int size; //Size of the queue.

    	//Constructs empty ArrayList with initial capacity of 1000
    	public MinPriorityQueue() {
    		this.queue = new ArrayList<T>(1000);
    		this.size = 0; 
    	}

	//Returns the number of elements in the queue.
	public int size() {
		return this.size;
	}
    
	//Adds and appropriately places a new element into the queue.
	//Works in O(logN).
	public void add(T elem) {
	//If first element to be added...
		if(isEmpty()) {
			queue.add(0,null); //Fill 0 index with null.
			queue.add(1, elem); //Place first element at index 1.
			this.size++;
			return;
		}
		
		//If not the first element...
		//Check sufficient capacity in queue, and add elem.
		queue.ensureCapacity(this.size + 1);
		queue.add(this.size + 1, elem);
		
		int iteration = 1; //Keeps track of how far up the heap we are

		//Keeps track of the index of the parent in the heap.
		int parentIndex =  (this.size+1)/(int)Math.pow(2, iteration);

		//Index of newly added elem.
		int childIndex = (this.size+1)/(int)Math.pow(2, iteration-1);
	
        	//while the new element is 'less than' it's parent, and
        	//we haven't reached the top...
		while((Math.pow(2,iteration) <= this.size + 1) &&
		      (qCompare(childIndex,parentIndex) < 0)){
			
			//swap the two elements...
			swap(parentIndex, childIndex);	
			//and 'move up' the tree.
			iteration++;
			//Update child and parent indecies.
			childIndex = (this.size+1)/(int)Math.pow(2,iteration-1);
			parentIndex = (this.size+1)/(int)Math.pow(2,iteration);
		}
		//Register increase in queue size.
		this.size++;
	}
 
	//Removes, and returns, the element at the front of the queue.
	//Works in O(logN).
	public T remove() {
		//If nothing to remove.
		if(isEmpty())
			return null;    	

		boolean completed = false; //Tracks whether process completed.

		//left and right child are the left- and right-hand child
		//of a node in the heap. min is used to store the smallest of
		//two children. 
		int leftChild, rightChild, min;

		//current position in the heap.
		int current = 1;

	    	// store the top node, to be returned.
		T topNode = queue.get(1);
		
	    	// move end node to the top of the heap.
		queue.set(1, queue.get(this.size));
		queue.remove(this.size);
		
		while (!completed) {
			//If has no child...
			if(2*current >= size){
			completed = true;
			//If has only one child...
			} else if(2*current + 1 == size){
				int child = 2*current; //child's index.
				// if parent is smaller than the child, swap,
				// and set new current index to child's index.
				if(qCompare(current, child) > 0) {
				    swap(current, child);
				    current = child;
				}
				completed = true;
			// Else, has two children. 
		    	} else {
		    		//Extract children.
				leftChild = 2*current;
				rightChild = 2*current + 1;
				//Determine which is smaller.
				if (qCompare(leftChild,rightChild) <= 0)
					min = leftChild;
				else 
					min = rightChild;
				// swap with the smaller child if greater than 
				// child, else we are finished.
				if (qCompare(current, min) > 0) {
					swap(current, min);
					current = min;
				} else {
				    completed = true;
				}
		    }
		}
		//Re-size queue size.
		this.size--;
	    //return top node.
	    return topNode;
	}

	//Returns true if the queue is empty, false otherwise.
	public boolean isEmpty() {
    		return (this.size == 0);
	}
    
	//Function swaps the object at pos1 in queue 
	//with the object at pos2 in queue
	private void swap(int pos1, int pos2){
    		T temp = queue.get(pos1);
    		queue.set(pos1,queue.get(pos2));
    		queue.set(pos2, temp);
	}
 	
	//Returns -1 if node1<node2, 0 if node1==node2, or 1 if node1>node2
	private int qCompare(int node1, int node2) {
		return (queue.get(node1)).compareTo(queue.get(node2));
	}
 }
