package com.javaincapsules.SpringMailExample;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import com.javaincapsules.SpringMailExample.service.EmailService;

@SpringBootApplication
public class SpringMailExampleApplication {
	
	@Autowired
	EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(SpringMailExampleApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void sendMail() throws MessagingException, IOException {
		
		String userEmails[] = {"ahmed.abdelkarim.fci@gmail.com","ahmedmcdr0@gmail.com"};
		String subject = "Email Contains Zip file and HTML body";
		String body = "<html><body><p>&nbsp;Hello This is an <b><span style=\"color: #ffa400;\"><u>HTML</u> </span></b>Body Content</p></body></html>";
		String path = "test.txt";
		
		emailService.sendEmailWithZipFileAsAttachmentToMultipleUsers(userEmails, subject , body, path);
	}

}
