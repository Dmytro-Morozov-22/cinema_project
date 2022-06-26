package ua.lviv.lgs;

import java.time.LocalTime;

public class Time {

	int hour;
	int min;
	boolean checkTime = true;
	
	public Time(int hour, int min) {
		super();

		if (hour >= 0 && hour < 24) {
			this.hour = hour;
		} else {
			checkTime = false;
			System.out.println("Wrong hour");
		}

		if (min >= 0 && min < 60) {
			this.min = min;
		} else {
			checkTime = false;
			System.out.println("Wrong min");
		}
	}

	public boolean getCheckTime() {
		return checkTime;
	}
	
	public int getHour() {
		return hour;
	}

	public int getMin() {
		return min;
	}

	public LocalTime getTime() {
		LocalTime seanceTime = LocalTime.parse("00:00");
		seanceTime = seanceTime.plusHours(hour).plusMinutes(min);
		return seanceTime;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + min;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (min != other.min)
			return false;
		return true;
	}

//	@Override
//	public String toString() {
//		return "Time [hour=" + hour + ", min=" + min + "]";
//	}
	
	
	@Override
	public String toString() {
		return hour + ":" + min;
	}
	
	

}
