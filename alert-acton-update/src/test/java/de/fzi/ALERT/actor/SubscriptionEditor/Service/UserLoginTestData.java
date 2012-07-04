package de.fzi.ALERT.actor.SubscriptionEditor.Service;

import java.util.Calendar;
import java.util.Date;

import de.fzi.ALERT.actor.Model.User;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Loginform;

public class UserLoginTestData {

	// Test data for user
	public static String USER_NAME = "JUNIT_TEST_USER";
	public static String USER_EMAIL = "JUNIT_TEST_USER@fzi.de";
	public static String USER_MESSAGE_ACCOUNT = "JUNIT_TEST_USER@fzi.de";
	public static String USER_MESSAGE_ACCOUNT_MODIFY = "JUNIT_TEST_USER@google.de";
	public static Date USER_LASTDATE = Calendar.getInstance().getTime();
	public static String USER_ID = "10000";
	public static String USER_UNREADMSG = "0";
	public static String USER_UNREADMSG_MODIFY = "0,10000";
	
	public static Loginform LOGINFORM = new Loginform();
		
	public static void setLoginform(){
		
		LOGINFORM.setEmail(USER_EMAIL);
		LOGINFORM.setUsername(USER_NAME);
	}

	
}
