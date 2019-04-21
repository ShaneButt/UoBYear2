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
		ReadyQueue = updateReadyQueue(start);
		Process next;
		CPU cpu = getCPU();
		long startTime = cpu.getStartTime();
		
		while (!ReadyQueue.isEmpty())
		{
			int temp_quanta = quanta;
			for (int i = 0; i < ReadyQueue.size()-1; i++)
			{
				next = ReadyQueue.get(i);
				next.execute();
				
				if (next.RemainingBurst > quanta) // is the remaining burst > cycle length?
				{
					while (temp_quanta > 0) // run through the cycle
					{
						System.out.printf("%-5s: %-3d %6d\n", "RR", next.ProcessID, next.RemainingBurst);
						next.RemainingBurst -= 100; // take off part of cycle
						temp_quanta -= 100; // rinse the cycle track
						Thread.sleep(100); // sleep repeat
					}
					next.pause();
					System.out.println("Process"+next.ProcessID+" paused at: " + (new Date().getTime() - startTime));
					
				} else // remaining burst <= cycle length
				{
					while (next.RemainingBurst > 0) // run through until completion
					{
						System.out.printf("%-5s: %-3d %6d\n", "RR", next.ProcessID, next.RemainingBurst);
						next.RemainingBurst -= 100;
						Thread.sleep(100);
					}
					Jobs.remove(next); // remove it from job list
					ReadyQueue.remove(next); // remove it from ready queue
					next.pause(); // pause it
					next.Executed = true; // change state to executed
					next.ExecutionTime = (int) (new Date().getTime() - startTime); // apply exection time

					System.out.println("Process"+next.ProcessID+" executed at: " + (next.ExecutionTime));
				}
				temp_quanta = quanta;
				long now = new Date().getTime();
				cpu.setTime(now - startTime);
				ReadyQueue = updateReadyQueue(now - startTime); // update ready queue
			}
		}
	}
}
