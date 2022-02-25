package com.javaincapsules.SpringMailExample.service;

import java.io.IOException;

import javax.mail.MessagingException;

public interface EmailService {
	
	public void sendEmailWithZipFileAsAttachmentToMultipleUsers(String[] userNameList, String subject, String body, String path) throws MessagingException, IOException;

}
