package com.kings.mail.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.kings.mail.constants.MailApplicationConstants.SENDOTP;
import static com.kings.mail.constants.MailApplicationConstants.VALIDATEOTP;
import static com.kings.mail.constants.MailApplicationConstants.MAIL;


import com.kings.mail.model.EmailRequest;
import com.kings.mail.model.OtpRequest;
import com.kings.mail.service.EmailService;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RequestMapping(value = MAIL)
@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;

	@PostMapping(value = SENDOTP )
	public Object sendOTP(@RequestBody EmailRequest emailRequest) {
		String mail = emailRequest.getMail();
		log.info("sendOTP, request body {}", mail);
		Object sendOTPToMail = emailService.sendOTP(mail);
		return sendOTPToMail;
	}


	@PostMapping(value = VALIDATEOTP )
	public Object validateOTP(@RequestBody OtpRequest otpRequest) {
		String otp = otpRequest.getOtp();
		log.info("validateOTP, request body {}", otp);
		return emailService.validateOTP(otp);
	}

}