package Assignment2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.chart.Axis;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ValueAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;

public class GanttChart<X, Y> extends XYChart<X, Y> {

	public static class ExtraData {

		public long Length; // the length
		public String CSSStyling; // the styling

		// constructs extra data
		public ExtraData(long lengthMs, String CSSStyling) {
			super();
			this.Length = lengthMs;
			this.CSSStyling = CSSStyling;
		}

		// gets length
		public long getLength() {
			return Length;
		}

		// sets length
		public void setLength(long Length) {
			this.Length = Length;
		}

		// gets css content
		public String getStyleClass() {
			return CSSStyling;
		}

		// sets css content
		public void setStyleClass(String CSSStyling) {
			this.CSSStyling = CSSStyling;
		}
	}

	private double blockHeight = 20; // standard block height
	
	//Constructors handling creation of the chartwith given parameters
	public GanttChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis) {
		this(xAxis, yAxis, FXCollections.<Series<X, Y>>observableArrayList());
	}

	public GanttChart(@NamedArg("xAxis") Axis<X> xAxis, @NamedArg("yAxis") Axis<Y> yAxis,
			@NamedArg("data") ObservableList<Series<X, Y>> data) {
		super(xAxis, yAxis);
		if (!(xAxis instanceof ValueAxis && yAxis instanceof CategoryAxis)) {
			throw new IllegalArgumentException("Axis type incorrect, X and Y should both be NumberAxis");
		}
		setData(data); // sets the data
	}
	
	// gets the style class of an object
	private static String getStyleClass(Object obj) {
		return ((ExtraData) obj).getStyleClass();
	}

	// gets the length
	private static double getLength(Object obj) {
		return ((ExtraData) obj).getLength();
	}

	@Override
	protected void layoutPlotChildren() {

		for (int seriesIndex = 0; seriesIndex < getData().size(); seriesIndex++) {
			// run through the series

			Series<X, Y> series = getData().get(seriesIndex);

			Iterator<Data<X, Y>> iter = getDisplayedDataIterator(series); // gets the iterator
			
			while (iter.hasNext()) 
			{ // while there is data to iterator over in the series
				Data<X, Y> item = iter.next(); // item is next data
				double x = getXAxis().getDisplayPosition(item.getXValue());
				double y = getYAxis().getDisplayPosition(item.getYValue());
				if (Double.isNaN(x) || Double.isNaN(y)) {
					continue;
				}
				Node node = item.getNode();
				Rectangle bar; // the bar representing the series
				if (node != null) { // makes sure item node is not null
					if (node instanceof StackPane) { // checks if node is a stackpane
						StackPane region = (StackPane) item.getNode(); // get the region node
						if (region.getShape() == null) { // checks the shape isnt null
							// creates a new rectangle with length and height
							bar = new Rectangle(getLength(item.getExtraValue()), getBlockHeight());
						} else if (region.getShape() instanceof Rectangle) {
							bar = (Rectangle) region.getShape(); // not null therefor set bar to the shape
						} else {
							return;
						}
						// calculates the width it should be going across the screen (this would be a bar chart otherwise)
						bar.setWidth(getLength(item.getExtraValue())
								* ((getXAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis) getXAxis()).getScale())
										: 1));

						// same as width but for height
						bar.setHeight(getBlockHeight()
								* ((getYAxis() instanceof NumberAxis) ? Math.abs(((NumberAxis) getYAxis()).getScale())
										: 1));
						// decrements y by block height / 2
						y -= getBlockHeight() / 2.0;

						// Region doesn't update itself when the shape is changed, so
						// null out and then restore the shape in order to force invalidation.
						region.setShape(null);
						region.setShape(bar);
						region.setScaleShape(false);
						region.setCenterShape(false);
						region.setCacheShape(false);

						node.setLayoutX(x);
						node.setLayoutY(y);
					}
				}
			}
		}
	}

	// gets the block height
	public double getBlockHeight() {
		return blockHeight;
	}

	// sets the block height
	public void setBlockHeight(double blockHeight) {
		this.blockHeight = blockHeight;
	}

	// method handling data adding
	@Override
	protected void dataItemAdded(Series<X, Y> series, int itemIndex, Data<X, Y> item) {
		Node block = createContainer(series, getData().indexOf(series), item, itemIndex);
		getPlotChildren().add(block);
	}

	// method handling data removal
	@Override
	protected void dataItemRemoved(final Data<X, Y> item, final Series<X, Y> series) {
		final Node block = item.getNode();
		getPlotChildren().remove(block);
		removeDataItemFromDisplay(series, item);
	}

	// empty method for data change handling
	@Override
	protected void dataItemChanged(Data<X, Y> item) {
	}

	// method handling for when a series is added
	@Override
	protected void seriesAdded(Series<X, Y> series, int seriesIndex) {
		for (int j = 0; j < series.getData().size(); j++) {
			Data<X, Y> item = series.getData().get(j);
			Node container = createContainer(series, seriesIndex, item, j);
			getPlotChildren().add(container);
		}
	}

	// method handling for when a series is removed
	@Override
	protected void seriesRemoved(final Series<X, Y> series) {
		for (XYChart.Data<X, Y> d : series.getData()) {
			final Node container = d.getNode();
			getPlotChildren().remove(container);
		}
		removeSeriesFromDisplay(series);

	}

	private Node createContainer(Series<X, Y> series, int seriesIndex, final Data<X, Y> item, int itemIndex) {

		Node container = item.getNode(); // gets item node

		if (container == null) { // if item node is null
			container = new StackPane(); // create a new one
			item.setNode(container); // set item node to container
		}

		container.getStyleClass().add(getStyleClass(item.getExtraValue())); // set container css to item css

		return container; // return container
	}

	// updates the range of axis
	@Override
	protected void updateAxisRange() {
		// gets the axis and stores them
		final Axis<X> xa = getXAxis();
		final Axis<Y> ya = getYAxis();

		// initalises xData and yData
		List<X> xData = null;
		List<Y> yData = null;

		// checks if either axis is auto ranging and creates an ArrayList if so
		if (xa.isAutoRanging())
			xData = new ArrayList<X>();
		if (ya.isAutoRanging())
			yData = new ArrayList<Y>();

		// if there is an array list for either axis
		if (xData != null || yData != null) {
			// run through the series
			for (Series<X, Y> series : getData()) {
				// run through series data
				for (Data<X, Y> data : series.getData()) {
					// if data is not null
					if (xData != null) {
						// add the current xValue to the xData list
						xData.add(data.getXValue());
						xData.add(
								xa.toRealValue(xa.toNumericValue(data.getXValue()) + getLength(data.getExtraValue())));
					}
					// if y data is non null
					if (yData != null) {
						// add it to the list
						yData.add(data.getYValue());
					}
				}
			}
			// invalidate the ranges if not null
			if (xData != null)
				xa.invalidateRange(xData);
			if (yData != null)
				ya.invalidateRange(yData);
		}
	}

}