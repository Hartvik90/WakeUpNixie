package main;

import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Weather {
	

	public Weather(){
		
	}
	
public void updateWeather(){

ForecastIO fio = new ForecastIO("da289976f0020ae24853417ab9a6c011"); //instantiate the class with the API key. 
fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
fio.setExcludeURL("hourly,minutely");             //excluded the minutely and hourly reports from the reply
fio.getForecast("58.937861", "5.702062");   //sets the latitude and longitude - not optional
                                               //it will fail to get forecast if it is not set
                                               //this method should be called after the options were set
System.out.println(fio.getDaily());
System.out.printf("%s%s\n","Todays weather ",fio.getDaily().get("data").asArray().get(0).asObject().get("icon"));
System.out.printf("%s%f\n","Minimum temperature ",fio.getDaily().get("data").asArray().get(0).asObject().get("temperatureMin").asDouble());

}
}
