package Assignment2;

public class CPU
{	
	private Queue<Queue<Process>> Queues; // Holds 3 Queues of High/Mid/Low tasks
	private Queue<Process> HighProcesses;
	private Queue<Process> MidProcesses;
	private Queue<Process> LowProcesses;
	
	public CPU(Queue<Queue<Process>> queue, Queue<Process> high, Queue<Process> mid, Queue<Process> low)
	{
		Queues = queue;
		HighProcesses = high;
		MidProcesses = mid;
		LowProcesses = low;
		
		queue.setAlgorithm(new FirstComeFirstServed(queue));
		high.setAlgorithm(new FirstComeFirstServed(high));
		mid.setAlgorithm(new RoundRobin(mid));
		low.setAlgorithm(new RoundRobin(low));
	}
}
