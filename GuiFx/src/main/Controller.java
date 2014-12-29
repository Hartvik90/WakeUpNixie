package main;

import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalTime;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.shape.Sphere;
import javafx.util.Duration;

public class Controller implements Initializable{
	private DecimalFormat df = new DecimalFormat("00.##");

	GregorianCalendar calender = new GregorianCalendar();
	private boolean waitMid = false;
	private boolean alarmSR = false;

	private String day = null;
	private Alarm alarm = new Alarm(0,0);

	@FXML
	private BorderPane borderpanel;

	@FXML
	private Sphere AlarmStatus;


	@FXML
	private Button Awake;

	@FXML
	private Button AlarmKnapp;

	@FXML
	private Button Button;

	@FXML
	private ImageView bilde;

	@FXML
	private AnchorPane LeftPane;

	@FXML
	private Label Klokke;

	@FXML
	private Hyperlink VaerLink;

	@FXML
	private ChoiceBox MinCBox;

	@FXML
	private ChoiceBox HourCBox;

	@FXML
	private Label AlarmLabel;

	private int nextDay = 0;	

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		HourCBox.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
		MinCBox.setItems(FXCollections.observableArrayList("00","05","10","15","20","25","30","35","40","45","50","55"));
		HourCBox.getSelectionModel().selectFirst();
		MinCBox.getSelectionModel().selectFirst();
		LeftPane.widthProperty().addListener((observable, oldvalue, newvalue)->{
			bilde.setFitWidth(newvalue.doubleValue());
		});

		LeftPane.heightProperty().addListener((observable, oldvalue, newvalue)->{
			bilde.setFitHeight(newvalue.doubleValue());
		});

		/*		LysKnapp.setOnMouseClicked(event -> {
	GPIO Lysswitch høy i 30 min.
		 *
		 */
		AlarmKnapp.setOnMouseClicked(event -> {
			if (alarmSR == false){
				//alarmSR.setTime(Integer.parseInt(HourCBox.getValue().toString()),Integer.parseInt(MinCBox.getValue().toString()));
				alarmSR = true;
			}else{

				alarm.alarmSet = false;

				alarmSR = false;
			}
			//System.out.println(event.getX());

		});


		Button.setOnMousePressed(event ->{
			nextDay = 1;
		});

		Button.setOnMousePressed(event ->{
			nextDay = 1;
		});
		Button.setOnMouseReleased(event ->{
			nextDay = 0;
		});
		Button.setOnMouseExited(event ->{
			Button.setDisable(false);
		});

		Awake.setOnMousePressed(event ->{
			alarm.setErVaaken(true);
		});
		Awake.setVisible(false);
		AlarmLabel.setVisible(false);

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(50),
				ae -> updateCalender()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

	}

	private void toggleLight(int time){
		Timeline lightTime = new Timeline(new KeyFrame(
				Duration.millis(1000),
				ae -> lightTrigger(time)));
		lightTime.setCycleCount(Animation.INDEFINITE);
		lightTime.play();	
	}

	private Object lightTrigger(int time) {


		return null;
	}

	public class Alarm{
		LocalTime alarmTime;
		GregorianCalendar alarm = new GregorianCalendar();
		private boolean isAlarmRinging = false;
		private boolean alarmSet = false;
		private boolean erVaaken = false;
		private void wakeUp(){
			if (alarmSet == true){
				LocalTime now = LocalTime.of(calender.getTime().getHours(),calender.getTime().getMinutes());
				if (alarmTime.compareTo(now) == 0 && erVaaken == false && waitMid == false){
					isAlarmRinging = true;
					System.out.println("Ring ring");
					AlarmLabel.setVisible(true);
					Awake.setVisible(true);
					//Slumre.setVisible(true);
					//GPIO Lysswitch høy.
				}	else{
					isAlarmRinging = false;
					Awake.setVisible(false);
					//Slumre.setVisible(false);
					AlarmLabel.setVisible(false);
				}
			}
		}

Alarm(int h, int m){
			this.alarmTime = LocalTime.of(h, m);
				LocalTime now = LocalTime.of(calender.getTime().getHours(),calender.getTime().getMinutes());
				if (alarmTime.compareTo(now) <= 0){
					waitMid = true;	
				}
				this.alarmSet = true;
			


		}
public void setTime(int h, int m) {
	this.alarmTime = LocalTime.of(h, m);
	
}

		public boolean isErVaaken() {
			return erVaaken;
		}
		public void setErVaaken(boolean erVaaken) {
			this.erVaaken = erVaaken;
		}

	}


	@SuppressWarnings("deprecation")
	public void updateCalender(){

		calender = new GregorianCalendar();


		switch (calender.getTime().getDay() + nextDay){
		case 0:
			day = new String("Sunday.png");
			break;
		case 1:
			day = new String("Monday.png");
			break;
		case 2:
			day = new String("Tuesday.png");
			break;
		case 3:
			day = new String("Wednesday.png");
			break;
		case 4:
			day = new String("Thursday.png");
			break;
		case 5:
			day = new String("Friday.png");
			break;
		case 6:
			day = new String("Saturday.png");
			break;
		case 7:
			day = new String("Sunday.png");
			break;


		}
		bilde.setImage(new Image(Controller.class.getResource(day).toString()));

		Klokke.setText(df.format(calender.getTime().getHours()) + ":" + df.format(calender.getTime().getMinutes()) + ":" + df.format(calender.getTime().getSeconds()));

		if (alarm.alarmSet == true){
			alarm.wakeUp();
			AlarmStatus.setVisible(true);

		}else{

			AlarmStatus.setVisible(false);

		}
		if (calender.getTime().getHours() == 0 && calender.getTime().getMinutes() == 0){
			waitMid = false;
		}

	}
}