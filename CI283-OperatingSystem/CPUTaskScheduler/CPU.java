package Assignment2;

import java.util.Calendar;

public class CPU
{	
	private Queue<Queue<Process>> Queues; // Holds 3 Queues of High/Mid/Low tasks
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	private Queue<Process> Jobs;
	
	private long Time;
	
	public CPU(Queue<Queue<Process>> queue, Queue<Process> high, Queue<Process> mid, Queue<Process> low)
	{
		Queues = queue;
		HighProcesses = high;
		MidProcesses = mid;
		LowProcesses = low;
		
		init();
	}
	
	private void init()
	{
		Time = 0;
		
		HighProcesses.setAlgorithm(new ShortestJobFirst(HighProcesses));
		MidProcesses.setAlgorithm(new RoundRobin(MidProcesses));
		LowProcesses.setAlgorithm(new FirstComeFirstServed(LowProcesses));
		
		for(Queue<Process> q : Queues)
		{
			for(Process p : q)
				Jobs.add(p);
		}
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
