package test;

import util.MySQLHelper;

public class TestConnection {

	public static void main(String[] args) {
		if(MySQLHelper.openDB() != null) {
			System.out.println("Connected!");
			MySQLHelper.closeDB();
		}else {
			System.out.println("Can not open DB");
		}
	}

}
