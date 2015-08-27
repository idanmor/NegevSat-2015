package webmap;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Utils.Constants;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import netscape.javascript.JSObject;

/**
 * @author Jasper Potts
 */
public class WebMap{
    private Timeline locationUpdateTimeline;
    private List<Coardinate> markers = new ArrayList <>();
    Object lock;
    public WebMap(){
        lock = new Object();
    }
   
    public void start() {
        Stage stage = new Stage();
        // create web engine and view
        final WebView webView = new WebView();
        final WebEngine webEngine = webView.getEngine();
        webEngine.load(getClass().getResource("googlemap.html").toString());
       // create map type buttons
       webView.setOnMouseClicked(new EventHandler<MouseEvent>() {

             public void handle(MouseEvent t) {
                 if (t.getClickCount() == 2){
                   //   webEngine.executeScript("document.updateLocation()");
                     JSObject jdoc = (JSObject) webView.getEngine().getDocument();
                      if(jdoc != null){
                             JSObject ds = (JSObject) jdoc.getMember("clickLocation");
                             String cc = ds.toString().trim();
                             cc = cc.substring(1, cc.length() - 1);
                              String[] ccs = cc.split(",");
                             double newLat = Double.parseDouble(ccs[0].trim());
                             double newLon = Double.parseDouble(ccs[1].trim());
                             Coardinate marker = new Coardinate(newLat, newLon);
                             if(markers.contains(marker)){
                                markers.remove(marker);
                             }else{
                                webEngine.executeScript("document.addMarker(" + newLat + "," + newLon + ")");
                                markers.add(new Coardinate(newLat, newLon));
                             }
                      }                              
         }
             }
       });
        final ToggleGroup mapTypeGroup = new ToggleGroup();
        final ToggleButton road = new ToggleButton("Road");
        road.setSelected(true);
        road.setToggleGroup(mapTypeGroup);
        final ToggleButton satellite = new ToggleButton("Satellite");
        satellite.setToggleGroup(mapTypeGroup);
        final ToggleButton hybrid = new ToggleButton("Hybrid");
        hybrid.setToggleGroup(mapTypeGroup);
        final ToggleButton terrain = new ToggleButton("Terrain");
        terrain.setToggleGroup(mapTypeGroup);
        mapTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle1) {
                if (road.isSelected()) {
                    webEngine.executeScript("document.setMapTypeRoad()");
                } else if (satellite.isSelected()) {
                    webEngine.executeScript("document.setMapTypeSatellite()");
                } else if (hybrid.isSelected()) {
                    webEngine.executeScript("document.setMapTypeHybrid()");
                } else if (terrain.isSelected()) {
                    webEngine.executeScript("document.setMapTypeTerrain()");
                }
            }
        });
        // add map source toggles
        ToggleGroup mapSourceGroup = new ToggleGroup();
        final ToggleButton google = new ToggleButton("Google");
        google.setSelected(true);
        google.setToggleGroup(mapSourceGroup);
        final ToggleButton yahoo = new ToggleButton("Yahoo");
        yahoo.setToggleGroup(mapSourceGroup);
        final ToggleButton bing = new ToggleButton("Bing");
        bing.setToggleGroup(mapSourceGroup);
        // listen to selected source
        mapSourceGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle toggle1) {
                terrain.setDisable(true);
                if (google.isSelected()) {
                    terrain.setDisable(false);
                    webEngine.load(getClass().getResource("googlemap.html").toString());
                } else if (yahoo.isSelected()) {
                    webEngine.load(getClass().getResource("bingmap.html").toString());
                } else if (bing.isSelected()) {
                    webEngine.load(getClass().getResource("yahoomap.html").toString());
                }
                mapTypeGroup.selectToggle(road);
            }
        });
        // add search
        final TextField searchBox = new TextField("95054");
       // searchBox.setColumns(12);
        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener(new ChangeListener<String>() {
            public void changed(ObservableValue<? extends String> observableValue, String s, String s1) {
                // delay location updates to we don't go too fast file typing
                if (locationUpdateTimeline!=null) locationUpdateTimeline.stop();
                locationUpdateTimeline = new Timeline();
                locationUpdateTimeline.getKeyFrames().add(
                    new KeyFrame(new Duration(400), new EventHandler<ActionEvent>() {
                        public void handle(ActionEvent actionEvent) {
                            webEngine.executeScript("document.goToLocation(\""+searchBox.getText()+"\")");
                        }
                    })
                );
                locationUpdateTimeline.play();
            }
        });
        Button zoomIn = new Button("Zoom In");
        zoomIn.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { webEngine.executeScript("document.zoomIn()"); }
        });
        Button zoomOut = new Button("Zoom Out");
        zoomOut.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { webEngine.executeScript("document.zoomOut()"); }
        });
        Button finish = new Button("Finish");
        finish.setOnAction(new EventHandler<ActionEvent>(){
              public void handle(ActionEvent actionEvent) { 
                synchronized(lock){
                    lock.notifyAll();
                } 
                  stage.close();
              }
              
            
        });
        // create toolbar
        ToolBar toolBar = new ToolBar();
        toolBar.getStyleClass().add("map-toolbar");
        toolBar.getItems().addAll(
                road, satellite, hybrid, terrain,
                createSpacer(),
                google, yahoo, bing,
                createSpacer(),
                new Label("Location:"), searchBox, zoomIn, zoomOut, finish);
        // create root
        BorderPane root = new BorderPane();
        root.getStyleClass().add("map");
        root.setCenter(webView);
        root.setTop(toolBar);
        // create scene
        stage.setTitle("Web Map");
        Scene scene = new Scene(root,1000,700, Color.web("#666970"));
        stage.setScene(scene);
        scene.getStylesheets().add(Constants.CSS_WEB_MAP);
        // show stage
        stage.show();
    }
    public List<Coardinate> getMarkers(){
        return markers;
    }
    
    public Object getLock(){
        return lock;
    }
    private Node createSpacer() {
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        return spacer;
    }
    
    static { // use system proxy settings when standalone application    
        System.setProperty("java.net.useSystemProxies", "true");
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
}