package de.fzi.ALERT.actor.Dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Model.User;

@ContextConfiguration("DAOTests-context.xml")
public final class UserDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	@Before
	public void setUp(){
		TestData.setUser();
		userDAO.create(TestData.USER);
	}

	@Test
	public void testFindByUserName() {		
		
		List<User> users = userDAO.findByUserName(TestData.USER_NAME);
		assertNotNull(users);
		
		User user = users.get(0);		
		assertEquals(TestData.USER_NAME , user.getUsername());
		assertEquals(TestData.USER_ID , user.getUid());
		assertEquals(TestData.USER_EMAIL , user.getEmail());
		assertEquals(TestData.USER_LASTDATE , user.getLastDate());
		assertEquals(TestData.USER_UNREADMSG , user.getUnreadmsg());
		assertEquals(TestData.USER_MESSAGE_ACCOUNT , user.getMessageAccount());
	}
	
	@Test
	public void testFindUserByUserid() {		
		
		List<User> users = userDAO.findUserByUserid(TestData.USER_ID);
		assertNotNull(users);
		
		User user = users.get(0);
		assertEquals(TestData.USER_NAME , user.getUsername());
		assertEquals(TestData.USER_ID , user.getUid());
		assertEquals(TestData.USER_EMAIL , user.getEmail());
		assertEquals(TestData.USER_LASTDATE , user.getLastDate());
		assertEquals(TestData.USER_UNREADMSG , user.getUnreadmsg());
		assertEquals(TestData.USER_MESSAGE_ACCOUNT , user.getMessageAccount());
	}

	@Test
	public void testModifyMessageAccount() {
		
		userDAO.modifyMessageAccount(TestData.USER_MESSAGE_ACCOUNT_MODIFY, TestData.USER_NAME);		
		List<User> users = userDAO.findByUserName(TestData.USER_NAME);
		assertNotNull(users);
		
		User user = users.get(0);
		String modifiedMsgAccount = user.getMessageAccount();
		assertEquals(TestData.USER_MESSAGE_ACCOUNT_MODIFY, modifiedMsgAccount);
	}
	
	@Test
	public void testModifyUnreadMsg() {	
		
		userDAO.modifyUnreadMsg(TestData.USER_UNREADMSG_MODIFY, TestData.USER_NAME);
		List<User> users = userDAO.findByUserName(TestData.USER_NAME);
		assertNotNull(users);
		
		User user = users.get(0);
		String modifiedUnreadMsg = user.getUnreadmsg();
		assertEquals(TestData.USER_UNREADMSG_MODIFY , modifiedUnreadMsg);
	}
	
	@Test
	public void testGetUnreadMsg() {
		
		String urmsg = userDAO.getUnreadMsg(TestData.USER_NAME);
		assertEquals(TestData.USER_UNREADMSG, urmsg);
	}
	
	@After
	public void tearDown(){
		
		userDAO.remove(TestData.USER);
	}
	
}
