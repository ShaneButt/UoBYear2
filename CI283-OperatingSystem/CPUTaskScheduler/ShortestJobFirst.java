package Assignment2;

import java.util.Date;

public class ShortestJobFirst extends AScheduler
{
	
	public ShortestJobFirst(Queue<Process> jobs, CPU controller)
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
		if(ReadyQueue.isEmpty())
		{
			return;
		}
		if (ReadyQueue.size() == 1)
		{
			next = ReadyQueue.get(0);
		}
		else next = getNextProcess();
		
		next.execute();
		while (next.isExecuting() && next.RemainingBurst > 0 && canRun)
		{
			System.out.printf("%-5s: %-3d %6d\n", "SJF", next.ProcessID, next.RemainingBurst);
			next.RemainingBurst -= 100;
			Thread.sleep(100);
		}
		long finTime = new Date().getTime();
		cpu.setTime(finTime - startTime);
		if (next.RemainingBurst <= 0) // check if executed
		{
			Jobs.remove(next); // remove it from job list
			ReadyQueue.remove(next); // remove it from ready queue
			next.pause(); // pause it
			next.Executed = true; // change state to executed
			next.ExecutionTime = (int) (new Date().getTime() - startTime); // apply execution time
			System.out.println("Process-" + next.ProcessID + " executed at: " + (next.ExecutionTime));
		} else System.out.println("Process-" + next.ProcessID + " paused at: " + (finTime - startTime));
		
		ReadyQueue = updateReadyQueue(finTime - startTime);
	}
	
	public Process getNextProcess()
	{
		Process curr = ReadyQueue.get(0);
		//if(ReadyQueue.size()<=1)
		//return curr;
		if (ReadyQueue.size() > 1)
		{
			for (int i = 0; i < ReadyQueue.size() - 1; i++)
			{
				Process next = ReadyQueue.get(i);
				if (curr != null)
				{
					int res = Integer.compare(next.BurstTime,
							curr.BurstTime);
					if (res < 0)
					{
						curr = next;
					}
					if (res == 0)
					{
						double next_rr = next.calculateResponseRatio();
						double curr_rr = curr.calculateResponseRatio();
						int f_res = Double.compare(next_rr, curr_rr);
						if (f_res < 0)
						{
							curr = next;
						}
					}
				} else
				{
					curr = next;
				}
			}
		}
		setCurrent(curr);
		return curr;
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
	
	}
}
