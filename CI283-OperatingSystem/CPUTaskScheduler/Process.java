package Assignment2;

public class Process
{

	public int ProcessID;
	public int ArrivalTime;
	public int ExecutionTime;
	public int WaitingTime;
	public int BurstTime;
	public int Priority;
	public int TurnaroundTime;
		
	public Process(int Id, int Arriving, int Burst, int Priority)
	{
		ProcessID = Id;
		ArrivalTime = Arriving;
		BurstTime = Burst;
		this.Priority = Priority;
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
		String s = String.format("%d { PID: %d; Arrival_Time: %d; Burst_Time: %d, Priority: %d", this.hashCode(), ProcessID, ArrivalTime, BurstTime, Priority);
		return s;
	}
}
