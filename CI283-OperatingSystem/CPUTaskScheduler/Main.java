package Assignment2;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
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
				Controller.run();
				long finishTime = new Date().getTime();
				long startTime = Controller.getStartTime();
				int runTime = (int) (finishTime - startTime) / 1000;
				System.out.printf("CPU Utilisation: %d%s\n", (EstimatedRuntime * 100) / runTime, "%");
			} catch (InterruptedException e) {}
		}).start();
		launch();
        
        /*
        TODO:
            * promotion/demotion of processes
            * Should be done after that
         */
	}
	
	@Override
	public void start(Stage stage) throws Exception
	{
		stage.setTitle("CPU Task Scheduler");
		
		final NumberAxis xAxis = new NumberAxis();
		final CategoryAxis yAxis = new CategoryAxis();
		final GanttChart<Number, String> chart = new GanttChart<Number, String>(xAxis, yAxis);
		
		xAxis.setLabel("Time (ms)");
		xAxis.setMinorTickCount(5);
		xAxis.setTickLabelFill(Color.DEEPSKYBLUE);
		
		
		yAxis.setLabel("Processes");
		yAxis.setTickLabelGap(20);
		yAxis.setTickLabelFill(Color.DEEPSKYBLUE);
		
		chart.setTitle( String.format("CENTRAL PROCESSING UNIT %d/%d", Controller.getCompletedJobs(), Controller.getJobs().size()));
		
		chart.setLegendVisible(false);
		chart.getStylesheets().add(Main.class.getResource("ganttchart.css").toExternalForm());
		updateChartSeries(chart);
		
		Scene scene = new Scene(chart, 1200, 900);
		chart.setBlockHeight( scene.getHeight()/Controller.getJobs().size() );
		
		stage.setScene(scene);
		stage.show();
		
		Thread updateThread = new Thread(() -> {
			do
			{
				try
				{
					Thread.sleep(1000);
					Platform.runLater(() -> updateChartSeries(chart));
				} catch (InterruptedException e) {}
			} while(Controller.getCompletedJobs() < Jobs.size());
		});
		updateThread.setDaemon(true);
		updateThread.start();
	}
	
	public String[] fillProcesses(int size)
	{
		String[] arr = new String[size];
		for (int i = 1; i <= size; i++)
		{
			arr[i - 1] = Integer.toString(Jobs.get(i - 1).ProcessID);
		}
		return arr;
	}
	
	void updateChartSeries(GanttChart<Number, String> chart)
	{
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
		Queue<Process> jobs = Controller.getJobs();
		for (int i = 0; i < jobs.size(); i++)
		{
			Process p = jobs.get(i);

			if (p.ExecutionStarts.size() != p.ExecutionPauses.size())
				return;
			
			for (int j = 0; j < p.ExecutionPauses.size(); j++)
			{
				XYChart.Series<Number, String> series = new XYChart.Series<>();
				
				XYChart.Data<Number, String> data = new XYChart.Data<>(
						p.ExecutionStarts.get(j) - Controller.getStartTime(),
						"Process"+p.ProcessID,
						new ExtraData(
								(p.ExecutionPauses.get(j) - Controller.getStartTime()) - (p.ExecutionStarts.get(j) - Controller.getStartTime()),
								(p.Priority==1)? "algorithm-fcfs" : (p.Priority==2)? "algorithm-rr" : "algorithm-sjf"
						)
				);
				if(series.getData().contains(data))
					return;
				series.getData().add(data);
				chart.getData().add(series);
			}
		}
		
	}
	
	void updateChart(GanttChart<Number, String> chart)
	{
		Queue<Process> allJobs = Controller.getJobs();
		for(Process p : allJobs)
		{
			if(p.ExecutionStarts.size() != p.ExecutionPauses.size())
				return;
			
			XYChart.Series<Number, String> series = new XYChart.Series<Number, String>();
			
		}
	}
	
}
