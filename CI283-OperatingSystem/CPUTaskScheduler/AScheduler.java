package Assignment2;

public abstract class AScheduler
{
	CPU Controller;
	Queue<Process> Jobs = new Queue<>(); // Holds all unavailable/not-arrived processes
	Queue<Process> ReadyQueue = new Queue<>(); // Holds all available/arrived processes
	boolean canRun = false; // scheduler is interrupted if true
	private Process current;
	
	public AScheduler(Queue<Process> jobs, CPU controller)
	{
		Controller = controller;
		setJobs(jobs);
	}
	
	public abstract void run(long initialTime) throws InterruptedException; // initalTime = start time of the algorithm?
	// updates each tick
	// does the queue need to be updated
	// 	IE 	is any task too old? 
	//		has a task with same priority got a higher response ratio?
	//
	// do jobs need to be enqueued into the ready queue?
	
	public abstract void setupQueues(AScheduler high, AScheduler mid, AScheduler low);
	
	public Queue<Process> getReadyQueue()
	{
		return ReadyQueue;
	}
	
	public void stop()
	{
		canRun = false;
	}
	
	public Queue<Process> updateReadyQueue(long millis)
	{
		for (Process p : Jobs)
		{
			if (p.ArrivalTime <= millis / 1000 && !p.Executed)
			{
				ReadyQueue.add(p);
			}
			if (p.Executed)
			{
				Jobs.remove(p);
			}
		}
		for (Process p : ReadyQueue)
		{
			if (p.Executed)
			{
				ReadyQueue.remove(p);
			}
		}
		return ReadyQueue;
	}
	
	public Queue<Process> getJobs()
	{
		return Jobs;
	}
	
	public void setJobs(Queue<Process> jobs)
	{
		Jobs = jobs;
	}
	
	public Process getCurrent()
	{
		return current;
	}
	
	public void setCurrent(Process current)
	{
		this.current = current;
	}
	
	public CPU getCPU() { return this.Controller; }
}
