package Assignment2;

import java.util.Date;

public class CPU
{
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	private Queue<Process> Jobs;
	
	private Date Clock = new Date();
	private long StartTime;
	private long Time = 0;
	
	public CPU(Queue<Process> Jobs)
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
	
	public void setupQueues()
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
		HighProcesses.setAlgorithm(new ShortestJobFirst(HighProcesses, this));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses, this));
		LowProcesses.setAlgorithm(new FirstComeFirstServed(LowProcesses, this));
		System.out.println("Algorithms set");
	}
	
	public void start() throws InterruptedException
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
		AScheduler high = HighProcesses.getAlgorithm();
		AScheduler mid = MidProcesses.getAlgorithm();
		AScheduler low = LowProcesses.getAlgorithm();
		
		while(getCompletedJobs() < Jobs.size())
		{
			high.run(new Date().getTime()-StartTime);
			mid.run(new Date().getTime()-StartTime);
			low.run(new Date().getTime()-StartTime);
			System.out.printf("\n\n\nCompleted: %d", getCompletedJobs());
		}
		System.out.println("Finished");
	}
	
	public int getCompletedJobs()
	{
		int completed = 0;
		
		for (Process p : Jobs)
		{
			if (p.Executed)
				completed++;
		}
		return completed;
	}
	
	public int getReadyJobs()
	{
		int ready = 0;
		
		for (Process p : Jobs)
		{
			if (!(p.Executed) && (Time / 1000 > p.ArrivalTime))
				ready++;
		}
		return ready;
	}
	
	public double getAverageWaitTime()
	{
		int jobs = 0;
		int totalWait = 0;
		for (Process p : Jobs)
		{
			if (p.Executed)
			{
				jobs++;
				totalWait += p.ExecutionTime - p.ArrivalTime;
			}
		}
		return (double) totalWait / jobs;
	}
	
	public double getAverageTurnaroundTime()
	{
		int jobs = 0;
		int totalTurnaround = 0;
		for (Process p : Jobs)
		{
			if (p.Executed)
			{
				jobs++;
				totalTurnaround += p.TurnaroundTime;
			}
		}
		return (double) totalTurnaround / jobs;
	}
	
	public Queue<Process> getHighProcesses()
	{
		return HighProcesses;
	}
	
	public void setHighProcesses(Queue<Process> highProcesses)
	{
		HighProcesses = highProcesses;
	}
	
	public Queue<Process> getMidProcesses()
	{
		return MidProcesses;
	}
	
	public void setMidProcesses(Queue<Process> midProcesses)
	{
		MidProcesses = midProcesses;
	}
	
	public Queue<Process> getLowProcesses()
	{
		return LowProcesses;
	}
	
	public void setLowProcesses(Queue<Process> lowProcesses)
	{
		LowProcesses = lowProcesses;
	}
	
	public Queue<Process> getJobs()
	{
		return Jobs;
	}
	
	public void setJobs(Queue<Process> jobs)
	{
		Jobs = jobs;
	}
	
	public long getTime()
	{
		setTime(new Date().getTime());
		return Time;
	}
	
	public void setTime(long time)
	{
		Time = time;
	}
	
	public long getStartTime()
	{
		return StartTime;
	}
}
