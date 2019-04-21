package Assignment2;

public class Process
{
	
	public int ProcessID; // The unique identifier of the process
	public int ArrivalTime; // The time (in s) the process is ready
	public int ExecutionTime; // The time (in s) the process is completed
	public int WaitingTime; // The time since the process was last worked on
	public int BurstTime; // The time it will take to fully execute the process (in ms)
	public int RemainingBurst; // The time remaining to fully execute the process
	public int Priority; // The priority of the process
	public double TurnaroundTime; // The time taken to complete the process (Execution-Arrival)
	public double ResponseRatio; // The ratio of which determines the priority of a process ((Waiting+Burst)/Burst)
	public boolean Executed = false; // Has the process been fully executed
	private boolean Executing = false; // Is the process currently executing
	
	/*
	 * <summary>
	 * Simple constructor assigning values to fields
	 */
	public Process(int Id, int Arriving, int Burst, int Priority)
	{
		ProcessID = Id;
		ArrivalTime = Arriving;
		BurstTime = Burst * 1000;
		RemainingBurst = Burst * 1000;
		this.Priority = Priority;
	}
	
	/*
	 * <summary>
	 * Simple method to indicate to the CPU/Scheduler that a process is executing
	 */
	public void execute()
	{
		this.Executing = true;
	}
	
	/*
	 * <summary>
	 * Simple method to indicate to the CPU/Scheduler that a process is paused
	 */
	public void pause()
	{
		this.Executing = false;
	}
	
	/*
	 * <summary>
	 * Calculates the turnaround time
	 */
	public double calculateTurnaroundTime()
	{
		TurnaroundTime = ExecutionTime - ArrivalTime;
		return TurnaroundTime;
	}
	
	/*
	 * <summary>
	 * Calculates the waiting time
	 */
	public double calculateWaitTime()
	{
		WaitingTime = ExecutionTime-BurstTime < 0? 0 : ExecutionTime-BurstTime;
		return WaitingTime;
	}
	
	/*
	 * <summary>
	 * Calculates the response ratio for tie-break scenarios
	 * Is a function of WaitingTime and BurstTime
	 */
	public double calculateResponseRatio()
	{
		calculateWaitTime();
		ResponseRatio = ((double) WaitingTime + BurstTime) / BurstTime;
		return ResponseRatio;
	}
	
	/*
	 * <summary>
	 * Compare methods to compare several fields
	 */
	public int compareArrival(Process p1)
	{
		return (this.ArrivalTime > p1.ArrivalTime) ? 1 : (this.ArrivalTime < p1.ArrivalTime) ? -1 : 0;
	}
	
	public int compareBurst(Process p1)
	{
		
		return (this.BurstTime > p1.BurstTime) ? 1 : (this.BurstTime < p1.BurstTime) ? -1 : 0;
	}
	
	public int comparePriority(Process p1)
	{
		return (this.Priority > p1.Priority) ? 1 : (this.Priority < p1.Priority) ? -1 : 0;
	}
	
	/*
	 * <summary>
	 * Getter for Executing variable
	 */
	public boolean isExecuting()
	{
		return Executing;
	}
	
	@Override
	/*
	 * <summary>
	 * Overrides the toString() method in Object to format the output of a process properly
	 */
	public String toString()
	{
		String s = String.format("Process %-3d { Arrival_Time: %2d; Burst_Time: %2d, Priority: %2d; Executed: %s }", ProcessID, ArrivalTime, BurstTime, Priority, Executed);
		return s;
	}
}
