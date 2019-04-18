package Assignment2;

import java.util.Calendar;
import java.util.Date;

public class CPU
{	
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	private Queue<Process> Jobs;
	
	private long StartTime;
	private long Time = 0;
	
	public CPU(Queue<Process> Jobs)
	{
		this.Jobs = Jobs;
		setupQueues();
		StartTime = new Date().getTime();
	}
	
	public void setupQueues()
	{
		HighProcesses = new Queue<>(1);
		MidProcesses = new Queue<>(2);
		LowProcesses = new Queue<>(3);
		
		for(Process p : Jobs)
		{
			if(p.ArrivalTime == 0)
			{
				switch(p.Priority)
				{
					case 1: HighProcesses.add(p); break;
					case 2: MidProcesses.add(p); break;
					case 3: LowProcesses.add(p); break;
					default: LowProcesses.add(p); break;
				}
			}
		}
		HighProcesses.setAlgorithm(new ShortestJobFirst(HighProcesses));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses));
		LowProcesses.setAlgorithm(new FirstComeFirstServed(LowProcesses));
	}
	
	public void start()
	{
		/*
		 * Runs FCFS
		 * Runs RR
		 * Runs SJF
		 * Tick(Time)
		 */
		Time+=100;
	}
	
	public int getCompletedJobs()
	{
		int completed = 0;
		
		for(Process p : Jobs)
		{
			if(p.Executed)
				completed++;
		}
		return completed;
	}
	
	public int getReadyJobs()
	{
		int ready = 0;
		
		for(Process p : Jobs)
		{
			if( !(p.Executed) && (Time/1000 > p.ArrivalTime) )
				ready++;
		}
		return ready;
	}
	
	public double getAverageWaitTime()
	{
		int jobs = 0;
		int totalWait = 0;
		for(Process p : Jobs)
		{
			if(p.Executed)
			{
				jobs++;
				totalWait += p.ExecutionTime-p.ArrivalTime;
			}
		}
		return (double) totalWait/jobs;
	}
	
	public double getAverageTurnaroundTime()
	{
		int jobs = 0;
		int totalTurnaround = 0;
		for(Process p : Jobs)
		{
			if(p.Executed)
			{
				jobs++;
				totalTurnaround += p.TurnaroundTime;
			}
		}
		return (double) totalTurnaround/jobs;
	}
	
	
}
