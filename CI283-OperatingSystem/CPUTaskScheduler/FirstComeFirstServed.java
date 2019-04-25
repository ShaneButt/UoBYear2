package Assignment2;

import java.util.Date;

public class FirstComeFirstServed extends AScheduler
{
	
	public FirstComeFirstServed(Queue<Process> jobs, CPU controller)
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
			next = ReadyQueue.get(0);
		else
			next = getNextProcess();
		
		next.execute();
		while (next.isExecuting() && next.RemainingBurst > 0 && canRun)
		{
			next.RemainingBurst -= 100;
			cpu.setTime( ( (start+=100) - cpu.getStartTime()) );
			Thread.sleep(100);
		}
		
		long finTime = new Date().getTime();
		cpu.setTime(finTime - startTime);
		if (next.RemainingBurst <= 0) // check if executed
		{
			next.pause();  // pause it
			Jobs.remove(next); // remove it from job list
			ReadyQueue.remove(next); // remove it from ready queue
			next.Executed = true; // change state to executed
			next.ExecutionTime = (int) (finTime - startTime); // apply execution time
		}
		ReadyQueue = updateReadyQueue(finTime - startTime);
	}
	
	public Process getNextProcess()
	{
		Process curr = ReadyQueue.get(0);
		if (ReadyQueue.size() > 0)
		{
			for (int i = 0; i < ReadyQueue.size() - 1; i++)
			{
				Process next = ReadyQueue.get(i);
				if (curr != null)
				{
					int res = Integer.compare(next.ArrivalTime,
							curr.ArrivalTime);
					if (res < 0)
					{
						curr = next;
					}
					if (res == 0)
					{
						double next_rr = next.calculateResponseRatio(Controller.getTime());
						double curr_rr = curr.calculateResponseRatio(Controller.getTime());
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
