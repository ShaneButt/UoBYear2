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
		ReadyQueue = updateReadyQueue(start);
		Process next;
		CPU cpu = getCPU();
		long startTime = cpu.getStartTime();
		while (!ReadyQueue.isEmpty())
		{
			System.out.printf("%d : %s", ReadyQueue.size(), ReadyQueue);
			if (ReadyQueue.size() == 0)
			{
				do
				{
					Thread.sleep(100);
					updateReadyQueue(cpu.getTime()-startTime);
				} while (ReadyQueue.size() == 0 && Jobs.size() > 0);
			}
			if (ReadyQueue.size() == 1)
				next = ReadyQueue.get(0);
			else next = getNextProcess();
			
			next.execute();
			while (next.isExecuting() && next.RemainingBurst > 0)
			{
				System.out.printf("%-5s: %-3d %6d\n", "FCFS", next.ProcessID, next.RemainingBurst);
				next.RemainingBurst -= 100;
				Thread.sleep(100);
			}
			long finTime = new Date().getTime();
			cpu.setTime(finTime - startTime);
			System.out.println("Process paused or executed at: " + (finTime-startTime));
			if (next.isExecuting())
			{
				Jobs.remove(next); // remove it from job list
				ReadyQueue.remove(next); // remove it from ready queue
				next.pause(); // pause it
				next.Executed = true; // change state to executed
				next.ExecutionTime = (int) (finTime - startTime);
			}
			ReadyQueue = updateReadyQueue(finTime - startTime);
		}
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
	
}
