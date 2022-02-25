package com.javaincapsules.SpringMailExample.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.javaincapsules.SpringMailExample.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void sendEmailWithZipFileAsAttachmentToMultipleUsers(String[] userNameList, String subject, String body,
			String filePath) throws MessagingException, IOException {

		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setTo(userNameList);
		helper.setSubject(subject);
		helper.setText(body, true); // set second parameter by true to consider the body as an HTML no a plain text
//		File zipFile = getZipFileFromExisingTextFileOnDisk(filePath);
		File zipFile = getZipFileFromStringText();
		helper.addAttachment(zipFile.getName(), zipFile);
		mailSender.send(mimeMessage);
		System.out.println("email sent successfully");
	}

	private File getZipFileFromExisingTextFileOnDisk(String filePath) throws IOException {

		String outZipFileName = "test.zip";
	    File fileToZip = new ClassPathResource(filePath).getFile();
		
		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outZipFileName)); FileInputStream fis = new FileInputStream(fileToZip)) {
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zos.putNextEntry(zipEntry);
			// play safe
			byte[] buffer = new byte[1024];
			int len;
			while ((len = fis.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			zos.closeEntry();
		}

		return new File(outZipFileName);
	}
	
	private File getZipFileFromStringText() throws IOException {

		String outZipFileName = "test.zip";
		String txtFileName = "test.txt";
		String sampleData = "Hello, Java In Capsules";

		try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(outZipFileName))) {
			ZipEntry zipEntry = new ZipEntry(txtFileName);
			zos.putNextEntry(zipEntry);
			ByteArrayInputStream bais = new ByteArrayInputStream(sampleData.getBytes());
			// play safe
			byte[] buffer = new byte[1024];
			int len;
			while ((len = bais.read(buffer)) > 0) {
				zos.write(buffer, 0, len);
			}
			zos.closeEntry();
		}
		
		return new File(outZipFileName);
	}

}
