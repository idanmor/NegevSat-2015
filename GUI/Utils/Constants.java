package Utils;

import negevsatgui.MainWindow;

public interface Constants {

	//css
	public static final String CSS_PACKAGE_LOCATION = "/CssPackage/";
	public static final String CSS_LOGIN = CSS_PACKAGE_LOCATION + "logInCss.css";
	public static final String CSS_MAIN = CSS_PACKAGE_LOCATION + "mainCss.css";
	public static final String CSS_WEB_MAP = CSS_PACKAGE_LOCATION + "WebMap.css";
	public static final String CSS_CHART = CSS_PACKAGE_LOCATION + "ChartCss.css";
	
	
	//Images locations TODO 
	public static final String ICON_TEMPERATURE = CSS_PACKAGE_LOCATION + "thermometer.gif";
	public static final String ICON_VOLTAGE = CSS_PACKAGE_LOCATION + "Voltage.gif";
	public static final String ICON_CPU = CSS_PACKAGE_LOCATION + "calc.gif";
	public static final String ICON_RED = "red.jpg";
	public static final String ICON_YELLOW =  "yellow.jpg";
	public static final String ICON_GREEN =  "green.png";
	public static final String ICON_ORANGE ="orange.jpg";
	public static final String EXCEL = "excel.jpg";
	public static final String INPASS = "pass.png";
	public static final String NOT_PASS = "pass_before.png";
	//MainPane Images
	public static final String MAIN_IMAGES_LOCATION = CSS_PACKAGE_LOCATION;
	
	//General
	public static final int MAIN_PANE_WIDTH = MainWindow.getMainWindow().getMainPane().widthProperty().intValue();
	
	//Components names
	public static final String TEMERATURE_COMPONENT_NAME = "Temperature";
	public static final String ENERGY_COMPONENT_NAME = "Energy";
	
	
	public static final String MISSION_SENT = "Mission sent successfully";
	public static final String MISSION_SEND_FAILED = "Mission creation failed, check if all fields present";
	
	//Statistics
	public static final String STATISTICS_VOLTAGE_DISK_LOCATION = "c:\\Statistics\\Voltage";
	public static final String STATISTICS_TEMPRETATURE_DISK_LOCATION = "C:\\Statistics\\Temperature";

}
