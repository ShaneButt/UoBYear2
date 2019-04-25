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
					HighProcesses.add(p); // adds it to the high queue if most important
					break;
				case 2:
					MidProcesses.add(p); // adds it to mid queue if mildly important
					break;
				case 3:
					LowProcesses.add(p); // adds it to low queue if not very important
					break;
				default:
					LowProcesses.add(p); // default is not important
					break;
			}
		}
		System.out.println("Added processes to Queues");
		
		// sets the algorithms of the queues with reference to themselves
		Jobs.setAlgorithm(new MultiLevelFeedbackQueue(Jobs, this));
		HighProcesses.setAlgorithm(new FirstComeFirstServed(HighProcesses, this));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses, this));
		LowProcesses.setAlgorithm(new ShortestRemainingTimeFirst(LowProcesses, this));
		System.out.println("Algorithms set");
	}
	
	void run() throws InterruptedException
	{
		System.out.println("Starting...");
		Clock = new Date(); // start time object - doesnt change
		StartTime = Clock.getTime(); // set start time
		Time = 0; // current time of the cpu
		
		// get the algorithms
		AScheduler jobs = Jobs.getAlgorithm();
		AScheduler high = HighProcesses.getAlgorithm();
		AScheduler mid = MidProcesses.getAlgorithm();
		AScheduler low = LowProcesses.getAlgorithm();
		
		jobs.setupQueues(high, mid, low); // sets up the queues within the MLFQ object
		while(getCompletedJobs() < Jobs.size())
		{
			jobs.run(new Date().getTime()-StartTime); // calls MLFQ.run() repeatedly until Completed=Jobs.Size()
		}
		printCPU(); // Outputs the final CPU 
		System.out.printf("Finished with an average wait time of {%.3f seconds} and an average turnaround time of {%.3f seconds}.\n", getAverageWaitTime()/1000, getAverageTurnaroundTime()/1000);
	}
	
	// gets the number of completed jobs
	int getCompletedJobs()
	{
		int completed = 0;
		
		for (Process p : Jobs) // runs through the jobs queue
		{
			if (p.Executed) // if its executed
				completed++; // increment counter
		}
		return completed; // return counter
	}
	
	// calculates the average wait time of the cpu
	private double getAverageWaitTime()
	{
		int jobs = 0; // number of jobs executed
		int totalWait = 0; // wait time
		
		for (Process p : Jobs) // runs through jobs queue
		{
			if (p.Executed) // is the process executed
			{
				jobs++; // increment jobs
				totalWait += p.calculateWaitTime(Time); // add process wait calc onto the total wait time
			}
		}
		return (double) totalWait / jobs; // calculates the average wait time
	}
	
	// calculates the average turnaround time of the cpu
	private double getAverageTurnaroundTime()
	{
		int jobs = 0; // executed jobs counter
		int totalTurnaround = 0; // turnaround time holder
		
		for (Process p : Jobs)
		{
			if (p.Executed)
			{
				jobs++;
				totalTurnaround += p.calculateTurnaroundTime(); // adds process turnaround calc onto total turnaround time
			}
		}
		return (double) totalTurnaround / jobs; // calculates the turnaround time average
	}
	
	// gets the current time of the cpu
	long getTime()
	{
		return Time;
	}
	
	// sets the cpu time (updating)
	void setTime(long time)
	{
		Time = time;
	}
	
	// gets the cpu start time
	long getStartTime()
	{
		return StartTime;
	}
	
	// prints the cpu out in a neat table format
	public void printCPU()
	{
		System.out.println(this);		
	}
	
	// overrides standard toString() method to format the cpu object toString method properly into a table
	@Override
	public String toString()
	{
		String cpu =  "";
		
		// first part of the print block, this doesn't change
		final String headers = " ________________________________________________________________________________________________________\n"
						   	+ "|                                                                                                        |\n"
						   	+ "|                                         CENTRAL PROCESSING UNIT                                        |\n"
						   	+ "|________________________________________________________________________________________________________|\n"
						   	+ "|            |              |            |                 |                |                 |          |\n"
						   	+ "| Process ID | Arrival Time | Burst Time | Remaining Burst | Execution Time | Turnaround Time | Executed |\n"
						   	+ "|____________|______________|____________|_________________|________________|_________________|__________|\n";
		final String top = 	  "|            |              |            |                 |                |                 |          |\n"; 

		final String bottom = "|____________|______________|____________|_________________|________________|_________________|__________|\n";
		
		String line; // the process line string
		
		cpu += headers; // adds the headers to the cpu string
		for(Process p : Jobs)
		{
			// gets the relevant information of the process (variables names abbreviated for ease of use)
			int PID = p.ProcessID;
			int AT = p.ArrivalTime;
			int BT = p.BurstTime;
			int RBT = p.RemainingBurst;
			int ET = p.ExecutionTime;
			double TAT = p.calculateTurnaroundTime();
			boolean EXCTD = p.Executed;
			
			// formats the process data into the correct format, centred in the table cells
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
	
	public static String centreString (int width, String s)
	{
	    return String.format(
	    		"%-" + width  + "s", // format the left padding with width
	    		String.format(
	    				"%" + (s.length() + (width - s.length()) / 2) + "s", // format the right padding with whats left to add
	    				s // string to format
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
