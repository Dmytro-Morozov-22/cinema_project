package ua.lviv.lgs;

import java.util.Comparator;

public class SeanceComparator implements Comparator<Seance> {

	@Override
	public int compare(Seance o1, Seance o2) {

//		if (o1.getMovie().getTitle().compareTo(o2.getMovie().getTitle()) > 0) {
//			return 1;
//		} else if (o1.getMovie().getTitle()
//				.compareTo(o2.getMovie().getTitle()) < 0) {
//			return -1;
//		} else {
//			if (o1.getMovie().getDuration().getHour() > o2.getMovie()
//					.getDuration().getHour()) {
//				return 1;
//			} else if (o1.getMovie().getDuration().getHour() < o2.getMovie()
//					.getDuration().getHour()) {
//				return -1;
//			} else {
//				if (o1.getMovie().getDuration().getMin() > o2.getMovie()
//						.getDuration().getMin()) {
//					return 1;
//				} else if (o1.getMovie().getDuration().getMin() < o2.getMovie()
//						.getDuration().getMin()) {
//					return -1;
//				} else {
					
					if (o1.getStartTime().getHour() > o2.getStartTime() //
							.getHour()) {
						return 1;
					} else if (o1.getStartTime().getHour() < o2.getStartTime()
							.getHour()) {
						return -1;
					} else {
						if (o1.getStartTime().getMin() > o2.getStartTime()
								.getMin()) {
							return 1;
						} else if (o1.getStartTime().getMin() < o2
								.getStartTime().getMin()) {
							return -1;
						} else {
							if (o1.getEndTime().getHour() > o2.getEndTime()
									.getHour()) {
								return 1;
							} else if (o1.getEndTime().getHour() < o2
									.getEndTime().getHour()) {
								return -1;
							} else {
								if (o1.getEndTime().getMin() > o2.getEndTime()
										.getMin()) {
									return 1;
								} else if (o1.getEndTime().getMin() < o2
										.getEndTime().getMin()) {
									return -1;
								}
							}
						}
					}
//				}//
//			}//
//		}//

		return 0;
	}

}
