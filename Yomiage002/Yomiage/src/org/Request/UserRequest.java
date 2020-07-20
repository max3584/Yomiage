package org.Request;

import java.util.Scanner;

public class UserRequest {

	private Scanner sc ;
	
	public UserRequest() {
		this.sc = new Scanner(System.in);
	}
	
	public String UserInputRequest(String msg) {
		System.out.print(msg);
		return sc.nextLine();
	}
}
