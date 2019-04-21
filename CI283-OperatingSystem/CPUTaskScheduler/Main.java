package Assignment2;

import java.io.*;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        /*
         * Begin by reading in the CSV File of processes
         */
        Queue<Process> jobs = new Queue<Process>(0);
        Process p;

        String csvFile = "processes2.csv";
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
            }
            lineContent = br.readLine();
            line++;
        }
        
        br.close();
        CPU cpu = new CPU(jobs);
        /*
        TODO:
            * Implement the Scheduling algorithms as a base for a Multi-Level-Queue
            * Implement visualisation (Gantt Chart) using JavaFX vis a vis XYChart
            * Implement a Multi-Level-Feedback-Queue algorithm that handles the 3 queues
            	and promotion/demotion of processes
            * Should be done after that
         */
    }

}
