package Assignment2;

public class MultiLevelFeedbackQueue extends AScheduler {
	
	private Queue<Process> highReadyQueue = new Queue<>(1);
	private Queue<Process> midReadyQueue = new Queue<>(2);
	private Queue<Process> lowReadyQueue = new Queue<>(3);
	
	private AScheduler High;
	private AScheduler Mid;
	private AScheduler Low;
	
	public MultiLevelFeedbackQueue(Queue<Process> jobs, CPU controller)
	{
		super(jobs, controller);
		updateReadyQueue(0);
	}
	
	@Override
	public void run(long start) throws InterruptedException
	{
		updateReadyQueues(start);
		/*
		* updateReadyQueue() runs through all jobs checking their priority
		*   updating their waitTime and responseRatio
		* Pause processes in lower priority queue when a higher priority process comes in
		* Promote/Demote processes that wait on/hog the CPU
		 */
		
		if(highReadyQueue.isEmpty() && midReadyQueue.isEmpty())
		{
			highReadyQueue.getAlgorithm().stop();
			midReadyQueue.getAlgorithm().stop();
			lowReadyQueue.getAlgorithm().run(start);
		}
		else if(highReadyQueue.isEmpty())
		{
			highReadyQueue.getAlgorithm().stop();
			lowReadyQueue.getAlgorithm().stop();
			midReadyQueue.getAlgorithm().run(start);
		}
		else
		{
			highReadyQueue.getAlgorithm().run(start);
		}
	}
	
	@Override
	public void setupQueues(AScheduler high, AScheduler mid, AScheduler low)
	{
		highReadyQueue.setAlgorithm(high);
		midReadyQueue.setAlgorithm(mid);
		lowReadyQueue.setAlgorithm(low);
		
		High = high;
		Mid = mid;
		Low = low;
	}
	
	public void updateReadyQueues(long millis)
	{
		for(Process p : Jobs)
		{
			p.calculateResponseRatio();
			if((double) (p.ArrivalTime*1000) <= (double) millis && !p.Executed)
			{
				switch (p.Priority)
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
						lowReadyQueue.add(p);
						break;
				}
			}
			if(p.Executed)
			{
				switch (p.Priority)
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
