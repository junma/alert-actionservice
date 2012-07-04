package de.fzi.ALERT.actor.Dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import de.fzi.ALERT.actor.Model.ActionType;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;

public class TestData {

	public static Date DATA = Calendar.getInstance().getTime(); 
	// Test data for user
	public static String USER_NAME = "JUNIT_TEST_USER";
	public static String USER_EMAIL = "JUNIT_TEST_USER@fzi.de";
	public static String USER_MESSAGE_ACCOUNT = "JUNIT_TEST_USER@fzi.de";
	public static String USER_MESSAGE_ACCOUNT_MODIFY = "JUNIT_TEST_USER@google.de";
	public static Date USER_LASTDATE = Calendar.getInstance().getTime();
	public static String USER_ID = "10000";
	public static String USER_UNREADMSG = "0,10000";
	public static int USER_UNREADMSG_ID = 10000;
	public static String USER_UNREADMSG_MODIFY = "0,10000";
	
	// Test data for actionType
	public static int ACTIONTYPE_ID = 10000;
	public static String ACTIONTYPE_NAME = "new channel";
	public static int EMAIL_ID = 1;
	public static String EMAIL_NAME = "Email";
	public static int INSTANT_MESSAGE_ID = 2;
	public static String INSTANT_MESSAGE_NAME = "InstantMessage";
	public static int WEB_MESSAGE_ID = 3;
	public static String WEB_MESSAGE_NAME = "WebMessage";
	
	// Test data for Message
	public static int MESSAGE_1_ID = 101;
	public static String MSG_1_CONTENT = "Test message 1";
	public static Date MSG_1_DATE = Calendar.getInstance().getTime();
	public static int MESSAGE_2_ID = 102;
	public static String MSG_2_CONTENT = "Test message 2";
	public static Date MSG_2_DATE = Calendar.getInstance().getTime();
	public static int MESSAGE_3_ID = 103;
	public static String MSG_3_CONTENT = "Test message 3";
	public static Date MSG_3_DATE = Calendar.getInstance().getTime();
	public static int MESSAGE_NEWST = 10000;
	
	// Test data for Pattern	
	public static String PATTERN_ID = "WERTY_3456_LKJHG";
	public static String PATTERN_NAME = "PATTERN1";
	public static String PATTERN_DESCRIPTION = "This is pattern1";
	public static int PATTERN_STATUS = 0;
	public static byte[] PATTERN_COMPLEX_EVENT = {1,2,3,4};
	
	// Test data for Subscription
	public static int SUB_ID = 10000;	
	public static Date SUB_DATE = Calendar.getInstance().getTime();
		
	// Test data of models
	public static User USER = new User();
	public static ActionType ACTIONTYPE = new ActionType() ;
	public static ActionType EMAIL = new ActionType();
	public static ActionType INSTANT_MESSAGE = new ActionType();
	public static ActionType WEB_MESSAGE = new ActionType();
	public static Message MESSAGE_1 = new Message();
	public static Message MESSAGE_2 = new Message();
	public static Message MESSAGE_3 = new Message();
	public static Pattern PATTERN = new Pattern();
	public static Subscription SUBSCRIPTION = new Subscription();
	
	public static List<Integer> UNREAD_MSG_LIST = new ArrayList<Integer>();
	
	public static void setUser(){		
		
		USER.setEmail(USER_EMAIL);
		USER.setLastDate(USER_LASTDATE);
		USER.setMessageAccount(USER_MESSAGE_ACCOUNT);
		USER.setSubscriptiontbCollection(null);
		USER.setUid(USER_ID);
		USER.setUnreadmsg(USER_UNREADMSG);
		USER.setUsername(USER_NAME);
	}
	
	public static void setActionType(){
		
		ACTIONTYPE.setActionID(ACTIONTYPE_ID);
		ACTIONTYPE.setActionName(ACTIONTYPE_NAME);
		ACTIONTYPE.setSubscriptiontbCollection(null);	

		EMAIL.setActionID(EMAIL_ID);
		EMAIL.setActionName(EMAIL_NAME);
		EMAIL.setSubscriptiontbCollection(null);
		
		INSTANT_MESSAGE.setActionID(INSTANT_MESSAGE_ID);
		INSTANT_MESSAGE.setActionName(INSTANT_MESSAGE_NAME);
		INSTANT_MESSAGE.setSubscriptiontbCollection(null);
		
		WEB_MESSAGE.setActionID(WEB_MESSAGE_ID);
		WEB_MESSAGE.setActionName(WEB_MESSAGE_NAME);
		WEB_MESSAGE.setSubscriptiontbCollection(null);
	}

	public static void setPattern(){
		
		PATTERN.setDescription(PATTERN_DESCRIPTION);
		PATTERN.setPatternID(PATTERN_ID);
		PATTERN.setPatternName(PATTERN_NAME);
		PATTERN.setStatus(PATTERN_STATUS);
		PATTERN.setComplex_event(null);
		PATTERN.setMsgtbCollection(null);
		PATTERN.setSubscriptiontbCollection(null);
	}
	
	public static void setMessage(){
		
		setPattern();
		
		MESSAGE_1.setContent(MSG_1_CONTENT);
		MESSAGE_1.setMsgDate(Calendar.getInstance().getTime());
		MESSAGE_1.setMsgID(MESSAGE_1_ID);
		MESSAGE_1.setPatternId(PATTERN);
		
		MESSAGE_2.setContent(MSG_2_CONTENT);
		MESSAGE_2.setMsgDate(Calendar.getInstance().getTime());
		MESSAGE_2.setMsgID(MESSAGE_2_ID);
		MESSAGE_2.setPatternId(PATTERN);
		
		MESSAGE_3.setContent(MSG_3_CONTENT);
		MESSAGE_3.setMsgDate(Calendar.getInstance().getTime());
		MESSAGE_3.setMsgID(MESSAGE_3_ID);
		MESSAGE_3.setPatternId(PATTERN);
	}
	
	public static void setSubscription(){
		
		setPattern();
		setActionType();
		setUser();		
		
		SUBSCRIPTION.setAction(WEB_MESSAGE);
		SUBSCRIPTION.setDate(SUB_DATE);
		SUBSCRIPTION.setKeyID(SUB_ID);
		SUBSCRIPTION.setPatternID(PATTERN);
		SUBSCRIPTION.setUid(USER);
	}
}
