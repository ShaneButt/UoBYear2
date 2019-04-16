package Assignment2;

import java.io.*;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        /*
         * Begin by reading in the CSV File of processes
         */
        Queue<Queue<Process>> Queue = new Queue<Queue<Process>>(0);
        Queue<Process> jobs = new Queue<Process>(5);
        Queue<Process> High = new Queue<Process>(1);
        Queue<Process> Mid = new Queue<Process>(2);
        Queue<Process> Low = new Queue<Process>(3);
        Queue.add(High);
        Queue.add(Mid);
        Queue.add(Low);

        Process p;

        String csvFile = "processes.csv";
        InputStream filePath = Main.class.getResourceAsStream(csvFile);
        String lineContent;
        String[] data;
        int line = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(filePath));
        lineContent = br.readLine();
        while (lineContent != null)
        {
            if (line > 0)
            {
                data = lineContent.split(",");

                int PID = Integer.parseInt(data[0]);
                int Arrival = Integer.parseInt(data[1]);
                int Burst = Integer.parseInt(data[2]);
                int Priority = Integer.parseInt(data[3]);

                p = new Process(PID, Arrival, Burst, Priority);
                jobs.add(p);
                
                switch(Priority)
                {
	                case 1: High.add(p); break;
	                case 2: Mid.add(p); break;
	                case 3: Low.add(p); break;
	                default: Low.add(p); break;
                }                
            }
            lineContent = br.readLine();
            line++;
        }

        br.close();
        CPU cpu = new CPU(Queue, High, Mid, Low);
        cpu.start();
        /*
        TODO:
            * RoundRobin implementation
            * FirstComeFirstServed implementation
            * CPU implementation
            * Visualisation via JavaFX
            * Aging implementation
         */
    }

}
