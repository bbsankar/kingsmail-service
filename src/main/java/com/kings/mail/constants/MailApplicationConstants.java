package com.kings.mail.constants;

public class MailApplicationConstants {
	
	
	/*
	 * These Endpoint paths for EmailController.
	 */	 
	public static final String MAIL = "/mail";  
	public static final String SENDOTP = "/sendOTP";  
	public static final String VALIDATEOTP = "/validateOTP";  
	
	
	/*
	 * Constants used in the {@code EmailServiceImpl} class for OTP (One-Time
	 * Password) generation and validation.
	 */
	public static final String OTP_EMAIL_SUBJECT = "Your OTP Code";
	public static final long OTP_EXPIRATION_TIME_MS = 5 * 60 * 1000;
	public static final int MIN_OTP_VALUE = 1000;
	public static final int MAX_OTP_VALUE = 9999;


}
