package ua.lviv.lgs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(MyTestWatcher.class)
public class CinemaTest {

	private Cinema cinema;
	private Movie movie;
	private Seance seance;
	private Seance firstSeance;

	@BeforeEach
	public void beforeTest() {
		cinema = new Cinema(new Time(10, 00), new Time(22, 30));
		movie = new Movie("go", new Time(1, 30));
		seance = new Seance(movie, new Time(10, 30));
		firstSeance = new Seance(movie, new Time(14, 30));
	}

	@AfterEach
	public void afterTest() {
		cinema = null;
		movie = null;
		seance = null;
		firstSeance = null;
	}

	@Test
	@DisplayName("Check the size of the collection")
	public void addMovieSizeTest() {
		cinema.addMovie(movie);
		int realSize = cinema.getMoviesLibrary().size();
		int expectedSize = 1;

		assertEquals(expectedSize, realSize);
	}

	@Test()
	@Timeout(value = 100, unit = TimeUnit.MILLISECONDS)
	@DisplayName("Check the duration of the movie")
	public void addMovieDurationTest() {
		cinema.addMovie(movie);
		Time realTime = cinema.getMoviesLibrary().get(0).getDuration();
		Time expectedTime = movie.getDuration();

		assertEquals(expectedTime, realTime);
	}

	@RepeatedTest(value = 2)
	@DisplayName("Check the title of the movie")
	public void addMovieTest() {
		cinema.addMovie(movie);
		String realTitle = cinema.getMoviesLibrary().get(0).getTitle();
		String expectedTitle = movie.getTitle();

		assertEquals(expectedTitle, realTitle);
	}

	@Test()
	public void endTimeTest() {
		Time realTime = seance.getEndTime();

		assertEquals(new Time(12, 00), realTime);
	}

	@Test()
	public void addSeanceToSchedulesTest() {
		cinema.addSeanceToSchedules(seance, Days.FRIDAY);
		int realSize = cinema.getSchedules().size();
		int expectedSize = 1;

		assertEquals(expectedSize, realSize);
	}

	@Test()
	public void removeMovieTest() {
		cinema.addMovie(movie);
		cinema.addSeanceToSchedules(firstSeance, Days.MONDAY);
		cinema.removeMovie(movie);
		
		int realSizeSchedules = cinema.getSchedules().get(Days.MONDAY).getSeances().size();
		int realSizeMovies = cinema.getMoviesLibrary().size();
	
		assertEquals(0, realSizeSchedules);
		assertEquals(0, realSizeMovies);
		assertTrue(cinema.getSchedules().get(Days.MONDAY).getSeances().isEmpty());
		assertTrue(cinema.getMoviesLibrary().isEmpty());
	}

	@Test
	public void removeSeanceTest() {
		cinema.addSeanceToSchedules(firstSeance, Days.SATURDAY);
		cinema.addSeanceToSchedules(seance, Days.SATURDAY);
		int schedulesSize = cinema.getSchedules().get(Days.SATURDAY)
				.getSeances().size();

		assertEquals(2, schedulesSize);

		cinema.removeSeance(seance, Days.SATURDAY);

		assertEquals(1, cinema.getSchedules().size());
		assertFalse(cinema.getSchedules().isEmpty());
	}

	@Test
	public void addSeanceToSchedulesCheckValueTest() {
		cinema.addSeanceToSchedules(firstSeance, Days.SATURDAY);
		Time realStartTime = null;
		Time realEndTime = null;
		Iterator<Seance> iterator = cinema.getSchedules().get(Days.SATURDAY)
				.getSeances().iterator();
		while (iterator.hasNext()) {
			Seance next = iterator.next();
			realStartTime = next.getStartTime();
			realEndTime = next.getEndTime();
		}

		assertEquals(firstSeance.getStartTime(), realStartTime);
		assertSame(firstSeance.getStartTime(), realStartTime);
		assertEquals(firstSeance.getEndTime(), realEndTime);
		assertSame(firstSeance.getEndTime(), realEndTime);
	}

}
