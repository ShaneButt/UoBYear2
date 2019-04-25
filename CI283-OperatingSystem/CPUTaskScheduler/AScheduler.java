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
	
	/* <summary>
	 * Updates the ready queue
	 * Gets the next process
	 * Executes the next process incrementally checking if the algorithm has been paused in the way the algorithm implements
	 * Check if the process has been executed
	 * Handle the onExecution status
	 * Set the cpu time to the finish time of the process outside of the loop
	 * 
	 */
	public abstract void run(long initialTime) throws InterruptedException; // initalTime = time the algorithm was called to run
	
	public abstract void setupQueues(AScheduler high, AScheduler mid, AScheduler low);
	
	// Gets the ready queue
	public Queue<Process> getReadyQueue()
	{
		return ReadyQueue;
	}
	
	// Pauses/Stops/Interrupts the algorithm
	public void stop()
	{
		canRun = false;
	}
	
	// Standard ready queue updater based on arrival time with no tiebreak
	public Queue<Process> updateReadyQueue(long millis)
	{
		for (Process p : Jobs) // runs through the jobs queue
		{
			if (p.ArrivalTime <= millis / 1000 && !p.Executed) // checks if the process has arrived
			{
				ReadyQueue.add(p); // adds it to the queue
			}
			if (p.Executed) // checks if p is executed
			{
				Jobs.remove(p); // removes it from the jobs
			}
		}
		for (Process p : ReadyQueue) // runs through the ready queue
		{
			if (p.Executed) // checks if an arrived process has executed
			{
				ReadyQueue.remove(p); // removes it from the queue
			}
		}
		return ReadyQueue; // returns the updated ready queue for later use
	}
	
	// gets the jobs queue containing all processes
	public Queue<Process> getJobs()
	{
		return Jobs;
	}
	
	// sets the jobs queue
	public void setJobs(Queue<Process> jobs)
	{
		Jobs = jobs;
	}
	
	// gets the current process
	public Process getCurrent()
	{
		return current;
	}
	
	// sets the current process
	public void setCurrent(Process current)
	{
		this.current = current;
	}
	
	// gets the parent CPU
	public CPU getCPU() { return this.Controller; }
}
