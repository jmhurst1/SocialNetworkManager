/**
 * 
 */
package edu.ncsu.csc316.social.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.junit.Test;

import edu.ncsu.csc316.social.factory.DSAFactory;

/**
 * Tests SocialNetworkManager
 * @author Jason
 *
 */
public class SocialNetworkManagerTest {
	
	private static final String VALID_FILE_1 = "input/sample.csv";
	private static final String VALID_FILE_2 = "input/sample2.csv";
	private static final String INVALID_FILE = "input/imaginary.csv";

	private static final DSAFactory FACTORY = new DSAFactory();
	/**
	 * Test method for {@link edu.ncsu.csc316.social.manager.SocialNetworkManager#SocialNetworkManager(java.lang.String)}.
	 */
	@Test
	public void testSocialNetworkManager() {
		SocialNetworkManager manager = null;
		assertNotNull( FACTORY );
		try {
			manager = new SocialNetworkManager(VALID_FILE_1);
		} catch (FileNotFoundException | ParseException e) {
			fail();
		}
		assertNotNull(manager);
		manager = null;
		try {
			manager = new SocialNetworkManager(INVALID_FILE);
			fail();
		} catch (FileNotFoundException | ParseException e) {
			assertNull(manager);
		}
		
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.social.manager.SocialNetworkManager#getDegreesReport(java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testGetDegreesReport() {
		SocialNetworkManager manager = null;
		try {
			manager = new SocialNetworkManager(VALID_FILE_1);
		} catch (FileNotFoundException | ParseException e) {
			fail();
		}
		assertEquals( manager.getDegreesReport("jamie@email.com", "dustin@email.com"),
				"3 degrees of separation between jamie@email.com and dustin@email.com [\n" + 
				"       jamie@email.com\n" + 
				"   --> jason@email.com\n" + 
				"   --> shawnique@email.com\n" + 
				"   --> dustin@email.com\n" +
				"]");
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.social.manager.SocialNetworkManager#getSuggestionReport(java.lang.String)}.
	 */
	@Test
	public void testGetSuggestionReport() {
		SocialNetworkManager manager = null;
		try {
			manager = new SocialNetworkManager(VALID_FILE_1);
		} catch (FileNotFoundException | ParseException e) {
			fail();
		}
		assertNotNull(manager);
		assertEquals( manager.getSuggestionReport("jason@email.com"),
				"Friend Suggestions for jason@email.com [\n" + 
				"   akond@email.com\n" + 
				"   david@email.com\n" + 
				"   dustin@email.com\n" + 
				"]");
		assertEquals( manager.getSuggestionReport("shawnique@email.com"),
				"Friend Suggestions for shawnique@email.com [\n" + 
				"   david@email.com\n" + 
				"   jamie@email.com\n" + 
				"   maria@email.com\n" + 
				"]");
		
		manager = null;
		try {
			manager = new SocialNetworkManager(VALID_FILE_2);
		} catch (FileNotFoundException | ParseException e) {
			fail();
		}
		assertNotNull(manager);
		assertEquals(manager.getSuggestionReport("dustin@email.com"), 
				"No friend suggestions for dustin@email.com" );
		assertEquals(manager.getSuggestionReport("shawnique@email.com"),
				"shawnique@email.com does not exist in the social network.");
	}

}
