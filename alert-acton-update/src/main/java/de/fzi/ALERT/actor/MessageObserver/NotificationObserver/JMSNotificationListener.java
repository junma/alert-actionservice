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
package de.fzi.ALERT.actor.MessageObserver.NotificationObserver;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.fzi.ALERT.actor.SubscriptionController.JMSMessageManager;

@Service
public class JMSNotificationListener implements MessageListener{
	
	/*
	 * define the parser as a service
	 */
	private JMSNotificationParser parser;
	
	@Autowired
	public void setJMSNotificationParser(JMSNotificationParser jmsNotificationParser){
		this.parser = jmsNotificationParser;
	}
	
	/*
	 * define the service for the JMSMessage manager
	 */
	private JMSMessageManager manager;
	
	@Autowired
	public void setJMSMessageManager(JMSMessageManager jmsMessageManager){
		this.manager = jmsMessageManager;
	}


	public void onMessage(Message msg) {
		// TODO Auto-generated method stub
		
		if(msg instanceof TextMessage){
//			model.Message message = new model.Message();
//			message.setMsgID(1);
////			Pattern pattern = (Pattern) patternDAO.findByPatternName("pattern1");
//			Pattern pattern = (Pattern) patternDAO.find(2);
//			message.setPatternId(pattern);
			TextMessage tMsg = (TextMessage)msg;
			try {
//				message.setContent(tMsg.getText());
				de.fzi.ALERT.actor.Model.Message message = parser.parseJsoup(tMsg.getText());
				if(message == null){
					System.out.println("message is null!!!!");
					return;}
//				messageDAO.create(message);
				if(!message.getContent().equals("ERROR!") && !message.getContent().equals("")){
					manager.saveMessage(message);
//					manager.sendAnnouncement(message);
				}else{
					System.out.println("message doesn't have content and will be discarded!");
				}
				
			} catch (JMSException e) {
				System.out.println("ERROR in paring complex event: "+e.getMessage());
			}
		}
	}

}
