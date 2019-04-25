package Assignment2;

import java.io.*;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import Assignment2.GanttChart.ExtraData;

public class Main extends Application
{
	
	static Queue<Process> Jobs = new Queue<>(0);
	static CPU Controller;
	static int EstimatedRuntime = 0;
	
	
	public static void main(String[] args) throws IOException, InterruptedException
	{
		/*
		 * Begin by reading in the CSV File of processes
		 */
		Process p;
		
		String csvFile = "processes2.csv"; // 3 Test files, "processes.csv", "processes2.csv", and "processes3.csv"
		InputStream filePath = Main.class.getResourceAsStream(csvFile); // gets the direct local path to the file
		String lineContent; // stores the row of the csv
		String[] data; // stores the columns/cells of the rows
		int line = 0; // line count
		
		BufferedReader br = new BufferedReader(new InputStreamReader(filePath));
		lineContent = br.readLine(); // reads in the first row / headers
		while (lineContent != null)
		{
			if (line > 0) // this only runs if the line is beyond the header line
			{
				data = lineContent.split(","); // splits the line into columns by splitting on commas
				
				// sets variables for the Processes
				int PID = Integer.parseInt(data[0]);
				int Arrival = Integer.parseInt(data[1]);
				int Burst = Integer.parseInt(data[2]);
				int Priority = Integer.parseInt(data[3]);
				
				// Increments the Estimated Runtime
				EstimatedRuntime += Burst;
				p = new Process(PID, Arrival, Burst, Priority); // Creates the process
				Jobs.add(p); // Adds it to the all job queue
			}
			lineContent = br.readLine(); // reads in the next line
			line++; // increments line count
		}
		
		br.close(); // closes the stream
		Controller = new CPU(Jobs); // creates the CPU object
		new Thread(() -> {
			try
			{
				Controller.run(); // runs the cpu until it's finished
				long finishTime = new Date().getTime(); // initalised to current time when cpu finishes processing
				long startTime = Controller.getStartTime(); // start time of the cpu
				int runTime = (int) (finishTime - startTime) / 1000; // the run time of the cpu in seconds
				System.out.printf("CPU Utilisation: %d%s\n", (EstimatedRuntime * 100) / runTime, "%"); // prints out percentage cpu utilisation
			} catch (InterruptedException e) {}
		}).start(); // starts the thread
		launch(); // launches the application
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("CPU Task Scheduler"); // sets the title of the application
		
		// creates new axis
		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();

		// instantiates a new chart with the axis
		final GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis, yAxis);
		
		xAxis.setLabel("Time (ms)"); // set the x-axis label
		xAxis.setMinorTickCount(5); // set the minor tick to 5
		xAxis.setTickLabelFill(Color.DEEPSKYBLUE); // colour the label
		
		
		yAxis.setLabel("Processes"); // set the y axis label
		yAxis.setTickLabelGap(20); // set the tick gap
		yAxis.setTickLabelFill(Color.DEEPSKYBLUE); // colour the label
		
		// set the title of the chart
		chart.setTitle( String.format("CENTRAL PROCESSING UNIT %d/%d", Controller.getCompletedJobs(), Controller.getJobs().size()));
		
		// make the legend invisible
		chart.setLegendVisible(false);
		
		//add the stylesheet
		chart.getStylesheets().add(Main.class.getResource("ganttchart.css").toExternalForm());
		
		//initialise the chart series with items if any
		updateChartSeries(chart);
		
		Scene scene = new Scene(chart, 1200, 900); // create a new scene/application
		
		chart.setBlockHeight( scene.getHeight()/Controller.getJobs().size() ); // set the block heigh as a function of the number of jobs
		
		stage.setScene(scene); // set the scene
		stage.show(); // show the application
		
		// update thread to update the chart every second
		Thread updateThread = new Thread(() -> {
			do
			{
				try
				{
					Thread.sleep(1000); // delay 1s
					Platform.runLater(() -> updateChartSeries(chart)); // update the chart
				} catch (InterruptedException e) {}
			} while(true); // only updates until Completed==Jobs.size()
		});
		updateThread.setDaemon(true); // jvm will only exit if the threads running are all daemon
		updateThread.start(); // start the thread
	}
	
	// called to update the chart and add new series
	void updateChartSeries(GanttChart<Number, String> chart)
	{
		// updates the title with how many seconds have passed and how many jobs have completed
		chart.setTitle(
				String.format(
						"CENTRAL PROCESSING UNIT %d/%d (Clock: %s seconds)",
						Controller.getCompletedJobs(),
						Controller.getJobs().size(),
						Long.toString(
								(new Date().getTime()-Controller.getStartTime()) / 1000
						)
				)
		);
		
		Queue<Process> jobs = Controller.getJobs(); // all jobs
		
		for (int i = 0; i < jobs.size(); i++) // run through all jobs
		{
			Process p = jobs.get(i); // get the i'th process

			if (p.ExecutionStarts.size() != p.ExecutionPauses.size()) // make sure Starts==Pauses otherwise it's still executing
				return;
			
			for (int j = 0; j < p.ExecutionPauses.size(); j++) // run through each start
			{
				Series<Number, String> series = new Series<>(); // initialise a new series
				
				Data<Number, String> data = new Data<>(
						p.ExecutionStarts.get(j) - Controller.getStartTime(), // x-value
						"Process"+p.ProcessID, // y-value
						new ExtraData(
								(p.ExecutionPauses.get(j) - Controller.getStartTime()) - (p.ExecutionStarts.get(j) - Controller.getStartTime()), // calculates length
								(p.Priority==1)? "algorithm-fcfs" : (p.Priority==2)? "algorithm-rr" : "algorithm-sjf" // decides colour
						)
				);
				series.getData().add(data); // adds data to series
				chart.getData().add(series); // adds series to chart
				p.ExecutionStarts.remove(j); // removes the j'th execution start
				p.ExecutionPauses.remove(j); // removes the j'th execution pause
			}
		}
		
	}	
}
