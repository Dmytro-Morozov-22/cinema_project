package ua.lviv.lgs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class Cinema {

	private Time open;
	private Time close;

	private List<Movie> moviesLibrary = new ArrayList<Movie>();
	private Map<Days, Schedule> schedules = new TreeMap<Days, Schedule>(
			new DaysComparator());

	public Cinema(Time open, Time close) {
		super();
		this.open = open;
		this.close = close;
	}

//CASTOM METHODS
//showMoviesLibrary
	public void showMoviesLibrary() {
		for (Movie movie : moviesLibrary)
			System.out.println(movie);
	}

//showSchedules
	public void showSchedules() {
		Set<Entry<Days, Schedule>> entrySet = schedules.entrySet();
		for (Entry<Days, Schedule> entry : entrySet)
			System.out.println(entry);
	}

//addMovie
	public void addMovie(Movie movie) {
		moviesLibrary.add(movie);
	}

//addSeanceToSchedules
	public void addSeanceToSchedules(Seance seance, Days enumDay) {

			if (schedules.isEmpty()) {
				schedules.put(enumDay, new Schedule(seance));
			} else {

				Iterator<Entry<Days, Schedule>> itr = schedules.entrySet().iterator();
				int count = 0;
				while (itr.hasNext()) {
					Entry<Days, Schedule> next = itr.next();
					if (next.getKey() == enumDay) {
						Schedule value = next.getValue();
						value.addSeance(seance);
					} else {
						count++;
					}
				}

				if (schedules.size() == count) {
					schedules.put(enumDay, new Schedule(seance));
				}
			}

	}

//removeMovie 
	public void removeMovie(Movie movie) {
		moviesLibrary = moviesLibrary.stream().filter(x -> !x.equals(movie))
				.collect(Collectors.toList());

		Iterator<Entry<Days, Schedule>> itr = schedules.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Days, Schedule> next = itr.next();
			Schedule value = next.getValue();

			Iterator<Seance> iterator = value.getSeances().iterator();
			while (iterator.hasNext()) {
				Seance next2 = iterator.next();
				if (next2.getMovie().equals(movie)) {
					iterator.remove();
				}
			}
		}
	}

//removeSeance
	public void removeSeance(Seance seance, Days enumDay) {

		Iterator<Entry<Days, Schedule>> itr = schedules.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<Days, Schedule> next = itr.next();
			if (next.getKey() == enumDay) {
				Schedule value = next.getValue();
				Iterator<Seance> iterator = value.getSeances().iterator();
				while (iterator.hasNext()) {
					Seance next2 = iterator.next();

					if (next2.equals(seance)) {

						iterator.remove();
					}
				}
			} else {
				System.err.println("Фільму немає у розкладі");
			}
		}
	}

//STANDART METHODS
	public List<Movie> getMoviesLibrary() {
		return moviesLibrary;
	}
	
	public Map<Days, Schedule> getSchedules() {
		return schedules;
	}
	
	public Time getOpen() {
		return open;
	}

	public Time getClose() {
		return close;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((close == null) ? 0 : close.hashCode());
		result = prime * result
				+ ((moviesLibrary == null) ? 0 : moviesLibrary.hashCode());
		result = prime * result + ((open == null) ? 0 : open.hashCode());
		result = prime * result
				+ ((schedules == null) ? 0 : schedules.hashCode());
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
		Cinema other = (Cinema) obj;
		if (close == null) {
			if (other.close != null)
				return false;
		} else if (!close.equals(other.close))
			return false;
		if (moviesLibrary == null) {
			if (other.moviesLibrary != null)
				return false;
		} else if (!moviesLibrary.equals(other.moviesLibrary))
			return false;
		if (open == null) {
			if (other.open != null)
				return false;
		} else if (!open.equals(other.open))
			return false;
		if (schedules == null) {
			if (other.schedules != null)
				return false;
		} else if (!schedules.equals(other.schedules))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cinema openTime=" + open.getTime() + ", closeTime=" + close.getTime();
	}
		
}
