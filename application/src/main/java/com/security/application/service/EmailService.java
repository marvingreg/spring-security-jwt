package com.security.application.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.emails.Email;
import com.security.application.properties.EmailProperties;

@Service
public class EmailService {
	
	@Autowired
	EmailProperties emailProperties;

	public MailerSendResponse sendEmail(String from, String recepients, String subject) throws Exception{
		Email email = new Email();


		email.setFrom("Greg Corporation", from);
		email.addRecipient("Name", recepients);
		email.setSubject(subject);
		email.setPlain("This is the text content");
		email.setHtml("This is the HTML content");

		MailerSend ms = new MailerSend();
		
		ms.setToken(emailProperties.getToken());

		MailerSendResponse response = ms.emails().send(email);
		
		return response;
	}

}
