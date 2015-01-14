package main;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import com.github.dvdme.ForecastIOLib.ForecastIO;

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
import javafx.scene.control.TextArea;
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
	private boolean alarmFlipFlop = false;
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
	@FXML
	private TextArea WeatherField;


	@SuppressWarnings({ "unchecked", "deprecation" })
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Må flyttes over i Wather init

		ForecastIO fio = new ForecastIO("da289976f0020ae24853417ab9a6c011"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		fio.setExcludeURL("hourly,minutely");             //excluded the minutely and hourly reports from the reply
		fio.getForecast("58.937861", "5.702062");   //sets the latitude and longitude - not optional
		//it will fail to get forecast if it is not set
		//this method should be called after the options were set
		System.out.println(fio.getDaily());
		//System.out.printf("%s%s\n","Todays weather ",fio.getDaily().get("data").asArray().get(0).asObject().get("icon"));
		//System.out.printf("%s%f\n","Minimum temperature ",fio.getDaily().get("data").asArray().get(0).asObject().get("temperatureMin").asDouble());
		
		String vare = " ";
		vare +="Todays weather: ";
		vare += fio.getDaily().get("data").asArray().get(0).asObject().get("icon");
		//vare += "/nMinimum temperature: /n";
		//vare += toString(fio.getDaily().get("data").asArray().get(0).asObject().get("temperatureMin").asString());		
				WeatherField.appendText(vare);
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
			if (alarmFlipFlop == false){
				alarm.setTime(Integer.parseInt(HourCBox.getValue().toString()),Integer.parseInt(MinCBox.getValue().toString()));
				alarm.setalarmSet(true);
				alarm.setisAwake(false);
				alarmFlipFlop = true;
			}else{
				alarm.setalarmSet(false);
				alarmFlipFlop = false;
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
			alarm.wakeUp();
			AlarmStatus.setVisible(true);

		}else{
			AlarmStatus.setVisible(false);
			AwakeButton.setVisible(false);
			AlarmLabel.setVisible(false);
		}
		if (alarm.isRinging()){
			AlarmLabel.setVisible(true);
			PostponeButton.setVisible(true);
			AwakeButton.setVisible(true);
			//Slumre.setVisible(true);
			//GPIO LightSwitch high.
		}else{
			AlarmLabel.setVisible(false);
			PostponeButton.setVisible(false);
			AwakeButton.setVisible(false);
		}

	}
}
