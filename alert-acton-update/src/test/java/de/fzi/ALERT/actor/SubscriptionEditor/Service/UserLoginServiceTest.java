package de.fzi.ALERT.actor.SubscriptionEditor.Service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.User;


@ContextConfiguration("ServiceTests-context.xml")
public class UserLoginServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}
	
	private UserLoginService userLoginService;

	@Autowired
	public void setUserLoginService(UserLoginService userLoginService) {
		this.userLoginService = userLoginService;
	}

	@Before
	public void setUp() throws Exception {
		
		TestData.setUser();
		UserLoginTestData.setLoginform();		
		userDAO.create(TestData.USER);		
	}

	
//	@Test
//	public void testCheckUser() {
//
//		assertTrue(userLoginService.checkUser(UserLoginTestData.LOGINFORM));
//	}

//	@Test
//	public void testGetMsgAccount() {
//		
//		String msgAccount = userLoginService.getMsgAccount(UserLoginTestData.LOGINFORM);
//		assertEquals(TestData.USER_MESSAGE_ACCOUNT , msgAccount);
//	}

	@Test
	public void testFindUserByName() {
		
		User user = userLoginService.findUserByName(TestData.USER_NAME);
		assertEquals(TestData.USER , user);
	}
	@After
	public void tearDown() throws Exception {
		userDAO.remove(TestData.USER);
	}

}
