package ua.lviv.lgs;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

public class Main {  

	static void menu() {
		System.out.println("1. Створити кінотеатр");
		System.out.println("2. Додати фільм до фільмотеки");
		System.out.println("3. Вивести фільмотеку у консоль");
		System.out.println("4. Додати фільм у розклад");
		System.out.println("5. Вивести розклад у консоль");
		System.out.println("6. Видалити фільм з розкладу і фільмотеки");
		System.out.println("7. Видалити фільм лише з розкладу");
		System.out.println("8. Вийти з програми");
	}

	static boolean checkTime(int hour, int min) {
		boolean result = true;
		if ((hour >= 0 && hour < 24) || (min >= 0 && min < 60)) {
			result = false;
		}
		return result;
	}

	public static void main(String[] args) {

		ArrayList<Cinema> cinemaList = new ArrayList<Cinema>();
		Cinema newCinema = null;

		LocalTime openTime = LocalTime.parse("00:00");
		LocalTime closeTime = LocalTime.parse("00:00");

		compare: while (true) {
			menu();
			Scanner sc = new Scanner(System.in);
			switch (sc.next()) {

//1. Створити кінотеатр
			case "1":

				if (cinemaList.isEmpty()) {
					System.out.println(
							"Вкажіть годину та хвилину ВІДКРИТТЯ кінотеатру через пробіл у форматі 00 00");

					int openHour = sc.nextInt();
					int openMin = sc.nextInt();

					if (checkTime(openHour, openMin)) {
						System.err.println("Невірний формат часу - " + openHour
								+ ":" + openMin);
						continue compare;
					}

					System.out.println(
							"Вкажіть годину та хвилину ЗАКРИТТЯ кінотеатру через пробіл у форматі 00 00");
					int closeHour = sc.nextInt();
					int closeMin = sc.nextInt();

					if (checkTime(closeHour, closeMin)) {
						System.err.println("Невірний формат часу - " + closeHour
								+ ":" + closeHour);
						continue compare;
					}

					newCinema = new Cinema(new Time(openHour, openMin),
							new Time(closeHour, closeMin));
					cinemaList.add(newCinema);
					System.out.println(newCinema);

					closeTime = closeTime.plusHours(closeHour)
							.plusMinutes(closeMin);
					openTime = openTime.plusHours(newCinema.getOpen().getHour())
							.plusMinutes(newCinema.getOpen().getMin());
				} else {
					System.err.println("Кінотеатр вже створено!");
					System.out.println(newCinema);
				}
				break;

//2. Додати фільм до фільмотеки
			case "2":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}
				System.out.println("Введіть НАЗВУ фільму");
				String filmName = sc.next();

				System.out.println(
						"Введіть ТРИВАЛІСТЬ фільму (годину та хвилину) через пробіл");
				int openHour = sc.nextInt();
				int openMin = sc.nextInt();

				if (checkTime(openHour, openMin)) {
					System.err.println("Невірний формат часу - " + openHour
							+ ":" + openMin);
					continue compare;
				}

				newCinema.addMovie(
						new Movie(filmName, new Time(openHour, openMin)));
				break;

//3. Вивести фільмотеку у консоль
			case "3":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}
				if (newCinema.getMoviesLibrary().isEmpty()) {
					System.err.println("Додайте спочатку фільм у фільмотеку");
					continue compare;
				}
				newCinema.showMoviesLibrary();
				break;

//4. Додати фільм у розклад			
			case "4":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}

				if (newCinema.getMoviesLibrary().isEmpty()) {
					System.err.println("Додайте спочатку фільм у фільмотеку");
					continue compare;
				}

				System.out.println(
						"Введіть НАЗВУ фільму, який вже є в фільмотеці");
				String movieName = sc.next();
				int count = 0;

				for (Movie movie : newCinema.getMoviesLibrary()) {
					if (!movie.getTitle().equalsIgnoreCase(movieName))
						count++;
				}

				if (newCinema.getMoviesLibrary().size() == count) {
					System.err.println("Фільму з назвою \"" + movieName
							+ "\" немає в фільмотеці");
					continue compare;
				}

				Days movieShowDay = null;
				String day = null;
				try {
					System.out.println(
							"Введіть ДЕНЬ для показу фільму (англійською)");
					day = sc.next().toUpperCase();
					movieShowDay = Days.valueOf(day);
				} catch (IllegalArgumentException e) {
					System.err.println(
							"Неправильно введено день тиждня - " + day);
					continue compare;
				}

				System.out.println(
						"Введіть ЧАС початку трансляції фільму (годину та хвилину через пробіл)");
				int startHour = sc.nextInt();
				int startMin = sc.nextInt();

