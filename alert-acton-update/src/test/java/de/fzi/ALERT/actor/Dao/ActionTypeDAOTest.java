package de.fzi.ALERT.actor.Dao;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.ActionTypeDAO;
import de.fzi.ALERT.actor.Model.ActionType;


@ContextConfiguration("DAOTests-context.xml")
public class ActionTypeDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

	private ActionTypeDAO actiontypeDAO;

	@Autowired
	public void setActiontypeDAO(ActionTypeDAO actionTypeDAO) {
		this.actiontypeDAO = actionTypeDAO;
	}
	
	@Before
	public void setUp(){
		TestData.setActionType();
		actiontypeDAO.create(TestData.ACTIONTYPE);
	}
	
	@Test
	public void testGetActionTypeNameById() {
		
		String name = actiontypeDAO.getActionTypeNameById(TestData.ACTIONTYPE_ID);
		assertEquals(TestData.ACTIONTYPE_NAME,name);
	}

	@Test
	public void testFindActionTypeByName() {
		
		ActionType actiontype = actiontypeDAO.findActionTypeByName(TestData.ACTIONTYPE_NAME);
		assertEquals(TestData.ACTIONTYPE_ID , actiontype.getActionID());
		assertEquals(TestData.ACTIONTYPE_NAME , actiontype.getActionName());
	}
	
	@Test
	public void testFindActionTypeByID(){
		
		ActionType actiontype = actiontypeDAO.findActionTypeByID(TestData.ACTIONTYPE_ID);
		assertEquals(TestData.ACTIONTYPE_ID , actiontype.getActionID());
		assertEquals(TestData.ACTIONTYPE_NAME , actiontype.getActionName());
	}
	
	@After
	public void tearDown(){
		
		actiontypeDAO.remove(TestData.ACTIONTYPE);
	}

}
