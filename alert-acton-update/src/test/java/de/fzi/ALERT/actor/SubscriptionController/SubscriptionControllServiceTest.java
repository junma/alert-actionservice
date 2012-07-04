package de.fzi.ALERT.actor.SubscriptionController;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.ActionActuator.MessageListService;
import de.fzi.ALERT.actor.Dao.ActionTypeDAO;
import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.PatternForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.Preferences;

@ContextConfiguration("ServiceTests-context.xml")
public class SubscriptionControllServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private MessageDAO messageDAO;

	@Autowired
	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	private SubscriptionDAO subDAO;

	@Autowired
	public void setSubscriptionDAO(SubscriptionDAO subscriptionDAO) {
		this.subDAO = subscriptionDAO;
	}

	private PatternDAO patternDAO;

	@Autowired
	public void setPatternDAO(PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}

	private ActionTypeDAO actiontypeDAO;

	@Autowired
	public void setActiontypeDAO(ActionTypeDAO actionTypeDAO) {
		this.actiontypeDAO = actionTypeDAO;
	}

	private SubscriptionControllService subscriptionControllService;

	@Autowired
	public void setSubscriptionControllService(
			SubscriptionControllService subscriptionControllService) {
		this.subscriptionControllService = subscriptionControllService;
	}

	@Before
	public void setUp() throws Exception {
		TestData.setSubscription();
		userDAO.create(TestData.USER);
		patternDAO.create(TestData.PATTERN);
		subDAO.create(TestData.SUBSCRIPTION);
	}

	@Test
	public void testGetSubscriptionList() {
		List<Subscription> subList = subscriptionControllService
				.getSubscriptionList(TestData.USER_NAME);
		assertNotNull(subList);
		assertEquals(TestData.SUBSCRIPTION, subList.get(0));
	}

	@Test
	public void testGetSubscriptionFormList() {

		SubscriptionControllServiceTestData.setPatternForm1();
		List<PatternForm> pfList = subscriptionControllService
				.getSubscriptionFormList(TestData.USER_NAME);
		assertNotNull(pfList);
		int size = pfList.size();
		PatternForm pf = pfList.get(size - 1);
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_1
						.getPatternName(),
				pf.getPatternName());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_1
						.getActionList(),
				pf.getActionList());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_1.isStatus(),
				pf.isStatus());

		SubscriptionControllServiceTestData.initActionList();
	}

	@SuppressWarnings("deprecation")
	@Test
	public void testGetSubscriptionTypeForPattern() {
		List<PatternForm> patternformList = new ArrayList<PatternForm>();
		SubscriptionControllServiceTestData.setPatternForm2();
		SubscriptionControllServiceTestData.setPreferences();
		patternformList.add(SubscriptionControllServiceTestData.PATTERN_FORM_2);
		Preferences preferences = subscriptionControllService
				.getSubscriptionTypeForPattern(patternformList,
						TestData.PATTERN_NAME);
		assertEquals(SubscriptionControllServiceTestData.PREFERENCE.getsubRadio(),
				preferences.getsubRadio());
		assertEquals(SubscriptionControllServiceTestData.PREFERENCE.getsubChBx(),
				preferences.getsubChBx());
		SubscriptionControllServiceTestData.initActionList();
	}

	@Test
	public void testAddSubscription() {

		SubscriptionControllServiceTestData.setPatternForm2();

		subscriptionControllService.addSubscription(TestData.PATTERN_NAME,
				TestData.USER_NAME, TestData.INSTANT_MESSAGE_NAME);

		List<PatternForm> pfList = subscriptionControllService
				.getSubscriptionFormList(TestData.USER_NAME);
		assertNotNull(pfList);
		int size = pfList.size();
		PatternForm pf = pfList.get(size - 1);
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getPatternName(),
				pf.getPatternName());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getActionList(),
				pf.getActionList());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2.isStatus(),
				pf.isStatus());

		SubscriptionControllServiceTestData.initActionList();
	}

	@Test
	public void testDeleteSubscription() {
		subscriptionControllService.deleteSubscription(TestData.PATTERN_NAME,
				TestData.USER_NAME, TestData.WEB_MESSAGE_NAME);
		List<Subscription> subList = subscriptionControllService
				.getSubscriptionList(TestData.USER_NAME);
		assertEquals(0, subList.size());

	}

	@Test
	public void testUpdateSubscription() {
		SubscriptionControllServiceTestData.setPatternForm2();
		subscriptionControllService.updateSubscription(TestData.USER_NAME,
				SubscriptionControllServiceTestData.PATTERN_FORM_2);
		List<PatternForm> pfList = subscriptionControllService
				.getSubscriptionFormList(TestData.USER_NAME);
		assertNotNull(pfList);
		int size = pfList.size();
		PatternForm pf = pfList.get(size - 1);
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getPatternName(),
				pf.getPatternName());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getActionList(),
				pf.getActionList());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2.isStatus(),
				pf.isStatus());
		SubscriptionControllServiceTestData.initActionList();

	}

	@Test
	public void testCreateNewPatternForm() {
		SubscriptionControllServiceTestData.setPatternForm2();
		SubscriptionControllServiceTestData.setPreferences();
		PatternForm pf = subscriptionControllService.createNewPatternForm(
				TestData.PATTERN_NAME,
				SubscriptionControllServiceTestData.PREFERENCE);
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getPatternName(),
				pf.getPatternName());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getActionList(),
				pf.getActionList());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2.isStatus(),
				pf.isStatus());
		SubscriptionControllServiceTestData.initActionList();
	}

	@Test
	public void testUpdatePatternFormList() {

		List<PatternForm> patternformList = new ArrayList<PatternForm>();
		SubscriptionControllServiceTestData.setPatternForm1();
		SubscriptionControllServiceTestData.setPatternForm2();
		patternformList.add(SubscriptionControllServiceTestData.PATTERN_FORM_1);

		subscriptionControllService.updatePatternFormList(patternformList,
				SubscriptionControllServiceTestData.PATTERN_FORM_2);

		PatternForm pf = patternformList.get(0);
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getPatternName(),
				pf.getPatternName());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2
						.getActionList(),
				pf.getActionList());
		assertEquals(
				SubscriptionControllServiceTestData.PATTERN_FORM_2.isStatus(),
				pf.isStatus());
		SubscriptionControllServiceTestData.initActionList();
	}

	@Test
	public void testModifyMessageAccount() {
		subscriptionControllService.modifyMessageAccount(
				TestData.USER_MESSAGE_ACCOUNT_MODIFY, TestData.USER_NAME);
		String msgAccount = userDAO.findByUserName(TestData.USER_NAME).get(0)
				.getMessageAccount();
		assertEquals(TestData.USER_MESSAGE_ACCOUNT_MODIFY, msgAccount);
	}

	@Test
	public void testGetInstantMsgReceiverList() {
		TestData.setMessage();
		messageDAO.create(TestData.MESSAGE_1);
		int id = messageDAO.findByPatternId(TestData.PATTERN_ID).get(0)
				.getMsgID();
		subscriptionControllService.addSubscription(TestData.PATTERN_NAME,
				TestData.USER_NAME, TestData.INSTANT_MESSAGE_NAME);
		List<String> receiverList = subscriptionControllService
				.getInstantMsgReceiverList(id);
		assertEquals(TestData.USER_MESSAGE_ACCOUNT, receiverList.get(0));
		messageDAO.remove(TestData.MESSAGE_1);
		System.out.println(receiverList);
	}

	@After
	public void tearDown() throws Exception {

		userDAO.remove(TestData.USER);
		patternDAO.remove(TestData.PATTERN);
		subDAO.remove(TestData.SUBSCRIPTION);
	}
}
