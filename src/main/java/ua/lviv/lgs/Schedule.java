package ua.lviv.lgs;

import static java.util.stream.Collectors.toSet;
import java.util.Set;
import java.util.TreeSet;

public class Schedule {

	private Set<Seance> seances = new TreeSet<>(new SeanceComparator());

	public Schedule(Seance s) {
		super();
		this.seances.add(s);
	}

	public Set<Seance> getSeances() {
		return seances;
	}

	public Set<Seance> addSeance(Seance newSeance) {
		seances.add(newSeance);
		return seances;
	}

//???
	public void removeSeance(Seance oldSeance) {
		seances = seances.stream().filter(x -> !x.equals(oldSeance))
				.collect(toSet());
	}

//STANDART METHODS
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((seances == null) ? 0 : seances.hashCode());
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
		Schedule other = (Schedule) obj;
		if (seances == null) {
			if (other.seances != null)
				return false;
		} else if (!seances.equals(other.seances))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Schedule=" + seances;
	}

}