package main;

import java.net.URL;
import java.text.DecimalFormat;
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
//Test

public class Controller implements Initializable{
	private DecimalFormat df = new DecimalFormat("00.##");
	private int nextDay;
	private GregorianCalendar calender = new GregorianCalendar();
	private boolean alarmFF = false;
	private String day = null;
	private Alarm alarm = new Alarm(0,0, calender);

	@FXML
	private BorderPane borderpanel;
	@FXML
	private Sphere AlarmStatus;
	@FXML
	private Button AwakeButton;
	@FXML
	private Button AlarmButton;
	@FXML
	private Button TomorrowButton;
	@FXML
	private Button PostponeButton;
	@FXML
	
	private ImageView bilde;
	@FXML
	private AnchorPane LeftPane;
	@FXML
	private Label Klokke;
	@FXML
	private Hyperlink VaerLink;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox MinCBox;
	@SuppressWarnings("rawtypes")
	@FXML
	private ChoiceBox HourCBox;
	@FXML
	private Label AlarmLabel;
	

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Set up itemboxes
		HourCBox.setItems(FXCollections.observableArrayList("00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"));
		MinCBox.setItems(FXCollections.observableArrayList("00","05","10","15","20","25","30","35","40","45","50","55"));
		HourCBox.getSelectionModel().selectFirst();
		MinCBox.getSelectionModel().selectFirst();

		//Automatic set schedule width.
		LeftPane.widthProperty().addListener((observable, oldvalue, newvalue)->{
			bilde.setFitWidth(newvalue.doubleValue());
		});
		LeftPane.heightProperty().addListener((observable, oldvalue, newvalue)->{
			bilde.setFitHeight(newvalue.doubleValue());
		});


		/*		LysKnapp.setOnMouseClicked(event -> {
	GPIO Toggle light on for 30 min		 *
		 */
		

		PostponeButton.setOnMouseClicked(event -> {
			calender.getTime().getHours();
			alarm.setTime(calender.getTime().getHours(),(calender.getTime().getMinutes()+5));
			alarm.setalarmSet(true);

		});
		
		AlarmButton.setOnMouseClicked(event -> {
			if (alarmFF == false){
				alarm.setTime(Integer.parseInt(HourCBox.getValue().toString()),Integer.parseInt(MinCBox.getValue().toString()));
				alarm.setalarmSet(true);
				alarmFF = true;
			}else{
				alarm.setalarmSet(false);
				alarmFF = false;
			}
		});
		

		TomorrowButton.setOnMousePressed(event ->{
			nextDay = 1;
		});

		TomorrowButton.setOnMouseReleased(event ->{
			nextDay = 0;
		});

		AwakeButton.setOnMousePressed(event ->{
			alarm.setisAwake(true);
			AwakeButton.setVisible(false);
		});

		Timeline timeline = new Timeline(new KeyFrame(
				Duration.millis(50),
				ae -> updateCalender()));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		AlarmLabel.setVisible(false);
		AlarmStatus.setVisible(false);
		
	}

	/*	private void toggleLight(int time){
		Timeline lightTime = new Timeline(new KeyFrame(
				Duration.millis(1000),
				ae -> lightTrigger(time)));
		lightTime.setCycleCount(Animation.INDEFINITE);
		lightTime.play();	
	}
	 */


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

		if (alarm.alarmSet()){
			System.out.println("AlarmSet");
			alarm.wakeUp();
			AlarmStatus.setVisible(true);

		}else{
			AlarmStatus.setVisible(false);
			AwakeButton.setVisible(false);
			AlarmLabel.setVisible(false);
		}
		if (alarm.isRinging()){
			AlarmLabel.setVisible(true);
			//Slumre.setVisible(true);
			//GPIO LightSwitch high.
			PostponeButton.setVisible(true);
			AwakeButton.setVisible(true);
		}else{
			PostponeButton.setVisible(false);
			AwakeButton.setVisible(false);
		}
		}
	}
