package Panels;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import data.Component;
import Utils.Utils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import misc.SatalliteUtils;
import misc.StatisticDataItem;
import misc.StatisticDataItemInterface;

/**
 * A class that acts as abstract component for statistics view.
 * Represents the screen of statistics for a chosen component(which should extend this class)
 * @author Max
 *
 */
public abstract class AbstractComponentStatistics implements CommunicationRefreshInterface {
	TableView<StatisticDataItemInterface> table;
	private BorderPane rightPane;
	private BorderPane mainPane;
	private SplitPane split;
	LineChart<String,Number> lineChart;

	public AbstractComponentStatistics(BorderPane mainParentPane){			
		createTable();
		split = new SplitPane();
		split.setOrientation(Orientation.HORIZONTAL);
		rightPane = new BorderPane();
		mainPane = new BorderPane();
		HBox chartHbox = SatalliteUtils.getHBox(10);

		final CategoryAxis xAxis = new CategoryAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Day Time");

		lineChart = new LineChart<String,Number>(xAxis,yAxis);
		Button clearChart = new Button("Clear Chart");
		clearChart.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				lineChart.getData().clear();

			}
		});
		rightPane.setCenter(lineChart);
		ImageView excellImage = new ImageView(Utils.getImageViewFromLocation(this.getClass(),"excel.jpg"));
		Button exportToExcel = new Button("Export to excel",excellImage);
		exportToExcel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				try {
						writeExcelApache(lineChart);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		chartHbox.getChildren().addAll(clearChart, exportToExcel);
		rightPane.setTop(chartHbox);
		split.getItems().addAll(table,rightPane);
		mainPane.setCenter(split);
		mainPane.setTop(createTopHBox());
		mainParentPane.setCenter(this.mainPane);

	}
	protected HBox createTopHBox(){
		HBox box = SatalliteUtils.getHBox(10);
		Label beforeDate = new Label("End date: ");
		DatePicker before = new DatePicker(LocalDate.now());   
		Label afterDate = new Label("Start date :");
		DatePicker after = new DatePicker(LocalDate.now());  
		after.setUserData(Calendar.getInstance().getTimeInMillis());
		Button filterButton = new Button("Filter");
		filterButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent t) {
				filter(before.getEditor().getText(), after.getEditor().getText());
			}
		});
		box.getChildren().addAll(afterDate,after,beforeDate,before, filterButton);
		return box;
	}

	protected void populateTableNodes(Timestamp oldestTS,
			ObservableList<StatisticDataItemInterface> nodes,
			DateFormat formatter, Timestamp toDate) {
		boolean hasData = false;
		List<Component> components = getComponent(oldestTS, toDate);
		if(components.isEmpty()){
			return;
		}
		Set<String> sensors = components.get(0).getSetOfSensorsNames();
		for(String sensor : sensors){
			String[][] data = new String[components.size()][3];
			for(int i = 0 ; i < components.size() ; i++){
				hasData = true;
				Component comp = components.get(i);
				data[i][0] = formatter.format(new Date(comp.getSampleTimestamp().getTime())).toString();
				data[i][1] = comp.getSensorValue(sensor).toString();
				data[i][2] = sensor;
			}    
			if(hasData){
				nodes.add(new StatisticDataItem(formatter.format(new Date(oldestTS.getTime())).toString(), sensor, getObjectName(), data)); //TODO
			}
		}
	}
	/**
	 * Unfortunately the filter doesnt work
	 * @param before
	 * @param after
	 */
	private void filter(String before, String after){
		Timestamp beforeCal = null;
		Timestamp afterCal = null;
		before = before.replace("/", "-");
		after = after.replace("/", "-");
		long dayinMS = 86400000;
		DateFormat writeFormat = new SimpleDateFormat( "dd-MM-yyyy");
		if(before != null && !before.isEmpty()){

			try {
				Date beforeDate = writeFormat.parse(before);
				beforeCal = new Timestamp(beforeDate.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}

		}else{

			beforeCal = new Timestamp(Calendar.getInstance().getTimeInMillis() + dayinMS);
		}
		if(after != null && !after.isEmpty()){
			Date afterDate;
			try {
				afterDate = writeFormat.parse(after);
				afterCal = new Timestamp(afterDate.getTime());
			} catch (ParseException e) {
				e.printStackTrace();
			}


		}else{
			long monthInMS = 26280000;//need to mult by 100
			afterCal = new Timestamp(Calendar.getInstance().getTimeInMillis() - monthInMS * 100);
		}

		ObservableList<StatisticDataItemInterface> filteredItems = FXCollections.observableArrayList();
		populateTableNodes(afterCal, filteredItems, new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss"), beforeCal);
		table.setItems(filteredItems);
	}
	
	
	/**
	 * Writes statistics data to excel using Apache POI platform
	 * @param chart
	 * @throws Exception
	 */
	private void writeExcelApache(LineChart<String, Number> chart) throws Exception{
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Statistics");
		SimpleDateFormat formatter = new SimpleDateFormat("dd-mm-yy_hh-mm");
		String time = formatter.format(new Date(System.currentTimeMillis()));
		File file = new File(getExellFileLocationAndName() + time + ".xls");
		file.mkdirs();
		if(file.exists()){
			file.delete();
		}
		file.createNewFile();
		List<List<String>> data = getTableDataAsList();
		int numOfRows = 0;
		int columnNum = 0;
		for(List<String> newLine : data){
		
			for(String singleData : newLine){
				Row row = sheet.getRow(numOfRows);
				row = row == null ? sheet.createRow(numOfRows) : row;
				numOfRows++;

				Cell cell = row.createCell(columnNum);
				cell.setCellValue(singleData);
				sheet.autoSizeColumn(columnNum);
				
			}
			numOfRows = 0;
			columnNum++;
		}
		try {
			FileOutputStream out =
					new FileOutputStream(file);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	private List<List<String>> getTableDataAsList() {
		List<List<String>> text = new ArrayList<List<String>>();
		//StringBuilder text = new StringBuilder();
		TableView.TableViewSelectionModel<StatisticDataItemInterface> selectionModel = table.getSelectionModel();
		for(int i = 0 ; i < selectionModel.getSelectedItems().size() ; i++){
			String[][] data = selectionModel.getSelectedItems().get(i).getData();
			for(int j = 0 ; j < data.length; j++){
				if(i == 0 && j == 0){
					for(int k = 0 ; k < data[j].length ; k++){
						List<String> newColumn = new ArrayList<>();
						newColumn.add(table.getColumns().get(k).getText());
						text.add(newColumn);
					}
				}
				//	                    	}
				for(int k = 0 ; k < data[j].length ; k++){
					text.get(k).add(data[j][k]);					
				}
			}

		}
		return text;
	}

	/**
	 * Gets the location where to write the excel file
	 * @return
	 */
	protected abstract String getExellFileLocationAndName();

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createTable(){
		if(table == null){
			table = new TableView<>();
			table.setPrefWidth(600);
			table.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			TableColumn date = new TableColumn("Date Taken");
			TableColumn component = new TableColumn("Component");
			TableColumn type = new TableColumn("Type");
			date.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
			component.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
			type.prefWidthProperty().bind(table.widthProperty().multiply(0.25));

			date.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("date")
					);
			component.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("component")
					);
			type.setCellValueFactory(
					new PropertyValueFactory<StatisticDataItemInterface,String>("type")
					);
			table.getColumns().addAll(date, component, type);
			table.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent t) {
					if(t.getClickCount() == 2 && table.getSelectionModel().getSelectedItem() != null){
						createStatisicsWindow(lineChart);
					}
					t.consume();
				}
			});
		}
		populateTable();
		table.getItems();
	}
	private void populateTable(){
		final long monthInMS = 26280000;//need to mult by 100
		final long dayinMS = 86400000;
		Timestamp oldestTS=new Timestamp(System.currentTimeMillis() - monthInMS * 100);
		Timestamp TS=new Timestamp(System.currentTimeMillis());
		ObservableList<StatisticDataItemInterface> nodes = FXCollections.observableArrayList();
		oldestTS = Utils.stripTimePortion(oldestTS);
		DateFormat formatter = new SimpleDateFormat( "dd-MM-yyyy HH:mm:ss");
		while(oldestTS.before(TS)){
			Timestamp toDate = new Timestamp(oldestTS.getTime() + dayinMS);
			populateTableNodes(oldestTS, nodes, formatter, toDate);
			oldestTS.setTime(oldestTS.getTime() + dayinMS);
		}
		table.getItems().addAll(nodes);
	}


	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createStatisicsWindow(LineChart<String,Number> lineChart){
		lineChart.getData().clear();
		lineChart.setTitle("Diagnostics");
		ObservableList<StatisticDataItemInterface> selected = table.getSelectionModel().getSelectedItems();
		for(int i = 0 ; i < selected.size(); i++){
			StatisticDataItemInterface selectedItem = selected.get(i);
			String[][] selectedItemData = selected.get(i).getData();
			XYChart.Series series = new XYChart.Series();
			series.setName(selectedItem.getDate());
			for(int j = 0 ; j < selectedItemData.length; j++){
				series.getData().add(new XYChart.Data(selectedItemData[j][0].substring(selectedItemData[j][0].indexOf(" ") + 1), Double.valueOf(selectedItemData[j][1])));          
			}
			lineChart.getData().add(series);
		}


	}

	
	@Override
	public void refreshPanelData(){
		this.createTable();
	}
	//Abstracts
	public abstract List<Component> getComponent(Timestamp oldestTS, Timestamp TS);
	public abstract String getObjectName();
}
