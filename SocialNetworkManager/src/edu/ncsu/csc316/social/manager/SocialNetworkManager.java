package edu.ncsu.csc316.social.manager;

import java.io.FileNotFoundException;
import java.text.ParseException;

import edu.ncsu.csc316.dsa.list.List;
import edu.ncsu.csc316.social.data.Friendship;
import edu.ncsu.csc316.social.io.TextFileIO;

/**
 * SocialNetworkManager manages details related to social networks, including
 * calculating degrees of separation and suggesting friends.
 * 
 * @author Dr. King
 * @author Jason Hurst
 * @author <YOUR NAME HERE>
 *
 */
public class SocialNetworkManager {

	/**
	 * Initializes the SocialNetworkManager
	 * 
	 * @param pathToFriendshipFile the path to the input friendship information
	 * @throws FileNotFoundException if the input file is not found
	 * @throws ParseException        if the input file contains an unparsable date
	 */
	public SocialNetworkManager(String pathToFriendshipFile) throws FileNotFoundException, ParseException {
		List<Friendship> friendList = TextFileIO.readFriendships( pathToFriendshipFile );
	}

	/**
	 * Returns a report of the degrees of separation between the two provided email
	 * addresses
	 * 
	 * @param emailOne the email address of the first user
	 * @param emailTwo the email address of the second user
	 * @return the report of the degrees of separation between the two provided
	 *         email addresses
	 */
	public String getDegreesReport(String emailOne, String emailTwo) {
		//TODO: complete this method
		return null;
	}

	/**
	 * Returns a report of the friend suggestions for the provided email address
	 * 
	 * @param email the email address for which to provide friend suggestions
	 * @return a report of the friend suggestions for the provided email address
	 */
	public String getSuggestionReport(String email) {
		//TODO: complete this method
		return null;
	}

	//TODO: add any additional private helper methods as needed
}