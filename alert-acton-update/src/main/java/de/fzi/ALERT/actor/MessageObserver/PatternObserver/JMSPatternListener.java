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
package de.fzi.ALERT.actor.MessageObserver.PatternObserver;

import java.io.PrintStream;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;


import org.springframework.beans.factory.annotation.Autowired;

import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.SubscriptionController.JMSMessageManager;

public class JMSPatternListener implements MessageListener
{
	private JMSPatternParser parser;
	private JMSMessageManager manager;

	@Autowired
	public void setJMSPatternParser(JMSPatternParser jmsPatternParser)
	{
		this.parser = jmsPatternParser;
	}

	@Autowired
	public void setJMSMessageManager(JMSMessageManager jmsMessageManager)
	{
		this.manager = jmsMessageManager;
	}

	public void onMessage(Message msg)
	{
		if ((msg instanceof TextMessage)) {
			TextMessage tMsg = (TextMessage)msg;
			try {
			    Pattern pattern = this.parser.parseJsoup(tMsg.getText());
			    System.out.println("Get a new Pattern with ID: " + pattern.getPatternID());
			    System.out.println("Get a new Pattern with name: " + pattern.getPatternName());
				System.out.println("The Action is " + pattern.getStatus());
				if(pattern.getPatternID() == null ||pattern.getPatternName()== null)
				{
					   System.out.println("pattern id and name can't be null!!");
					   return;
				}
				switch (pattern.getStatus()) { 
					case 0: this.manager.savePattern(pattern);
				            break;
				    case 1: this.manager.updatePattern(pattern);
				       		break;
				    case 2: this.manager.deletePattern(pattern);
				       		break;
				    default:System.out.println("The wrong action type!"); 
				}
			} catch (JMSException e) {
			     e.printStackTrace();
			}
		}
	}
}

