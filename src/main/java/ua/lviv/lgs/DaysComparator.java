package ua.lviv.lgs;

import java.util.Comparator;

public class DaysComparator implements Comparator <Days>{

	@Override
	public int compare(Days o1, Days o2) {
		
		return o1.name().compareTo(o2.name());
				
	}

}
