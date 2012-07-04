package de.fzi.ALERT.actor.ActionActuator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionController.JMSMessageManager;

@Service
public class AsychMailThreadService extends Thread{
	
	private @Value("#{AnnounceProperties['mail.frequence']}") Integer frequence;
	
  	private @Value("#{AnnounceProperties['mailService.host']}") String smtpServer;
  	private @Value("#{AnnounceProperties['mailService.port']}") String smtpServerPort;
  	// Get the login informations from the properties file
  	private @Value("#{AnnounceProperties['mail.username']}") String mailUsername;
  	private @Value("#{AnnounceProperties['mail.password']}") String mailPassword;
  	
	private MailAuthenticator auth;
	
  	private MessageDAO messageDAO;
	private UserDAO userDAO;
	private SubscriptionDAO subscriptionDAO;
	
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
	
	/*
	 * define the service for the JMSMessage manager
	 */
	private JMSMessageManager manager;

	private MailService mailService;
	
	@Autowired
	public void setJMSMessageManager(JMSMessageManager jmsMessageManager){
		this.manager = jmsMessageManager;
	}
	
	public AsychMailThreadService(){
	}
	
	public void initMailServer(){
		if(mailService == null)
			mailService = new MailService(smtpServer,smtpServerPort,mailUsername, mailPassword);
	}
	
	public void sendMailAsych(){
		initMailServer();
		if(auth == null){
			auth = manager.getMailAuthenticator();
		}
			
		List<User> userList = userDAO.findAllUsers();
		for(User user : userList){
//			String msgIDStr = userDAO.getUnreadMsg(user.getUsername());
			String subject = "Alert Message";
			String content = "";
			String msgIDStr = user.getUnreadmsg();
			String [] msgIDs = msgIDStr.split(",");
			//System.out.println("The user " + user.getUsername() + " has unread message: " + msgIDStr);
			//if there is unread message
			if(msgIDs.length > 1){
				//from back to front
				long limitTime = System.currentTimeMillis() - frequence*1000;
				boolean existNewMsg = false;
				//the first place of msgIDs is 0, and will be not read as a message
				for(int i=msgIDs.length-1;i>0;i--){
					Message message = messageDAO.findByMsgId(Integer.valueOf(msgIDs[i]));
					
					//check whether the message is subscribed
					List<Subscription> subscriptionList = subscriptionDAO.findSubscriptionForUserAndPattern(
							message.getPatternId(), user); 
					boolean isSubscribed = false;
					if(subscriptionList != null){
						for(Subscription sub : subscriptionList){
							if(sub.getAction().getActionName().equals("Email")){
								isSubscribed = true;
								break;
							}
						}
					}
					
					long messageTime = message.getMsgDate().getTime();
					if(messageTime>limitTime && isSubscribed){
						content = content + "Nr." + message.getMsgID()
										  + "\n"
										  + "Message for Pattern: " 
										  + message.getPatternId().getPatternName()
										  + "\n"
										  + message.getContent()
										  + "\n";
						existNewMsg = true;
					} else {
						break;
					}
				}
				String address = user.getEmail();
				if(existNewMsg){
					mailService.sendEmail(auth, address, subject, content);
					System.out.println("An announce Email for the last " + frequence + " seconds has been send to " + user.getUsername());
				}
			}
		}
	}
	
	public void run(){
		System.out.println("The Announcement Frequence is:" + frequence);
		while(frequence > 0){
			sendMailAsych();
			try {
				sleep(frequence*1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
