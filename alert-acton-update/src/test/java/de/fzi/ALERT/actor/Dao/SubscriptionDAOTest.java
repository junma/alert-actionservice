package de.fzi.ALERT.actor.Dao;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.ActionTypeDAO;
import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.SubscriptionDAO;
import de.fzi.ALERT.actor.Dao.UserDAO;
import de.fzi.ALERT.actor.Model.ActionType;
import de.fzi.ALERT.actor.Model.Pattern;
import de.fzi.ALERT.actor.Model.Subscription;
import de.fzi.ALERT.actor.Model.User;

@ContextConfiguration("DAOTests-context.xml")
public class SubscriptionDAOTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private SubscriptionDAO subscriptionDAO;

	@Autowired
	public void setActionDAO(SubscriptionDAO subscriptionDAO) {
		this.subscriptionDAO = subscriptionDAO;
	}

	private UserDAO userDAO;

	@Autowired
	public void setUserDAO(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	private PatternDAO patternDAO;

	@Autowired
	public void setpatternDAO(PatternDAO patternDAO) {
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

		userDAO.create(TestData.USER);
		patternDAO.create(TestData.PATTERN);

		subscriptionDAO.create(TestData.SUBSCRIPTION);
	}

	@Test
	public void testListAllByUser() {
		List<Subscription> subList = subscriptionDAO
				.listAllByUser(TestData.USER);
		assertNotNull(subList);
		assertEquals(1, subList.size());
		assertEquals(TestData.SUBSCRIPTION, subList.get(0));
	}

	@Test
	public void testFindSubscriptionForUserAndPattern() {

		List<Subscription> subList = subscriptionDAO
				.findSubscriptionForUserAndPattern(TestData.PATTERN,
						TestData.USER);

		assertNotNull(subList);
		assertEquals(1, subList.size());
		assertEquals(TestData.SUBSCRIPTION, subList.get(0));

	}

	@Test
	public void testFindUnitAction() {

		Subscription sub = subscriptionDAO.findUnitSubscription(
				TestData.PATTERN, TestData.USER, TestData.WEB_MESSAGE_NAME);

		assertEquals(TestData.SUBSCRIPTION, sub);
	}

	@Test
	public void testFindUsersWithPattern() {

		List<User> userList = subscriptionDAO
				.findUsersWithPattern(TestData.PATTERN);

		assertNotNull(userList);
		assertEquals(TestData.USER, userList.get(0));
	}

	@Test
	public void testFindSubscriptionsWithPattern() {

		List<Subscription> subList = subscriptionDAO
				.findSubscriptionsWithPattern(TestData.PATTERN);

		assertNotNull(subList);
		assertEquals(TestData.SUBSCRIPTION, subList.get(0));
	}

	@Test
	public void testFindSubscriptionsByPatternAndAction() {

		List<Subscription> subList = subscriptionDAO
				.findSubscriptionsByPatternAndAction(TestData.PATTERN,
						TestData.WEB_MESSAGE);
		
		assertNotNull(subList);
		assertEquals(TestData.SUBSCRIPTION, subList.get(0));
	}
	
	@After
	public void tearDown() {

		patternDAO.remove(TestData.PATTERN);
		userDAO.remove(TestData.USER);
		subscriptionDAO.remove(TestData.SUBSCRIPTION);
	}

}
