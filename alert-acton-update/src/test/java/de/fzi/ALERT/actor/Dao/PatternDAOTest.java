package de.fzi.ALERT.actor.Dao;

import static org.junit.Assert.*;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Model.Pattern;


@ContextConfiguration("DAOTests-context.xml")
public class PatternDAOTest extends AbstractTransactionalJUnit4SpringContextTests{

	private PatternDAO patternDAO;
	
	@Autowired
	public void setpatternDAO(PatternDAO patternDAO){
		this.patternDAO = patternDAO;
	}
	
	@Before
	public void setUp() {
		
		TestData.setPattern();		
		patternDAO.create(TestData.PATTERN);
		
	}
	
	@Test
	public void testFindByPatternName() {
		
		Pattern pattern = patternDAO.findByPatternName(TestData.PATTERN_NAME);
		assertEquals(TestData.PATTERN , pattern);
	}

	@Test
	public void testListPattern() {
		assertNotNull(patternDAO.listPattern());
	}
	
	@Test
	public void testFindById() {
		
		Pattern pattern = patternDAO.findById(TestData.PATTERN_ID);
		assertEquals(TestData.PATTERN , pattern);
	}
	
	@Test
	public void testCheckIfModify(){
		
		patternDAO.remove(TestData.PATTERN);
		TestData.PATTERN.setStatus(1);
		patternDAO.create(TestData.PATTERN);
		
		assertTrue(patternDAO.checkIfModify());		
	}
	
	@After
	public void tearDown() {

		patternDAO.remove(TestData.PATTERN);
	}

}
