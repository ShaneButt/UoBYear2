package Assignment2;

public abstract class AScheduler
{
	private CPU Controller;
	protected Queue<Process> Jobs = new Queue<>(); // Holds all unavailable/not-arrived processes
	protected Queue<Process> ReadyQueue = new Queue<>(); // Holds all available/arrived processes
	
	private Process current;
	
	private final int msDelay = 5;
	
	public AScheduler(Queue<Process> jobs, CPU controller)
	{
		Controller = controller;
		setJobs(jobs);
		updateReadyQueue(0);
	}
	
	public abstract void run(long initialTime) throws InterruptedException; // initalTime = start time of the algorithm?
	// updates each tick
	// does the queue need to be updated
	// 	IE 	is any task too old? 
	//		has a task with same priority got a higher response ratio?
	//
	// do jobs need to be enqueued into the ready queue?
	
	public Queue<Process> getReadyQueue()
	{
		return ReadyQueue;
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
	
	public int getMsDelay() { return this.msDelay; }
	
	public CPU getCPU() { return this.Controller; }
}
