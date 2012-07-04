/*******************************************************************************
 * Copyright 2012 FZI-HIWI
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/
package de.fzi.ALERT.actor.ActionActuator;

import java.util.Date;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

public class MailService {
	/**
	 * Email service to remind the users, if a new message is accept
	 */
	/*
	 * Mail Server Information
	 */
	private String host = "smtp.live.com";
	private String port = "587";
	
	/*
	 * Login information for the Administrator
	 */
	private String username = "alert_project.eu@hotmail.com";
	private String password = "16122011";

	public MailService(String smtpServer, String port, String senderEmail, String senderPwd){
		this.host = smtpServer;
		this.port = port;
		this.username = senderEmail;
		this.password = senderPwd;
	}

	public void sendEmail(Authenticator auth, String address, String subject, String content){
	    try {
	    	Properties props = new Properties();
	    	props.put("mail.smtp.host", host);
	    	props.put("mail.smtp.port", port);
	    	props.put("mail.smtp.auth", true);
	    	props.put("mail.smtp.starttls.enable","true");
	    	
	    	Session session = Session.getDefaultInstance(props,auth);
			// -- Create a new message --
		    Message msg = new MimeMessage(session);
		    
		    // -- Set the FROM and TO fields --
			msg.setFrom(new InternetAddress(username));
			msg.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(address, false));
		    
			// -- Set the subject and body text --
			msg.setSubject(subject);
		    msg.setText(content);
		    
		    // -- Set some other header information --
		    msg.setHeader("X-Mailer", "LOTONtechEmail");
		    msg.setSentDate(new Date());
		    
		    // -- Send the message --
		    Transport.send(msg);
		    
		    System.out.println("An announce Mail has been send to " + address);
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
