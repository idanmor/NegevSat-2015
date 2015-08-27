/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package negevsatgui;

import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Utils.Constants;
import Utils.Utils;
import data.DataManager;
import data.Satellite;
import data.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Pair;

/**
 *
 * @author Max
 */
public class SattelitePictureController implements Initializable {
   // private static SattelitePictureController instance;
    @FXML
    private ImageView ImageViewSunSensor;
    @FXML
    private Label labelSunSensor;
    @FXML
    private Label labelMomentumWheel;
    @FXML
    private ImageView ImageViewMomentum;
    @FXML
    private Label LabelSBand;
    @FXML
    private ImageView ImageViewSBand;
    @FXML
    private Group groupPayload;
    @FXML
    private ImageView ImageViewPayload;
    @FXML
    private Label LabelPayload;
    @FXML
    private Group groupPayload1;
    @FXML
    private ImageView ImageViewComputer;
    @FXML
    private Label labelComputer;
    @FXML
    private Group groupSP;
    @FXML
    private ImageView ImageViewSP;
    @FXML
    private Label labelSP;
    @FXML
    private Group groupPDU;
    @FXML
    private ImageView ImageViewPDU;
    @FXML
    private Label labelPDU;
    @FXML
    private Group groupRadio;
    @FXML
    private ImageView ImageViewRadio;
    @FXML
    private Label labelRadio;
    @FXML
    private Group groupBattery;
    @FXML
    private ImageView ImageViewBattery;
    @FXML
    private Label labelBattery;
    Satellite st = null;
    URL url = null;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
      DataManager db = DataManager.getInstance();
      st = db.getLatestSatData();

    if(st == null){
    	  ImageViewBattery.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          ImageViewRadio.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          ImageViewPDU.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          ImageViewComputer.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          ImageViewPayload.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          ImageViewSP.setImage(Utils.getIconForStatus(getClass(), Status.UNKNOWN).getImage());
          return;
    }
      nonSupdateSateliteStatus(st);

    }
    
    public  void nonSupdateSateliteStatus(Satellite st){
        
      ImageViewBattery.setImage(Utils.getIconForStatus(getClass(), st.getEnergyStatus()).getImage());
      ImageViewRadio.setImage(Utils.getIconForStatus(getClass(), st.getTempratureStatus()).getImage());
      ImageViewPDU.setImage(Utils.getIconForStatus(getClass(), st.getThermalStatus()).getImage());
      ImageViewComputer.setImage(Utils.getIconForStatus(getClass(), st.getSbandStatus()).getImage());
      ImageViewPayload.setImage(Utils.getIconForStatus(getClass(), st.getPayloadStatus()).getImage());
      ImageViewSP.setImage(Utils.getIconForStatus(getClass(), st.getSolarPanelsStatus()).getImage());
      
        
      }
}
