package webfilmsProject;

public class InformationManager {
	private static String theater;
	private static String movie;
	private static String time;
	
	
	public static void setTheater(String t) {
		theater = t;
	}
	public static void setMovie(String m) {
		movie = m;
	}
	public static void setTime(String t) {
		time = t;
	}
	
	public static String getTheater() {
		return theater;
	}
	public static String getMovie() {
		return movie;
	}
	public static String getTime() {
		return time;
	}

}
