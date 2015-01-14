package main;

import java.time.LocalTime;
import java.util.GregorianCalendar;

public class Alarm{
	private LocalTime alarmTime;
	private GregorianCalendar calendar;
	private boolean alarmSet = false;
	private boolean isAwake = false;
	private boolean isRinging = false;

	//Check if it is time to wake up:
	void wakeUp(){
		calendar = new GregorianCalendar();
		@SuppressWarnings("deprecation")
		LocalTime now = LocalTime.of(calendar.getTime().getHours(),calendar.getTime().getMinutes());
		if (alarmSet && !isAwake && alarmTime.compareTo(now) == 0){
			isRinging = true;
		}else{
			isRinging = false;
		}
	}

	//Constructor
	Alarm(int hour, int minutes, GregorianCalendar calendar){
		this.calendar = calendar;
		alarmTime = LocalTime.of(hour, minutes);
		alarmSet = false;
		isAwake = false;
	}


	public void setalarmSet(boolean bol) {
		alarmSet = bol;
	}


	public void setTime(int hours, int minutes) {
		alarmTime = LocalTime.of(hours, minutes);
	}

	public boolean alarmSet() {
		return alarmSet;
	}

	public boolean isRinging() {
		return isRinging;
	}

	public void setisAwake(boolean b) {
		isAwake = b;
	}
}