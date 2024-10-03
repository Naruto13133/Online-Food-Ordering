package com.atm.exception;

public class UserExceptions {

	public Exception userNotFoundException() {
		Exception userNotFound = new Exception("please provide valid user data!");
		return userNotFound;
	}
	
}