				if (checkTime(startHour, startHour)) {
					System.err.println("Невірний формат часу - " + startHour
							+ ":" + startHour);
					continue compare;
				}

				LocalTime seanceTime = LocalTime.parse("00:00");
				seanceTime = seanceTime.plusHours(startHour)
						.plusMinutes(startMin);

				if (openTime.isAfter(seanceTime)) {
					System.err.println("Час ВІДКРИТТЯ кінотеатру " + openTime
							+ ", сеанс не може розпочатися о " + seanceTime);
					continue compare;
				}

				if (closeTime.isBefore(seanceTime.plusMinutes(1))) {
					System.err.println("Час ЗАКРИТТЯ кінотеатру " + closeTime
							+ ", сеанс не може розпочатися о " + seanceTime);
					continue compare;
				}

//період між останнім фільмом і закриттям кінотеатру
				Movie currentMovie = null;
				for (Movie movie : newCinema.getMoviesLibrary()) {
					if (movie.getTitle().equalsIgnoreCase(movieName)) {
						currentMovie = movie;
						if (seanceTime.plusHours(movie.getDuration().getHour())
								.plusMinutes(movie.getDuration().getMin())
								.isAfter(closeTime)) {
							System.err.println("Фільм завершиться о "
									+ seanceTime
											.plusHours(movie.getDuration()
													.getHour())
											.plusMinutes(movie.getDuration()
													.getMin())
									+ " після закриття кінотеатру "
									+ closeTime);
							continue compare;
						}
					}
				}
//період між останнім фільмом і закриттям кінотеатру		

				if (!newCinema.getSchedules().isEmpty()) {
					Iterator<Entry<Days, Schedule>> iterator6 = newCinema
							.getSchedules().entrySet().iterator();
					while (iterator6.hasNext()) {
						Entry<Days, Schedule> next = iterator6.next();
						if (next.getKey().equals(movieShowDay)) {
							Iterator<Seance> iterator = next.getValue()
									.getSeances().iterator();

//зарезервовано іншим фільмом
							while (iterator.hasNext()) {
								Seance next2 = iterator.next();

								LocalTime seanceStartTime = next2.getStartTime()
										.getTime();
								LocalTime seanceEndTime = next2.getEndTime()
										.getTime();

								if (seanceTime.isAfter(
										seanceStartTime.minusMinutes(1))
										&& seanceTime.isBefore(
												seanceEndTime.plusMinutes(1))) {
									System.out.println("Start time: "
											+ next2.getStartTime().getTime()
											+ " - " + "Finish time: "
											+ next2.getEndTime().getTime());
									System.err.println(
											"Данний час зарезервовано іншим фільмом");
									continue compare;
								}

							}
//зарезервовано іншим фільмом

							LocalTime example = LocalTime.parse("00:00");
							LocalTime seanceEndTime22 = LocalTime
									.parse("00:00");

							Iterator<Seance> iterator1 = next.getValue()
									.getSeances().iterator();
							while (iterator1.hasNext()) {
								Seance next3 = iterator1.next();

								LocalTime seanceStartTime = next3.getStartTime()
										.getTime();
								LocalTime seanceEndTime21 = next3.getEndTime()
										.getTime();

//Проміжок часу між відкриттям кінотеатру і наступним фільмом
								LocalTime openTimeOfCinema = LocalTime
										.parse("00:00");
								openTimeOfCinema = openTimeOfCinema
										.plusHours(
												newCinema.getOpen().getHour())
										.plusMinutes(
												newCinema.getOpen().getMin());

								if (seanceTime.isAfter(openTimeOfCinema)
										&& seanceTime.isBefore(next3
												.getStartTime().getTime())) {
									long durationBetweenOpentimeAndStartMovie = Duration
											.between(openTimeOfCinema,
													next3.getStartTime()
															.getTime())
											.toMinutes();
									long durationOfAddedMovie = ((currentMovie
											.getDuration().getHour() * 60)
											+ currentMovie.getDuration()
													.getMin());
									if (durationBetweenOpentimeAndStartMovie <= durationOfAddedMovie) {
										System.err.println(
												"Проміжок часу між відкриттям кінотеатру і наступним фільмом у розкладі недостатній для додавання нового фільму");
										continue compare;
									}

								}
//Проміжок часу між відкриттям кінотеатру і наступним фільмом								

//період між фільмами								
								if (seanceEndTime22.isAfter(example)) {
									long durationBetweenMovies = Duration
											.between(seanceEndTime22,
													seanceStartTime)
											.toMinutes();

									if (seanceTime.isAfter(seanceEndTime22)
											&& seanceTime.isBefore(
													seanceEndTime21)) {
										long durationOfAddedMovie = ((currentMovie
												.getDuration().getHour() * 60)
												+ currentMovie.getDuration()
														.getMin());
										long durationBetweenAddedTimeAndStartTime = Duration
												.between(seanceTime,
														seanceStartTime)
												.toMinutes();

										if (durationBetweenMovies <= durationOfAddedMovie
												|| durationBetweenAddedTimeAndStartTime <= durationOfAddedMovie) {
											System.err.println(
													"Проміжок часу між фільмами менший ніж тривалість фільму який додається");
											continue compare;
										}
									}
								}
								seanceEndTime22 = next3.getEndTime().getTime();
							}
//період між фільмами

						}
					}

				}

