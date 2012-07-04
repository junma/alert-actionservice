package de.fzi.ALERT.actor.ActionActuator;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.ActionTypeDAO;
import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.SubscriptionEditor.Form.MessageForm;
import de.fzi.ALERT.actor.SubscriptionEditor.Service.PatternListService;

@ContextConfiguration("ServiceTests-context.xml")
public class MessageListServiceTest extends
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

	private MessageListService messageListService;

	@Autowired
	public void setMessageListService(MessageListService messageListService) {
		this.messageListService = messageListService;
	}

	@Before
	public void setUp() throws Exception {
		TestData.setSubscription();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestData.setMessage();

		userDAO.create(TestData.USER);
		patternDAO.create(TestData.PATTERN);
		actiontypeDAO.create(TestData.ACTIONTYPE);
		subDAO.create(TestData.SUBSCRIPTION);
		messageDAO.create(TestData.MESSAGE_1);
		messageDAO.create(TestData.MESSAGE_2);
		messageDAO.create(TestData.MESSAGE_3);

		MessageListServiceTestData.setMessageForm();
	}

	@Test
	public void testGetUnreadMsg() {
		List<Integer> unReadList = messageListService
				.getUnreadMsg(TestData.USER_NAME);
		assertNotNull(unReadList);
		assertEquals(1, unReadList.size());
		assertEquals(TestData.USER_UNREADMSG_ID, unReadList.get(0).intValue());
	}

	@Test
	public void testGetAllMsgForUser() {

		List<MessageForm> messageFormList = messageListService
				.getAllMsgForUser(TestData.USER);
		assertEquals(3, messageFormList.size());
	}

	@Test
	public void testGet5Msg() {
	}

	@Test
	public void testLeerUnreadMsgList() {
		messageListService.leerUnreadMsgList(TestData.USER_NAME);
		List<Integer> unReadList = messageListService
				.getUnreadMsg(TestData.USER_NAME);
		assertEquals(0, unReadList.size());
	}

	@After
	public void tearDown() throws Exception {
		userDAO.remove(TestData.USER);
		patternDAO.remove(TestData.PATTERN);
		actiontypeDAO.remove(TestData.ACTIONTYPE);
		subDAO.remove(TestData.SUBSCRIPTION);
		messageDAO.remove(TestData.MESSAGE_1);
		messageDAO.remove(TestData.MESSAGE_2);
		messageDAO.remove(TestData.MESSAGE_3);
	}
}
