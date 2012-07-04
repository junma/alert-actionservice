package de.fzi.ALERT.actor.ActionActuator;

import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;

public class MessageListServiceTestData {

	public static MessageForm MESSAGEFORM_1 = new MessageForm();
	public static MessageForm MESSAGEFORM_2 = new MessageForm();
	public static MessageForm MESSAGEFORM_3 = new MessageForm();
	
	public static void setMessageForm(){
		
		MESSAGEFORM_1.setMessageContent(TestData.MSG_1_CONTENT);
		MESSAGEFORM_1.setMessageDate(TestData.MSG_1_DATE);
		MESSAGEFORM_1.setPatternName(TestData.PATTERN_NAME);
		MESSAGEFORM_1.setStatus(false);
		
		MESSAGEFORM_2.setMessageContent(TestData.MSG_2_CONTENT);
		MESSAGEFORM_2.setMessageDate(TestData.MSG_2_DATE);
		MESSAGEFORM_2.setPatternName(TestData.PATTERN_NAME);
		MESSAGEFORM_2.setStatus(false);
		
		MESSAGEFORM_3.setMessageContent(TestData.MSG_3_CONTENT);
		MESSAGEFORM_3.setMessageDate(TestData.MSG_3_DATE);
		MESSAGEFORM_3.setPatternName(TestData.PATTERN_NAME);
		MESSAGEFORM_3.setStatus(false);
	}
}
