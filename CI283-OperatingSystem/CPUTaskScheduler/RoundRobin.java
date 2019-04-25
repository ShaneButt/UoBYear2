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
		canRun = true;
		ReadyQueue = updateReadyQueue(start);
		Process next;
		CPU cpu = getCPU();
		long startTime = cpu.getStartTime();
		
		int temp_quanta = quanta;
		if(ReadyQueue.isEmpty())
		{
			return;
		}
		for (int i = 0; i < ReadyQueue.size() - 1; i++)
		{
			next = ReadyQueue.get(i);
			next.execute();
			
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
				Jobs.remove(next); // remove it from job list
				ReadyQueue.remove(next); // remove it from ready queue
				next.pause(); // pause it
				next.Executed = true; // change state to executed
				next.ExecutionTime = (int) (new Date().getTime() - startTime); // apply execution time
			}
			temp_quanta = quanta;
		}
		long now = new Date().getTime();
		cpu.setTime(now - startTime);
		ReadyQueue = updateReadyQueue(now - startTime); // update ready queue
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
	
	}
}
