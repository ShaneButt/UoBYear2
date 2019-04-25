package Assignment2;

import java.util.Date;

public class RoundRobin extends AScheduler
{
	int quanta = 1000; // 1 second
	
	public RoundRobin(Queue<Process> jobs, CPU controller)
	{
		super(jobs, controller);
	}
	
	@Override
	public void run(long start) throws InterruptedException
	{
		canRun = true; // indicates if the algorithm has been interrupted when set to false
		ReadyQueue = updateReadyQueue(start); // updates the ready queue
		Process next; // empty variable for next process
		CPU cpu = getCPU(); // holds the cpu
		long startTime = cpu.getStartTime(); // holds the start time of the cpu
		
		int temp_quanta = quanta; // temp_quanta holds value of quanta and allows math to be made on it such that is can always
		// reference back to it's original value
		
		if(ReadyQueue.isEmpty())
		{ // if the queue is empty do nothing
			return;
		}
		
		for (int i = 0; i < ReadyQueue.size() - 1; i++)
		{
			next = ReadyQueue.get(i); // next is set to the i'th element
			
			next.execute(); // next is now executing
			
			if (next.RemainingBurst > quanta) // is the remaining burst > cycle length?
			{
				while (temp_quanta > 0 && canRun) // run through the cycle while able to (canRun assigned by MLFQ)
				{
					next.RemainingBurst -= 100; // take off part of cycle
					temp_quanta -= 100; // rinse the cycle track
					cpu.setTime( (start+=100 - cpu.getStartTime()) );
					Thread.sleep(100); // sleep repeat
				}
				next.pause();
			} else // remaining burst <= cycle length
			{
				while (next.RemainingBurst > 0 && canRun) // run through until completion
				{
					next.RemainingBurst -= 100;
					cpu.setTime( (start+=100 - cpu.getStartTime()) );
					Thread.sleep(100);
				}
			}
			
			if (next.RemainingBurst <= 0) // check if executed
			{
				next.pause();  // pause it
				Jobs.remove(next); // remove it from job list
				ReadyQueue.remove(next); // remove it from ready queue
				next.Executed = true; // change state to executed
				next.ExecutionTime = (int) (new Date().getTime() - startTime); // apply execution time
			}
			temp_quanta = quanta; // reset temp_quanta
		}
		long now = new Date().getTime();
		cpu.setTime(now - startTime);
		ReadyQueue = updateReadyQueue(now - startTime); // update ready queue
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
		// stub has no use here
	}
}
