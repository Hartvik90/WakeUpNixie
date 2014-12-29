package main;

import java.time.LocalTime;
import java.util.GregorianCalendar;

public class Alarm{
	private LocalTime alarmTime;
	private GregorianCalendar calendar;
	private boolean alarmSet = false;
	private boolean isAwake = false;


	//Check if it is time to wake up:
	void wakeUp(){

		@SuppressWarnings("deprecation")
		LocalTime now = LocalTime.of(calendar.getTime().getHours(),calendar.getTime().getMinutes());
		if (alarmSet == true && alarmTime.compareTo(now) == 0 && isAwake == false){
			System.out.println("Ring ring");
			//Awake.setVisible(true);
			//Slumre.setVisible(true);
			//GPIO LightSwitch high.
		}
	}




	//Constructor
	Alarm(int h, int m, GregorianCalendar calendar){
		this.calendar = calendar;
		this.alarmTime = LocalTime.of(h, m);
		this.alarmSet = true;
		this.isAwake = false;
	}

	
	public void setalarmSet(boolean bol) {
		this.alarmSet = bol;
	}

	
	public void setTime(int h, int m) {
		this.alarmTime = LocalTime.of(h, m);
	}

	public boolean getalarmSet() {
		return this.alarmSet;
		}



	public void setisAwake(boolean b) {
		this.isAwake = b;
	}
}