package Assignment2;

public abstract class AScheduler
{
	
	private static Queue<Process> Jobs;
	private static Queue<Process> Queue;
	
	public AScheduler(Queue jobs)
	{
		Jobs = jobs;
	}
	
	public abstract void run();
	// updates each tick
	// does the queue need to be updated
	// do jobs need to be enqueued
}
