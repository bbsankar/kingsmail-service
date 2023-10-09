package com.kings.mail.service;

public interface EmailService {

	public Object sendOTP(String mail);

	public Object validateOTP(String otp);

}
