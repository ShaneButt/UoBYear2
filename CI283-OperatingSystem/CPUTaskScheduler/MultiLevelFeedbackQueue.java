package Assignment2;

public class MultiLevelFeedbackQueue extends AScheduler {
	
	// The Ready-Processes Queues that the MLFQ  handles
	private Queue<Process> highReadyQueue = new Queue<>(1);
	private Queue<Process> midReadyQueue = new Queue<>(2);
	private Queue<Process> lowReadyQueue = new Queue<>(3);
	
	// The Algorithms that the MLFQ handles
	private AScheduler High;
	private AScheduler Mid;
	private AScheduler Low;
	
	// When intialised updates the ready queue with 0 time and calls AScheduler constructer to set vars.
	public MultiLevelFeedbackQueue(Queue<Process> jobs, CPU controller)
	{
		super(jobs, controller);
		updateReadyQueue(0);
	}
	
	@Override
	public void run(long start) throws InterruptedException
	{
		updateReadyQueues(start); // update the queues each run
		Controller.setTime(start); // set the cpu time
		/*
		* updateReadyQueue() runs through all jobs checking their priority
		*   updating their waitTime and responseRatio
		* Pause processes in lower priority queue when a higher priority process comes in
		* Promote/Demote processes that wait on/hog the CPU
		 */
		
		if(highReadyQueue.isEmpty() && midReadyQueue.isEmpty()) // if high and mid are empty
		{
			High.stop(); // stop high
			Mid.stop(); // stop mid
			Low.run(start); // run low
		}
		else if(highReadyQueue.isEmpty()) // if high is empty
		{
			High.stop(); // stop high
			Low.stop(); // stop low
			Mid.run(start); // run mid
		}
		else // high is not empty
		{
			Low.stop(); // stop low
			Mid.stop(); // stop mid
			High.run(start); // run high
		}
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
		// Sets up the ready queue's algorithms
		highReadyQueue.setAlgorithm(high);
		midReadyQueue.setAlgorithm(mid);
		lowReadyQueue.setAlgorithm(low);
		
		// Sets the algorithms
		High = high;
		Mid = mid;
		Low = low;
	}
	
	public void updateReadyQueues(long millis)
	{
		for(Process p : Jobs)
		{
			p.calculateResponseRatio(Controller.getTime()); // calculate the response ratio of the process
			if((double) (p.ArrivalTime*1000) <= (double) millis && !p.Executed) 
				// if the process has arrived, add it to the ready queue it belongs to
			{
				switch (p.Priority) // switches on the priorities, 1=High, 2=Mid, 3=Low, and adds to correct ready queue
				{
					case 1:
						highReadyQueue.add(p);
						break;
					case 2:
						midReadyQueue.add(p);
						break;
					case 3:
						lowReadyQueue.add(p);
						break;
					default:
						// default case for if the priority is null or not between 1 and 3.
						lowReadyQueue.add(p);
						break;
				}
			}
			if(p.Executed) 
				// if the process has been executed, remove it from the ready queue it belongs to
			{
				switch (p.Priority) // switch on the priorities to find out which queue it belongs to
				{
					case 1:
						highReadyQueue.remove(p);
						break;
					case 2:
						midReadyQueue.remove(p);
						break;
					case 3:
						lowReadyQueue.remove(p);
						break;
					default:
						lowReadyQueue.remove(p);
						break;
				}
			}
		}
	}
}
