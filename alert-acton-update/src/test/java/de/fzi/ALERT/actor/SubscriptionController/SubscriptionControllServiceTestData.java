package de.fzi.ALERT.actor.SubscriptionController;

import java.util.ArrayList;
import java.util.List;

import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Preferences;

public class SubscriptionControllServiceTestData {

	public static PatternForm PATTERN_FORM_1 = new PatternForm();
	public static PatternForm PATTERN_FORM_2 = new PatternForm();
	public static PatternForm PATTERN_FORM_3 = new PatternForm();

	public static boolean PATTERN_FORM_STATUS_TRUE = true;
	public static boolean PATTERN_FORM_STATUS_FALSE = false;

	public static List<String> ACTION_LIST_1 = new ArrayList<String>();
	public static List<String> ACTION_LIST_2 = new ArrayList<String>();

	public static Preferences PREFERENCE = new Preferences();
	public static String PREFERENCE_SUBRADIO_SUBSCRIPTION = "subscription";
	public static String PREFERENCE_SUBRADIO_UNSUBSCRIPTION = "unsubscription";
	public static String[] PREFERENCE_SUBCHBX = { TestData.WEB_MESSAGE_NAME,
			TestData.INSTANT_MESSAGE_NAME };

	public static void setPatternForm1() {

		ACTION_LIST_1.add(TestData.WEB_MESSAGE_NAME);

		PATTERN_FORM_1.setPatternName(TestData.PATTERN_NAME);
		PATTERN_FORM_1.setActionList(ACTION_LIST_1);
		PATTERN_FORM_1.setStatus(PATTERN_FORM_STATUS_TRUE);

	}

	public static void setPatternForm2() {

		ACTION_LIST_2.add(TestData.WEB_MESSAGE_NAME);
		ACTION_LIST_2.add(TestData.INSTANT_MESSAGE_NAME);

		PATTERN_FORM_2.setPatternName(TestData.PATTERN_NAME);
		PATTERN_FORM_2.setActionList(ACTION_LIST_2);
		PATTERN_FORM_2.setStatus(PATTERN_FORM_STATUS_TRUE);

	}
	
	public static void setPreferences(){
		PREFERENCE.setsubRadio(PREFERENCE_SUBRADIO_SUBSCRIPTION);
		PREFERENCE.setsubChBx(PREFERENCE_SUBCHBX);
	}
	
	public static void initActionList(){
		ACTION_LIST_1 = new ArrayList<String>();
		ACTION_LIST_2 = new ArrayList<String>();
	}
}
