package model;

public class User {
	private int id;
	private String username;
	private String password;
	private int typeOfUser;
	
	public User() {
		super();
	}
	public User(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	public User(int id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
	}
		
	public User(int id, String username, String password, int typeOfUser) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.typeOfUser = typeOfUser;
	}
	public User(String username, String password, int typeOfUser) {
		super();
		this.username = username;
		this.password = password;
		this.typeOfUser = typeOfUser;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getTypeOfUser() {
		return typeOfUser;
	}
	public void setTypeOfUser(int typeOfUser) {
		this.typeOfUser = typeOfUser;
	}
	@Override
	public String toString() {
		return this.username;
	}
	
}
