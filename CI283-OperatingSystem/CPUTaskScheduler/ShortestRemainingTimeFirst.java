package Assignment2;

import java.util.Date;

public class ShortestRemainingTimeFirst extends AScheduler
{
	
	public ShortestRemainingTimeFirst(Queue<Process> jobs, CPU controller)
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
		
		if(ReadyQueue.isEmpty())
		{ // if the queue is empty do nothing
			return;
		}
		
		if (ReadyQueue.size() == 1)
			//if the queue has only 1 element
			next = ReadyQueue.get(0);
		else
			// otherwise get the next process via method
			next = getNextProcess();
		
		next.execute(); // sets executing to true
		
		while (next.isExecuting() && next.RemainingBurst > 0 && canRun) 
		{	// while next is not interrupted, next has burst time, and the algorithm can run
			next.RemainingBurst -= 100; // decrement next rem. burst time by 100ms
			cpu.setTime( ( (start+=100) - cpu.getStartTime()) ); // update the cpu time
			Thread.sleep(100); // delay 100ms
		}
		
		long finTime = new Date().getTime(); // fin time of the loop, regardless if it executed or got interrupted
		cpu.setTime(finTime - startTime); // updates the cpu time to fin time in 1000s of ms
		
		if (next.RemainingBurst <= 0) // check if executed
		{
			next.pause();  // pause it
			Jobs.remove(next); // remove it from job list
			ReadyQueue.remove(next); // remove it from ready queue
			next.Executed = true; // change state to executed
			next.ExecutionTime = (int) (finTime - startTime); // apply execution time
		}
		ReadyQueue = updateReadyQueue(finTime - startTime); // updates the ready queue
	}
	
	public Process getNextProcess()
	{
		Process curr = ReadyQueue.get(0); // set current to first element
		if (ReadyQueue.size() > 0) //if queue has 1 or more elements
		{
			for (int i = 0; i < ReadyQueue.size() - 1; i++) // run through the queue
			{
				Process next = ReadyQueue.get(i); // next is the i'th element
				if (curr != null) // if curr is not null
				{
					int res = Integer.compare(next.RemainingBurst,
							curr.RemainingBurst); // integer comparison return 1, -1, or 0
					if (res < 0) // if -1
					{
						curr = next; // current is set to next
					}
					if (res == 0) // if 0
					{ // tie-break scenario
						// tie-break on response ratios
						double next_rr = next.calculateResponseRatio(Controller.getTime());
						double curr_rr = curr.calculateResponseRatio(Controller.getTime());
						int f_res = Double.compare(next_rr, curr_rr);
						if (f_res < 0) // if -1
						{
							curr = next; // current is set to next
						}
					}
				} else
				{
					curr = next;
				}
			}
		}
		setCurrent(curr); // set current field to current variable
		return curr; // return current
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
		// stub has no use here
	}
}
