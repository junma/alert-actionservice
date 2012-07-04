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
package de.fzi.ALERT.actor.SubscriptionController;

import java.util.List;

import org.jivesoftware.smack.XMPPException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.ActionActuator.ChatClient;
import de.fzi.ALERT.actor.ActionActuator.InstantMsgService;
import de.fzi.ALERT.actor.ActionActuator.MailAuthenticator;
import de.fzi.ALERT.actor.ActionActuator.MailService;
import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;

@Service
public class JMSMessageManager {
	// Information for the Manager
//	String mailUsername = "alert_project.eu@hotmail.com";
//	String mailPassword = "16122011";

//  	String insMsgUserName = "hiwi.fzi@googlemail.com";
//  	String insMsgPassword = "Karlsruhe";
  	private @Value("#{AnnounceProperties['mailService.host']}") String smtpServer;
  	private @Value("#{AnnounceProperties['mailService.port']}") String smtpServerPort;
  	// Get the login informations from the properties file
  	private @Value("#{AnnounceProperties['mail.username']}") String mailUsername;
  	private @Value("#{AnnounceProperties['mail.password']}") String mailPassword;
  	
  	private @Value("#{AnnounceProperties['instantMessage.username']}") String insMsgUserName;
  	private @Value("#{AnnounceProperties['instantMessage.password']}") String insMsgPassword;
  	
  	private @Value("#{AnnounceProperties['mail.frequence']}") Integer frequence;
  	
  	private MessageDAO messageDAO;
	private UserDAO userDAO;
	private SubscriptionDAO subscriptionDAO;
	private PatternDAO patternDAO;
	
	ChatClient chatClient; 
	MailAuthenticator auth;
	MailService mailService;
	@Autowired
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}
	
	@Autowired
	public void serUserDAO(UserDAO userDAO){
		this.userDAO = userDAO;
	}
	
	@Autowired
	public void setSubscriptionDAO(SubscriptionDAO subscriptionDAO){
		this.subscriptionDAO = subscriptionDAO;
	}
	
	@Autowired
	public void setPatternDAO(PatternDAO patternDAO) {
	    this.patternDAO = patternDAO;
	}
	
	public void initMailServer(){
		if(mailService == null)
			mailService = new MailService(smtpServer,smtpServerPort,mailUsername, mailPassword);
	}
	public void initMail(){
		if(auth == null){
			auth = new MailAuthenticator(this.mailUsername, this.mailPassword);
		}
	}
	
	public void initChatCleint(){
		if(chatClient == null){
			chatClient = new ChatClient();
		}
	}
	/*
	 * save the message
	 */
	public void saveMessage(Message message){
		
		// save the message into the Message table
		messageDAO.create(message);
		List<User> userList = subscriptionDAO.findUsersWithPattern(message.getPatternId());
		for(User user : userList){
			// update the unread message in the User table
			String msgID = String.valueOf(message.getMsgID());
			if(user.getUnreadmsg().length() == 0){
				user.setUnreadmsg(msgID);
			} else {
				String oldMsg = user.getUnreadmsg();
				user.setUnreadmsg(oldMsg + "," + msgID);
			}
			userDAO.edit(user);
		}
		
	}
	
	/*
	 * send Announcement to the user.
	 */
	public void sendAnnouncement(Message message){
		initMail();
		initMailServer();
		initChatCleint();
	    
		System.out.println("The Announcement Frequence is:" + frequence);
		
//		ChatClient chatClient = new ChatClient(); 
//		MailAuthenticator auth = new MailAuthenticator(this.mailUsername, this.mailPassword);
		
	    try {
	        chatClient.login(this.insMsgUserName, this.insMsgPassword);
	    } catch (XMPPException e1) {
	        e1.printStackTrace();
	    }

	    List<Subscription> actionList = this.subscriptionDAO.findSubscriptionsWithPattern(message.getPatternId());

	    for (Subscription action : actionList) {
	        String subject = "Alert Message";
	        String content = "Message for Pattern: " + 
	        			      action.getPatternID().getPatternName() + 
	        			      " \n " + 
	        			      message.getContent();

	        //if the action type is Email and the new email is ask to send synchronous
		    if (action.getAction().getActionName().equals("Email")){
		    	if(frequence==0){
		    		String address = action.getUid().getEmail();
		    		mailService.sendEmail(auth, address, subject, content); 
		    	}
		    } else if (action.getAction().getActionName().equals("InstantMessage")){
		        String address = action.getUid().getMessageAccount();
		        try {
		        	InstantMsgService.sendMsg(chatClient, content, address);
		        } catch (XMPPException e) {
		            e.printStackTrace();
		        }
		    }
	    }

//	    chatClient.disconnect();
	  
	}
	
	public MailAuthenticator getMailAuthenticator(){
		if(auth == null){
			auth = new MailAuthenticator(this.mailUsername, this.mailPassword);
		}
		return this.auth;
	}	
	
	public void savePattern(Pattern pattern){
	    this.patternDAO.create(pattern);
	}

	public void updatePattern(Pattern pattern) {
	    this.patternDAO.edit(pattern);
	}

	public void deletePattern(Pattern pattern) {
	    this.patternDAO.remove(pattern);
	}
	
}
