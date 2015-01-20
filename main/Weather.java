package main;

import com.github.dvdme.ForecastIOLib.ForecastIO;

public class Weather {
	
	private ForecastIO fio;

	public Weather(){
		System.out.println("Init weather");
	//it will fail to get forecast if it is not set
		//this method should be called after the options were set
	}

	public String updateWeather(){
		ForecastIO fio = new ForecastIO("da289976f0020ae24853417ab9a6c011"); //instantiate the class with the API key. 
		fio.setUnits(ForecastIO.UNITS_SI);             //sets the units as SI - optional
		fio.setExcludeURL("hourly,minutely");             //excluded the minutely and hourly reports from the reply
	
		System.out.println("updateWeather");
		fio.getForecast("58.937861", "5.702062");   //sets the latitude and longitude - not optional
		System.out.println(fio.getDaily());
		//System.out.printf("%s%s\n","Todays weather ",fio.getDaily().get("data").asArray().get(0).asObject().get("icon"));
		
		String vare = " ";
		vare +="Todays weather:\n";
		//vare += fio.getDaily().get("data").asArray().get(0).asObject().get("icon");

		vare += fio.getDaily().get("data").asArray().get(0).asObject().get("summary");
		vare += "\n" +"Minimum temperature:\n" + fio.getDaily().get("data").asArray().get(0).asObject().get("temperatureMin").asDouble();
	
		return vare;
	}
}
