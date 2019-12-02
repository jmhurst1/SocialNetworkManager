/**
 * 
 */
package edu.ncsu.csc316.social.manager;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.junit.Test;

/**
 * Tests SocialNetworkManager
 * @author Jason
 *
 */
public class SocialNetworkManagerTest {
	
	private static final String VALID_FILE_1 = "input/sample.csv";
	private static final String INVALID_FILE = "input/imaginary.csv";

	/**
	 * Test method for {@link edu.ncsu.csc316.social.manager.SocialNetworkManager#SocialNetworkManager(java.lang.String)}.
	 */
	@Test
	public void testSocialNetworkManager() {
		SocialNetworkManager manager = null;
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
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link edu.ncsu.csc316.social.manager.SocialNetworkManager#getSuggestionReport(java.lang.String)}.
	 */
	@Test
	public void testGetSuggestionReport() {
		fail("Not yet implemented");
	}

}