				Iterator<Movie> iterator = newCinema.getMoviesLibrary()
						.iterator();
				while (iterator.hasNext()) {
					Movie next = iterator.next();
					if (next.getTitle().equalsIgnoreCase(movieName)) {
						newCinema.addSeanceToSchedules(
								new Seance(next, new Time(startHour, startMin)),
								movieShowDay);
					}
				}
				break;

//5. Вивести розклад у консоль				
			case "5":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}

				if (newCinema.getSchedules().isEmpty()) {
					System.err.println("Додайте спочатку фільм у розклад");
					continue compare;
				}

				newCinema.showSchedules();
				break;

//6. Видалити фільм з розкладу і фільмотеки				
			case "6":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}

				if (newCinema.getSchedules().isEmpty()
						|| newCinema.getMoviesLibrary().isEmpty()) {
					System.err.println(
							"Додайте спочатку фільм у розклад та фільмотеку");
					continue compare;
				}

				System.out.println("Введіть назву фільму для видалення");
				String movieForRemove = sc.next();

				int sizeOfMoviesLibrary = newCinema.getMoviesLibrary().size();
				int counter = 0;

				Iterator<Movie> iterator2 = newCinema.getMoviesLibrary()
						.iterator();
				while (iterator2.hasNext()) {
					Movie next = iterator2.next();
					if (next.getTitle().equalsIgnoreCase(movieForRemove)) {
						newCinema.removeMovie(next);
						System.out.println("Фільм \"" + next.getTitle()
								+ "\" видалено з розкладу та фільмотеки");
					} else {
						counter++;
					}
				}

				if (sizeOfMoviesLibrary == counter) {
					System.out.println("Фільму \"" + movieForRemove
							+ "\" немає у фільмотеці");
				}

				break;

//7. Видалити фільм лише з розкладу			
			case "7":
				if (cinemaList.isEmpty()) {
					System.err.println("Створіть спочатку кінотеатр");
					continue compare;
				}
				if (newCinema.getSchedules().isEmpty()) {
					System.err.println("Додайте спочатку фільм у розклад");
					continue compare;
				}

				Days enumDay = null;
				String day1 = null;
				try {
					System.out.println("Введіть день для видалення сеансу");
					day1 = sc.next().toUpperCase();

					enumDay = Days.valueOf(day1);
				} catch (IllegalArgumentException e) {
					System.err.println(
							"Неправильно введено день тиждня --> " + day1);
					continue compare;
				}

				Seance seanceForRemote = null;
				System.out.println(
						"Введіть час початку трансляції фільму (годину та хвилину через пробіл)");
				int someHour = sc.nextInt();
				int someMin = sc.nextInt();

				if (checkTime(someHour, someMin)) {
					System.err.println("Невірний формат часу - " + someHour
							+ ":" + someMin);
					continue compare;
				}

				Iterator<Entry<Days, Schedule>> iterator3 = newCinema
						.getSchedules().entrySet().iterator();
				while (iterator3.hasNext()) {
					Entry<Days, Schedule> next = iterator3.next();
					if (next.getKey().equals(enumDay)) {
						Schedule value = next.getValue();

						int seanceSize = value.getSeances().size();
						int seanceCounter = 0;
						Iterator<Seance> iterator5 = value.getSeances()
								.iterator();

						while (iterator5.hasNext()) {
							Seance next2 = iterator5.next();
							if (next2.getStartTime().getHour() == someHour
									&& next2.getStartTime()
											.getMin() == someMin) {
								seanceForRemote = next2;
								System.out.println("Сеанс " + next2
										+ " видалено з розкладу");
							} else {
								seanceCounter++;
							}
						}

						if (seanceSize == seanceCounter) {
							System.err.println(
									"У розкладі немає фільму на таку годину "
											+ someHour + ":" + someMin);
						}
					}
				}
				newCinema.removeSeance(seanceForRemote, enumDay);
				break;

//8. Вийти з програми			
			case "8":
				System.out.println("Програму завершено!");
				sc.close();
				System.exit(0);
				break;

			default:
				System.err.println(
						"Невірно введено значення, введіть від 1 до 8");
				break;
			}
		}

	}
}// main class
