package Utils;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

import com.j256.ormlite.field.types.TimeStampType;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import data.Status;

public class Utils {



	public static Label getTextPictureLabel(String text, Image picture){
		return new TextAndPicture(text, picture);
	}
	
	public static Timestamp stripTimePortion(Timestamp timestamp) {
	    long msInDay = 1000 * 60 * 60 * 24; // Number of milliseconds in a day
	    long msPortion = timestamp.getTime() % msInDay;
	    timestamp.setTime(timestamp.getTime() - msPortion);
	    return timestamp;
	}
	public static Image getImageViewFromLocation(Class cs, String location){
		return new Image(cs.getResourceAsStream(Constants.MAIN_IMAGES_LOCATION + location));
	}
	public static GregorianCalendar createCalendar(String Date){
		String[] beforeSplitted = Date.split("/");
		return new GregorianCalendar(Integer.parseInt(beforeSplitted[2]), Integer.parseInt(beforeSplitted[1]), Integer.parseInt((beforeSplitted[0])));         
	}
	
	public static ImageView getIconForStatus(Class cs,Status status){
		Image icon = null;
		switch (status) {
		case ON:
			icon = getImageViewFromLocation(cs, Constants.ICON_GREEN);
			break;
		case MALFUNCTION:
			icon = getImageViewFromLocation(cs, Constants.ICON_YELLOW);
			//
			break;
		case STANDBY:
			icon = getImageViewFromLocation(cs, Constants.ICON_ORANGE);
			break;
		case NON_OPERATIONAL:
			icon = getImageViewFromLocation(cs, Constants.ICON_RED);
			break;
		}
	
		return new ImageView(icon);
	}
	public static class TextAndPicture extends Label{
		private String text = null;
		private Image picture = null;
		public TextAndPicture(String text, Image picture){
			super();
			this.text = text;
			this.picture = picture;	
			init();
		}

		private void init(){
			this.setText(text);
			this.setGraphic(new ImageView(picture));
		}

		@Override
		public String toString(){
			return text;
		}
	}
}
