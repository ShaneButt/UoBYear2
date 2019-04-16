package Assignment2;

public abstract class AScheduler
{
	
	private static Queue<Process> Jobs;
	private static Queue<Process> ReadyQueue;
	
	public AScheduler(Queue<Process> jobs)
	{
		setJobs(jobs);
	}
	
	public abstract void addReady(Process job);
	
	public abstract void run();
	// updates each tick
	// does the queue need to be updated
	// 	IE 	is any task too old? 
	//		has a task with same priority got a shorter time to finish?
	//		
	// do jobs need to be enqueued to ready

	public void tick(long oldTime)
	{
		for(Process p : ReadyQueue)
		{
			if(!p.isExecuting())
				p.WaitingTime+=100;
			else
				p.RemainingBurst-=100;
		}
		updateReadyQueue(oldTime);
	}
	
	public static Queue<Process> getReadyQueue() {
		return ReadyQueue;
	}

	
	public static void updateReadyQueue(long millis) {
		for(Process p : Jobs)
		{
			if(p.ArrivalTime < millis)
			{
				ReadyQueue.add(p);
			}
		}
	}

	public static Queue<?> getJobs() {
		return Jobs;
	}

	public static void setJobs(Queue<Process> jobs) {
		Jobs = jobs;
	}
}
