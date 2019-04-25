package Assignment2;

import java.util.Date;

class CPU
{
	// holds the corresponding processes
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	
	// holds all processes
	private Queue<Process> Jobs;
	
	private Date Clock; // the clock
	private long StartTime; // the start time of the program
	private long Time; // the current time of the program
	
	CPU(Queue<Process> Jobs)
	{
		this.Jobs = Jobs; // sets the jobs
		setupQueues(); // setup all queues
		StartTime = new Date().getTime(); // set the start time
		Time = 0; // sets the current time
	}
	
	private void setupQueues()
	{
		// instantiates the queues
		HighProcesses = new Queue<>(1);
		MidProcesses = new Queue<>(2);
		LowProcesses = new Queue<>(3);
		
		System.out.println("Queues made");
		
		// run through all jobs and add them to their respective queues
		for (Process p : Jobs)
		{
			switch (p.Priority)
			{
				case 1:
					HighProcesses.add(p);
					break;
				case 2:
					MidProcesses.add(p);
					break;
				case 3:
					LowProcesses.add(p);
					break;
				default:
					LowProcesses.add(p);
					break;
			}
		}
		System.out.println("Added processes to Queues");
		
		// sets the algorithms
		Jobs.setAlgorithm(new MultiLevelFeedbackQueue(Jobs, this));
		HighProcesses.setAlgorithm(new FirstComeFirstServed(HighProcesses, this));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses, this));
		LowProcesses.setAlgorithm(new ShortestJobFirst(LowProcesses, this));
		System.out.println("Algorithms set");
	}
	
	void run() throws InterruptedException
	{
		System.out.println("Starting...");
		Clock = new Date();
		StartTime = Clock.getTime();
		Time = 0;
		
		AScheduler jobs = Jobs.getAlgorithm();
		AScheduler high = HighProcesses.getAlgorithm();
		AScheduler mid = MidProcesses.getAlgorithm();
		AScheduler low = LowProcesses.getAlgorithm();
		
		jobs.setupQueues(high, mid, low);
		while(getCompletedJobs() < Jobs.size())
		{
			jobs.run(new Date().getTime()-StartTime);
		}
		printCPU();
		System.out.printf("Finished with an average wait time of {%.3f seconds} and an average turnaround time of {%.3f seconds}.\n", getAverageWaitTime()/1000, getAverageTurnaroundTime()/1000);
	}
	
	int getCompletedJobs()
	{
		int completed = 0;
		
		for (Process p : Jobs)
		{
			if (p.Executed)
				completed++;
		}
		return completed;
	}
	
	private double getAverageWaitTime()
	{
		int jobs = 0;
		int totalWait = 0;
		for (Process p : Jobs)
		{
			if (p.Executed)
			{
				jobs++;
				totalWait += p.calculateWaitTime(Time);
			}
		}
		return (double) totalWait / jobs;
	}
	
	private double getAverageTurnaroundTime()
	{
		int jobs = 0;
		int totalTurnaround = 0;
		for (Process p : Jobs)
		{
			if (p.Executed)
			{
				jobs++;
				totalTurnaround += p.calculateTurnaroundTime();
			}
		}
		return (double) totalTurnaround / jobs;
	}
	
	long getTime()
	{
		return Time;
	}
	
	void setTime(long time)
	{
		Time = time;
	}
	
	long getStartTime()
	{
		return StartTime;
	}
	
	public void printCPU()
	{
		System.out.println(this.toString());		
	}
	
	@Override
	public String toString()
	{
		String cpu =  " ________________________________________________________________________________________________________\n"
					+ "|                                                                                                        |\n"
					+ "|                                         CENTRAL PROCESSING UNIT                                        |\n"
					+ "|________________________________________________________________________________________________________|\n"
					+ "|            |              |            |                 |                |                 |          |\n"
					+ "| Process ID | Arrival Time | Burst Time | Remaining Burst | Execution Time | Turnaround Time | Executed |\n"
					+ "|____________|______________|____________|_________________|________________|_________________|__________|\n";
		
		final String top = 		"|            |              |            |                 |                |                 |          |\n"; 
		final String bottom = 	"|____________|______________|____________|_________________|________________|_________________|__________|\n";
		String line;
		for(Process p : Jobs)
		{
			int PID = p.ProcessID;
			int AT = p.ArrivalTime;
			int BT = p.BurstTime;
			int RBT = p.RemainingBurst;
			int ET = p.ExecutionTime;
			double TAT = p.calculateTurnaroundTime();
			boolean EXCTD = p.Executed;
			
			line = "|"+centreString(12, Integer.toString(PID))+"|"
				  +""+centreString(14, Integer.toString(AT))+"|"
				  +""+centreString(12, Integer.toString(BT))+"|"
				  +""+centreString(17, Integer.toString(RBT))+"|"
				  +""+centreString(16, Integer.toString(ET))+"|"
				  +""+centreString(17, Double.toString(TAT))+"|"
				  +""+centreString(10, Boolean.toString(EXCTD))+"|\n";
			cpu += top+line+bottom;
		}
		return cpu;
	}
	
	public static String centreString (int width, String s) {
	    return String.format(
	    		"%-" + width  + "s", 
	    		String.format(
	    				"%" + (s.length() + (width - s.length()) / 2) + "s", 
	    				s
	    			)
	    	);
	}

	/**
	 * @return the highProcesses
	 */
	public Queue<Process> getHighProcesses() {
		return HighProcesses;
	}

	/**
	 * @param highProcesses the highProcesses to set
	 */
	public void setHighProcesses(Queue<Process> highProcesses) {
		HighProcesses = highProcesses;
	}

	/**
	 * @return the midProcesses
	 */
	public Queue<Process> getMidProcesses() {
		return MidProcesses;
	}

	/**
	 * @param midProcesses the midProcesses to set
	 */
	public void setMidProcesses(Queue<Process> midProcesses) {
		MidProcesses = midProcesses;
	}

	/**
	 * @return the lowProcesses
	 */
	public Queue<Process> getLowProcesses() {
		return LowProcesses;
	}

	/**
	 * @param lowProcesses the lowProcesses to set
	 */
	public void setLowProcesses(Queue<Process> lowProcesses) {
		LowProcesses = lowProcesses;
	}

	/**
	 * @return the jobs
	 */
	public Queue<Process> getJobs() {
		return Jobs;
	}

	/**
	 * @param jobs the jobs to set
	 */
	public void setJobs(Queue<Process> jobs) {
		Jobs = jobs;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(long startTime) {
		StartTime = startTime;
	}
	
	
}
