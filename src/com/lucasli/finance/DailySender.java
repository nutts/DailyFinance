package com.lucasli.finance;

import java.io.File;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

import org.apache.commons.io.FileUtils;

public class DailySender {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{

		// Recipient's email ID needs to be mentioned.
		String to = "lucasli@adobe.com";

		// Sender's email ID needs to be mentioned
		String from = "\"Sui Li\" <lucasli@adobe.com>";

		// Assuming you are sending email from localhost
		String host = "mailsj-v1.corp.adobe.com";

		// Get system properties
		Properties properties = System.getProperties();

		// Setup mail server
		properties.setProperty("mail.smtp.host", host);

		// Get the default Session object.
		Session session = Session.getDefaultInstance(properties);
		
		String template = getTemplate();
		String templateCnt = FileUtils.readFileToString(new File(template));
		
		try {
			
			String dailyQihuo = CCPM.getQihuoDaily();
			String link = "<p>" + CCPM.getXMLURL() + "</p>";
			templateCnt = templateCnt.replace("@@CCPRESULT@@", link + dailyQihuo);
			
			
			// Create a default MimeMessage object.
			MimeMessage message = new MimeMessage(session);

			// Set From: header field of the header.
			message.setFrom(new InternetAddress(from));

			// Set To: header field of the header.
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(
					to));

			// Set Subject: header field
			message.setSubject("每日财经!");

			// Send the actual HTML message, as big as you like
			message.setContent(templateCnt, "text/html;charset=utf-8");

			// Send message
			Transport.send(message);
			System.out.println("Sent message successfully....");
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}

	}
	
	public static String getTemplate(){
		
		String os = System.getProperty("os.name");
		if(os.contains("Windows")){
			return "D:/work/eclipse_workspace/Oberon_Testing/DailyFinance/template/DailyReport.html";
		}else{
			String f = System.getProperty("templateFile");
			System.out.println("The system specified template file location is " + f);
			return f;
			
		} 
			
	}

}
