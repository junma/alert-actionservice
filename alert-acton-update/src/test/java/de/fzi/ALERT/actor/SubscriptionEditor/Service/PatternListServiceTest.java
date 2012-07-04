package de.fzi.ALERT.actor.SubscriptionEditor.Service;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;

import de.fzi.ALERT.actor.Dao.PatternDAO;
import de.fzi.ALERT.actor.Dao.TestData;
import de.fzi.ALERT.actor.Model.Pattern;

@ContextConfiguration("ServiceTests-context.xml")
public class PatternListServiceTest extends
		AbstractTransactionalJUnit4SpringContextTests {

	private PatternDAO patternDAO;
	
	@Autowired
	public void setpatternDAO(PatternDAO patternDAO){
		this.patternDAO = patternDAO;
	}
	
	private PatternListService patternListService;

	@Autowired
	public void setPatternListService(PatternListService patternListService) {
		this.patternListService = patternListService;
	}
	
	@Before
	public void setUp() throws Exception {
		TestData.setPattern();		
		patternDAO.create(TestData.PATTERN);
	}

	@Test
	public void testGetPatternList() {
		assertNotNull(patternListService.getPatternList());
	}

	@Test
	public void testFindPatternByName() {
		Pattern pattern = patternListService.findPatternByName(TestData.PATTERN_NAME);
		assertEquals(TestData.PATTERN , pattern);
	}

	@Test
	public void testCheckIfModify() {
				
	}

	@Test
	public void testGetDescription() {
		String des = patternListService.getDescription(TestData.PATTERN_NAME);
		assertEquals(TestData.PATTERN_DESCRIPTION , des);
	}


	@After
	public void tearDown() throws Exception {
		patternDAO.remove(TestData.PATTERN);
	}
}
