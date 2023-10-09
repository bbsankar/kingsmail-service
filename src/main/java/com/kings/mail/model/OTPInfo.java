package com.kings.mail.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OTPInfo {

	 private String otp;
     private long expirationTime;

}
