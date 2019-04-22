package Assignment2;

import java.util.Date;

class CPU
{
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	private Queue<Process> Jobs;
	
	private Date Clock = new Date();
	private long StartTime;
	private long Time;
	
	CPU(Queue<Process> Jobs)
	{
		this.Jobs = Jobs;
		setupQueues();
		StartTime = Clock.getTime();
		Time = new Date().getTime();
		try
		{
			start();
		} catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
	
	private void setupQueues()
	{
		HighProcesses = new Queue<>(1);
		MidProcesses = new Queue<>(2);
		LowProcesses = new Queue<>(3);
		System.out.println("Queues made");
		for (Process p : Jobs)
		{
			//System.out.println(p);
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
		System.out.println("Added process to Queues");
		System.out.printf("High: %3d, Mid: %3d, Low: %3d\n", HighProcesses.size(), MidProcesses.size(), LowProcesses.size());
		Jobs.setAlgorithm(new MultiLevelFeedbackQueue(Jobs, this));
		HighProcesses.setAlgorithm(new ShortestJobFirst(HighProcesses, this));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses, this));
		LowProcesses.setAlgorithm(new FirstComeFirstServed(LowProcesses, this));
		System.out.println("Algorithms set");
	}
	
	private void start() throws InterruptedException
	{
		/*
		 * Runs FCFS
		 * Runs RR
		 * Runs SJF
		 * T
		 * tick(Time)
		 */
		
		System.out.println("Starting...");
		Clock = new Date();
		StartTime = Clock.getTime();
		Time = StartTime;
		AScheduler jobs = Jobs.getAlgorithm();
		AScheduler high = HighProcesses.getAlgorithm();
		AScheduler mid = MidProcesses.getAlgorithm();
		AScheduler low = LowProcesses.getAlgorithm();
		
		jobs.setupQueues(high, mid, low);
		
		while(getCompletedJobs() < Jobs.size())
		{
			jobs.run(new Date().getTime()-StartTime);
			//high.run(new Date().getTime()-StartTime);
			//mid.run(new Date().getTime()-StartTime);
			//low.run(new Date().getTime()-StartTime);
		}
		
		System.out.printf("Finished with an average wait time of {%.3f seconds} and an average turnaround time of {%.3f seconds}.", getAverageWaitTime()/1000, getAverageTurnaroundTime()/1000);
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
				totalWait += p.calculateWaitTime();
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
		setTime(new Date().getTime());
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
}
