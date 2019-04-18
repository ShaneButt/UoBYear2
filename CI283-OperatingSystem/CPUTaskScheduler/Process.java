package Assignment2;

public class Process
{

	public int ProcessID;
	public int ArrivalTime;
	public int ExecutionTime;
	public int WaitingTime;
	public int BurstTime;
	public int RemainingBurst;
	public int Priority;
	public double TurnaroundTime;
	public boolean Executed = false;
	private boolean Executing = false;
		
	public Process(int Id, int Arriving, int Burst, int Priority)
	{
		ProcessID = Id;
		ArrivalTime = Arriving;
		BurstTime = Burst;
		RemainingBurst = Burst;
		this.Priority = Priority;
	}
	
	public void execute()
	{
		this.Executing = true;
	}
	
	public void pause()
	{
		this.Executing = false;
	}
	
	public double calculateTurnaroundTime()
	{
		
		return 0;
	}

	public int compareArrival(Process p1)
	{
		return (this.ArrivalTime>p1.ArrivalTime)? 1 : -1;
	}
	
	public int compareBurst(Process p1)
	{
		return (this.BurstTime>p1.BurstTime)? 1 : -1;
	}
	
	public int comparePriority(Process p1)
	{
		return (this.Priority > p1.Priority)? 1 : -1;
	}

	@Override
	public String toString()
	{
		String s = String.format("Process %-3d { Arrival_Time: %2d; Burst_Time: %2d, Priority: %2d", ProcessID, ArrivalTime, BurstTime, Priority);
		return s;
	}

	public boolean isExecuting() {
		return Executing;
	}

	public void setExecuting(boolean executing) {
		Executing = executing;
	}
}
