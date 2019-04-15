package Assignment2;

import java.io.*;
import java.net.URL;

public class Main
{

    public static void main(String[] args) throws IOException
    {
        /*
         * Begin by reading in the CSV File of processes
         */
        Queue<Queue<Process>> Queue = new Queue<Queue<Process>>(0);
        Queue<Process> High = new Queue<Process>(1);
        Queue<Process> Mid = new Queue<Process>(2);
        Queue<Process> Low = new Queue<Process>(3);
        Queue.add(High);
        Queue.add(Mid);
        Queue.add(Low);

        CPU cpu = new CPU(Queue, High, Mid, Low);

        Process p;

        String csvFile = "processes.csv";
        InputStream filePath = Main.class.getResourceAsStream(csvFile);
        String lineContent;
        String[] cellContent;
        int line = 0;

        BufferedReader br = new BufferedReader(new InputStreamReader(filePath));
        lineContent = br.readLine();
        while (lineContent != null)
        {
            if (line > 0)
            {
                System.out.println(lineContent);
                cellContent = lineContent.split(",");

                int PID = Integer.parseInt(cellContent[0]);
                int Arrival = Integer.parseInt(cellContent[1]);
                int Burst = Integer.parseInt(cellContent[2]);
                int Priority = Integer.parseInt(cellContent[3]);

                p = new Process(PID, Arrival, Burst, Priority);

                if(Priority == 1)
                    High.add(p);
                else if(Priority == 2)
                    Mid.add(p);
                else Low.add(p);
            }
            lineContent = br.readLine();
            line++;
        }

        br.close();
        
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
