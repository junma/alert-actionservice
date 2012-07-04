package de.fzi.ALERT.actor.Dao;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.MessageDAO;
import de.fzi.ALERT.actor.Model.ActionType;
import de.fzi.ALERT.actor.Model.Message;
import de.fzi.ALERT.actor.Model.Pattern;

@ContextConfiguration("DAOTests-context.xml")
public class MessageDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests {

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
	
	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private PatternDAO patternDAO;

	@Autowired
	public void setPatternDAO( PatternDAO patternDAO) {
		this.patternDAO = patternDAO;
	}
	
	private ActionTypeDAO actiontypeDAO;

	@Autowired
	public void setActiontypeDAO(ActionTypeDAO actionTypeDAO) {
		this.actiontypeDAO = actionTypeDAO;
	}	

	@Before
	public void setUp() {
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
		
	}

	@Test
	public void testListMessage() {
		List<Message> msgList = messageDAO.listMessage();
		assertNotNull(msgList);
		assertEquals(3, msgList.size());

		assertEquals(TestData.MESSAGE_1, msgList.get(0));
		TestData.MESSAGE_1.setMsgID(msgList.get(0).getMsgID());
		System.out.println(TestData.MESSAGE_1.getMsgID());
		assertEquals(TestData.MESSAGE_2, msgList.get(1));
		TestData.MESSAGE_2.setMsgID(msgList.get(1).getMsgID());
		System.out.println(TestData.MESSAGE_2.getMsgID());
		assertEquals(TestData.MESSAGE_3, msgList.get(2));
		TestData.MESSAGE_3.setMsgID(msgList.get(2).getMsgID());
		System.out.println(TestData.MESSAGE_3.getMsgID());
	}

	@Test
	public void testFindByPatternId() {

		List<Message> msgList = messageDAO.findByPatternId(TestData.PATTERN_ID);
		assertNotNull(msgList);
		assertEquals(3, msgList.size());

		assertEquals(TestData.MESSAGE_1, msgList.get(0));
		assertEquals(TestData.MESSAGE_2, msgList.get(1));
		assertEquals(TestData.MESSAGE_3, msgList.get(2));
	}

	@Test
	public void testFindByUser() {
		
		System.out.println(TestData.MESSAGE_1.getMsgDate());
		System.out.println(TestData.MESSAGE_2.getMsgDate());
		System.out.println(TestData.MESSAGE_3.getMsgDate());
		System.out.println(TestData.SUBSCRIPTION.getDate());
		List<Message> msgList = messageDAO.findByUser(TestData.USER,
				TestData.MESSAGE_NEWST);
		assertNotNull(msgList);
		assertEquals(3, msgList.size());
	}

	@Test
	public void testFindPatternByMsgId() {

		Pattern pattern = messageDAO.findPatternByMsgId(TestData.MESSAGE_1.getMsgID());
		assertEquals(TestData.PATTERN, pattern);
	}

	@Test
	public void testFindByMsgId() {

		Message msg = messageDAO.findByMsgId(5002);
		int l = msg.getContent().length();
		System.out.println(msg.getContent()+"++++++++++++++++++");
		for(int i=0; i<l; i++)
		System.out.print(" "+(int)msg.getContent().charAt(i));

		assertEquals(TestData.MESSAGE_1, msg);
	}

	@After
	public void tearDown() {

		userDAO.remove(TestData.USER);
		patternDAO.remove(TestData.PATTERN);
		actiontypeDAO.remove(TestData.ACTIONTYPE);
		subDAO.remove(TestData.SUBSCRIPTION);
		messageDAO.remove(TestData.MESSAGE_1);
		messageDAO.remove(TestData.MESSAGE_2);
		messageDAO.remove(TestData.MESSAGE_3);

	}
}
