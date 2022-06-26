package ua.lviv.lgs;

import java.time.LocalTime;

public class Seance {

	private Movie movie;
	private Time startTime;
	private Time endTime;

	public Seance(Movie movie, Time startTime) {
		super();
		this.movie = movie;
		this.startTime = startTime;

		LocalTime time = LocalTime.parse("00:00");
		time = time.plusHours(startTime.getHour())
				.plusMinutes(startTime.getMin());
		time = time.plusHours(movie.getDuration().getHour())
				.plusMinutes(movie.getDuration().getMin());
		this.endTime = new Time(time.getHour(), time.getMinute());

	}

	public Time getStartTime() {
		return startTime;
	}

	public Movie getMovie() {
		return movie;
	}

	public Time getEndTime() {
		return endTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((endTime == null) ? 0 : endTime.hashCode());
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result
				+ ((startTime == null) ? 0 : startTime.hashCode());
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
		Seance other = (Seance) obj;
		if (endTime == null) {
			if (other.endTime != null)
				return false;
		} else if (!endTime.equals(other.endTime))
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (startTime == null) {
			if (other.startTime != null)
				return false;
		} else if (!startTime.equals(other.startTime))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Seance [" + movie + ", startTime=" + startTime.getTime()
				+ ", endTime=" + endTime.getTime() + "]";
	}
	
}
