package test;

import dao.UserDAO;

public class TestUser {

	public static void main(String[] args) {
		UserDAO dao = new UserDAO();
		if(dao.login("admin", "admin12")) {
			System.out.println("Login Success!");
		}else {
			System.out.println("username or password incorrect!");
		}
	}

}
