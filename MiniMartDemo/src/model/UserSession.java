package model;


public final class UserSession {
	private static String username;

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		UserSession.username = username;
	}	
}
