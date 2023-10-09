package com.kings.mail.serviceimpl;


import static com.kings.mail.constants.MailApplicationConstants.MAX_OTP_VALUE;
import static com.kings.mail.constants.MailApplicationConstants.MIN_OTP_VALUE;
import static com.kings.mail.constants.MailApplicationConstants.OTP_EMAIL_SUBJECT;
import static com.kings.mail.constants.MailApplicationConstants.OTP_EXPIRATION_TIME_MS;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.kings.mail.model.OTPInfo;
import com.kings.mail.service.EmailService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;


	public Map<String, OTPInfo> emailOtpMap = new HashMap<String, OTPInfo>(); 

	@Override
	public Object sendOTP(String mail) {
		log.info("sendOTP, request {}", mail);
		try {
			String emailRegex = "[A-Za-z0-9]+@[a-z]+\\.[a-z]{2,3}";
			Pattern pattern = Pattern.compile(emailRegex);
			Matcher matcher = pattern.matcher(mail);

			if (matcher.matches()) {
				Random random = new Random();
				int otp = MIN_OTP_VALUE + random.nextInt(MAX_OTP_VALUE - MIN_OTP_VALUE + 1);
				String otpValue = String.valueOf(otp);

				emailOtpMap.put(mail, new OTPInfo(otpValue, System.currentTimeMillis() + OTP_EXPIRATION_TIME_MS));

				sendOTPToEmail(mail, otpValue);
				log.debug("sendOTP, response {}", otp);
				return "OTP sent successfully to " + mail;
			} else {
				log.info("Email is Invalid Format");
				return "Email is Invalid Format";
			}
		} catch (Exception e) {
			log.error("sendOTP, Failed to send OTP to {}: {}", mail, e.getMessage());
			return "Failed to send OTP to " + mail + ": " + e.getMessage();
		}
	}



	public Object validateOTP(String otp) {
		log.info("validateOTP, request {}",otp);
		boolean result = isValidateOTP(otp);
		if(result==true) {
			log.debug("validateOTP, ",otp,"this OTP is valid");
			return "OTP is valid";
		}
		else {
			log.error("validateOTP, ",otp,"this OTP is invalid");
			return "OTP is invalid";
		}
	}

	public boolean isValidateOTP(String otp) {
		for (Map.Entry<String, OTPInfo> entry : emailOtpMap.entrySet()) {
			OTPInfo otpInfo = entry.getValue();
			if (otpInfo.getOtp().equals(otp) && System.currentTimeMillis() <= otpInfo.getExpirationTime()) {
				return true;
			}
		}
		return false;
	}

	private void sendOTPToEmail(String mail, String otpValue) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(mail);
		message.setSubject(OTP_EMAIL_SUBJECT);
		message.setText("Your OTP code is: " + otpValue);
		mailSender.send(message);
	}


}
